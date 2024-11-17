package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.ActivityDTO;
import com.alumniportal.unmsm.model.Activity;
import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.IActivityDAO;
import com.alumniportal.unmsm.persistence.ICompanyDAO;
import com.alumniportal.unmsm.persistence.IUserDAO;
import com.alumniportal.unmsm.service.IActivityService;
import com.alumniportal.unmsm.util.EmailTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class ActivityServiceImpl implements IActivityService {

    @Autowired
    private IActivityDAO activityDAO;

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private ICompanyDAO companyDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private S3Client s3Client;


    @Autowired
    private LambdaClient lambdaClient;

    @Override
    public List<ActivityDTO> findAll() {
        return activityDAO.findAll()
                .stream()
                .map((activity) -> modelMapper.map(activity, ActivityDTO.class))
                .toList();
    }

    @Override
    public ActivityDTO findById(Long id) {
        Activity activity = activityDAO.findById(id);
        if (activity == null) {
            return null;
        }
        return modelMapper.map(activity, ActivityDTO.class);
    }

    @Override
    public void save(Activity activity) {
        activityDAO.save(activity);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        Activity activity = activityDAO.findById(id);
        if (activity == null) {
            throw new Exception("Activity with id " + id + " not found");
        }
        if (activity.getUrl() != null) {
            try {
                deleteActivityImage(id);
            } catch (Exception e) {
                throw new RuntimeException("Failed to delete activity image", e);
            }
        }
        activityDAO.deleteById(id);
    }

    @Override
    public List<ActivityDTO> findActivitiesByUserId(Long userId) {
        return activityDAO.findActivitiesByUserId(userId)
                .stream()
                .map(activity -> modelMapper.map(activity, ActivityDTO.class))
                .toList();
    }

    @Override
    public List<ActivityDTO> findActivitiesByCompanyId(Long companyId) {
        return activityDAO.findActivitiesByCompanyId(companyId)
                .stream()
                .map(activity -> modelMapper.map(activity, ActivityDTO.class))
                .toList();
    }

    @Override
    public void saveActivityWithImageByUserId(Activity activity, Long userId, MultipartFile image) throws IOException {
        // Buscar el usuario
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        // Setear el usuario y la fecha de creación
        activity.setUser(user);
        activity.setCreatedAt(LocalDate.now());

        // Guardar la actividad
        activityDAO.save(activity);

        user.getActivityList().add(activity);

        // Subir la imagen si está presente
        if (image != null && !image.isEmpty()) {
            uploadActivityImage(activity.getId(), image);
        }

        invokeLambda("Nueva Actividad: " + activity.getTitle(), user.getName(), activity);

    }


    @Override
    public void saveActivityWithImageByCompanyId(Activity activity, Long companyId, MultipartFile image) throws IOException {
        Company company = companyDAO.findById(companyId);
        if (company == null) {
            throw new IllegalArgumentException("Empresa no encontrada");
        }

        // Setea la compañía y la fecha de creación
        activity.setCompany(company);
        activity.setCreatedAt(LocalDate.now());

        // Guarda la actividad antes de subir la imagen
        activityDAO.save(activity);


        // Subir la imagen si está presente
        if (image != null && !image.isEmpty()) {
            uploadActivityImage(activity.getId(), image);
        }

        invokeLambda("AlumniPortal | New Activity: " + activity.getTitle(), company.getName(), activity);
    }

    @Override
    public void saveActivityByUserId(Activity activity, Long userId) {
        // Buscar el usuario
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        // Setear el usuario y la fecha de creación
        activity.setUser(user);
        activity.setCreatedAt(LocalDate.now());

        // Guardar la actividad
        activityDAO.save(activity);

        user.getActivityList().add(activity);

        invokeLambda("Nueva Actividad: " + activity.getTitle(), user.getName(), activity);
    }

    @Override
    public void saveActivityByCompanyId(Activity activity, Long companyId) {

        Company company = companyDAO.findById(companyId);
        if (company == null) {
            throw new IllegalArgumentException("Empresa no encontrada");
        }

        // Setea la compañía y la fecha de creación
        activity.setCompany(company);
        activity.setCreatedAt(LocalDate.now());

        // Guarda la actividad antes de subir la imagen
        activityDAO.save(activity);


        invokeLambda("AlumniPortal | New Activity: " + activity.getTitle(), company.getName(), activity);

    }

    @Override
    public void uploadActivityImage(Long activityId, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        Activity activity = activityDAO.findById(activityId);
        if (activity == null) {
            throw new RuntimeException("Activity with id " + activityId + " not found");
        }
        try {
            String fileName = file.getOriginalFilename();

            String carpeta = "activityimages/";
            String key = carpeta + activity.getId() + "_" + fileName;

            // Se verifica si la activity ya tiene imagen y eliminarla si es necesario
            if (activity.getUrl() != null) {
                s3Client.deleteObject(builder -> builder.bucket("alumniportals3").key(activity.getUrl()).build());
            }

            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket("alumniportals3")
                    .key(key)
                    .build();
            s3Client.putObject(objectRequest, RequestBody.fromBytes(file.getBytes()));

            activity.setUrl(key);
            activityDAO.save(activity);


        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public void updateActivity(Long id, Map<String, Object> fields) {
        Activity activity = activityDAO.findById(id);
        if (activity == null) {
            throw new RuntimeException("Error: activity not found!");
        }

        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Activity.class, k);
            field.setAccessible(true);


            Object valueToSet = (v instanceof String && ((String) v).trim().isEmpty()) ? null : v;

            // Convertir a LocalDate solo para campos de fecha
            if ((k.equals("startDate") || k.equals("endDate")) && valueToSet != null) {
                valueToSet = LocalDate.parse(valueToSet.toString());
            }

            ReflectionUtils.setField(field, activity, valueToSet);
        });
        activity.setUpdatedAt(LocalDate.now());
        activityDAO.save(activity);
    }


    @Override
    public byte[] downloadActivityImage(Long activityId) throws Exception {
        Activity activityDB = activityDAO.findById(activityId);
        try {
            String key = activityDB.getUrl();
            if (key == null) {
                throw new Exception("Activity does not have an image");
            }

            GetObjectRequest objectRequest = GetObjectRequest.builder()
                    .bucket("alumniportals3")
                    .key(key)
                    .build();

            return s3Client.getObjectAsBytes(objectRequest).asByteArray();

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void deleteActivityImage(Long activityId) throws Exception {
        Activity activityDB = activityDAO.findById(activityId);
        if (activityDB == null) {
            throw new Exception("Activity with id " + activityId + " not found");
        }
        try {
            String key = activityDB.getUrl();
            if (key == null) {
                throw new RuntimeException("Activity does not have an image");
            }

            s3Client.deleteObject(builder -> builder.bucket("alumniportals3")
                    .key(key)
                    .build());

            activityDB.setUrl(null);
            activityDAO.save(activityDB);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    @Override
    public String getFileContentType(Long activityId) {

        Activity activityDB = activityDAO.findById(activityId);

        String key = activityDB.getUrl();


        String fileExtension = key.substring(key.lastIndexOf('.') + 1).toLowerCase();

        switch (fileExtension) {
            case "jpg":
                return "image/jpeg";
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "pdf":
                return "application/pdf";

            default:
                return "application/octet-stream"; // Tipo de contenido genérico
        }

    }

    @Override
    public String getFileName(Long activityId) {
        Activity activityDB = activityDAO.findById(activityId);
        String key = activityDB.getUrl();
        if (key == null) {
            throw new RuntimeException("Activity does not have an image");
        }

        // Retorna el nombre del archivo desde la clave (key)
        return key.substring(key.lastIndexOf('/') + 1);
    }


    public void invokeLambda(String subject, String userName, Activity activity) {

        List<String> recipients = findRecipients();

        String htmlContent = EmailTemplate.generateHtmlContent(
                userName,
                activity.getTitle(),
                activity.getDescription(),
                activity.getEventType(),
                activity.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                activity.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                activity.getLocation(),
                activity.isEnrollable()
        );

        try {
            // Se crea el payload en JSON
            Map<String, Object> payload = Map.of(
                    "subject", subject,
                    "htmlContent", htmlContent,
                    "recipients", recipients
            );
            String payloadJson = new ObjectMapper().writeValueAsString(payload);

            // Se crea la solicitud para invocar Lambda
            InvokeRequest invokeRequest = InvokeRequest.builder()
                    .functionName("arn:aws:lambda:us-east-2:047719652432:function:alumnilambda")
                    .payload(SdkBytes.fromUtf8String(payloadJson))
                    .build();

            // Se invoca la función Lambda
            CompletableFuture<InvokeResponse> futureResponse = CompletableFuture.supplyAsync(() -> lambdaClient.invoke(invokeRequest));

            // Manejar la respuesta de la invocación asíncrona
            futureResponse.thenAccept(invokeResponse -> {
                String response = invokeResponse.payload().asUtf8String();
                System.out.println("Lambda response: " + response);
            }).exceptionally(e -> {
                System.err.println("Error al invocar Lambda: " + e.getMessage());
                return null;
            });


        } catch (Exception e) {
            System.err.println("Error al invocar Lambda: " + e.getMessage());
        }
    }

    private List<String> findRecipients() {
        return userDAO.findAll().stream().map(User::getEmail).toList();
    }
}
