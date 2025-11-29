package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.response.ActivityResponseDTO;
import com.alumniportal.unmsm.dto.request.ActivityRequestDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.ActivityMapper;
import com.alumniportal.unmsm.model.Activity;
import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.interfaces.IActivityDAO;
import com.alumniportal.unmsm.persistence.interfaces.ICompanyDAO;
import com.alumniportal.unmsm.persistence.interfaces.IUserDAO;
import com.alumniportal.unmsm.service.interfaces.IActivityService;
import com.alumniportal.unmsm.util.EmailTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
@RequiredArgsConstructor
public class ActivityServiceImpl implements IActivityService {

    private final IActivityDAO activityDAO;

    private final IUserDAO userDAO;

    private final ICompanyDAO companyDAO;

    private final ActivityMapper activityMapper;

    private final S3Client s3Client;

    private final LambdaClient lambdaClient;

    @Value("${custom.bucket.s3.name}")
    private String bucketName;

    @Value("${custom.s3.folder.activity}")
    private String folder;

    @Value("${custom.lambda.function.name}")
    private String lambdaFunctionName;

    @Override
    public List<ActivityResponseDTO> findAll() {
        List<Activity> activities = activityDAO.findAll();

        return activityMapper.entityListToDTOList(activities);
    }

    @Override
    public ActivityResponseDTO findById(Long id) {
        Activity activity = activityDAO.findById(id);
        if (activity == null) {
            throw new AppException("Actividad con id " + id + " no encontrada", "NOT_FOUND");
        }
        return activityMapper.entityToDTO(activity);
    }

    @Override
    public void save(Activity activity) {
        activityDAO.save(activity);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        Activity activity = activityDAO.findById(id);
        if (activity == null) {
            throw new AppException("Actividad con id " + id + " no encontrada", "NOT_FOUND");
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
    public List<ActivityResponseDTO> findActivitiesByUserId(Long userId) {
        List<Activity> activities = activityDAO.findActivitiesByUserId(userId);

        return activityMapper.entityListToDTOList(activities);
    }

    @Override
    public List<ActivityResponseDTO> findActivitiesByCompanyId(Long companyId) {
        List<Activity> activities = activityDAO.findActivitiesByCompanyId(companyId);

        return activityMapper.entityListToDTOList(activities);
    }

    @Override
    public void saveActivityWithImageByUserId(ActivityRequestDTO activityRequestDTO, Long userId, MultipartFile image) throws IOException {
        // Buscar el usuario
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new AppException("Usuario con id " + userId + " no encontrado", "NOT_FOUND");
        }

        Activity activity = activityMapper.requestDtoToEntity(activityRequestDTO);

        // Setear el usuario y la fecha de creación
        activity.setUser(user);
        activity.setCreatedAt(LocalDate.now());

        // Guardar la actividad
        activityDAO.save(activity);

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
            throw new AppException("Empresa con id " + companyId + " no encontrada", "NOT_FOUND");
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
    public ActivityResponseDTO saveActivityByUserId(ActivityRequestDTO activityRequestDTO, Long userId) {
        // Buscar el usuario
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new AppException("Usuario con id " + userId + " no encontrado", "NOT_FOUND");
        }

        Activity activity = activityMapper.requestDtoToEntity(activityRequestDTO);

        // Setear el usuario y la fecha de creación
        activity.setUser(user);
        activity.setCreatedAt(LocalDate.now());

        // Guardar la actividad
        activityDAO.save(activity);

        invokeLambda("Nueva Actividad: " + activity.getTitle(), user.getName(), activity);
        return activityMapper.entityToDTO(activity);
    }

    @Override
    public ActivityResponseDTO saveActivityByCompanyId(ActivityRequestDTO activityRequestDTO, Long companyId) {

        Company company = companyDAO.findById(companyId);
        if (company == null) {
            throw new AppException("Empresa con id " + companyId + " no encontrada", "NOT_FOUND");
        }

        Activity activity = activityMapper.requestDtoToEntity(activityRequestDTO);
        // Setea la compañía y la fecha de creación
        activity.setCompany(company);
        activity.setCreatedAt(LocalDate.now());

        // Guarda la actividad antes de subir la imagen
        activityDAO.save(activity);


        invokeLambda("AlumniPortal | New Activity: " + activity.getTitle(), company.getName(), activity);
        return activityMapper.entityToDTO(activity);
    }

    @Override
    public void uploadActivityImage(Long activityId, MultipartFile file) throws AppException {
        // Validar el archivo recibido
        if (file == null || file.isEmpty()) {
            throw new AppException("No se ha proporcionado un archivo válido", "BAD_REQUEST");
        }

        // Recuperar la actividad de la base de datos
        Activity activity = activityDAO.findById(activityId);
        if (activity == null) {
            throw new AppException("La actividad con id " + activityId + " no existe", "NOT_FOUND");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new AppException("El archivo no tiene un nombre válido", "BAD_REQUEST");
        }


        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(folder)
                .append("/")
                .append(activity.getId())
                .append("_")
                .append(fileName);

        String key = keyBuilder.toString();

        try {
            // Eliminar la imagen previa, si existe
            if (activity.getUrl() != null) {
                s3Client.deleteObject(builder -> builder.bucket(bucketName).key(activity.getUrl()).build());
            }

            // Subir la nueva imagen al bucket
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            s3Client.putObject(objectRequest, RequestBody.fromBytes(file.getBytes()));

            // Actualizar la URL de la imagen en la actividad
            activity.setUrl(key);
            activityDAO.save(activity);

        } catch (IOException e) {
            throw new AppException("Error al subir la imagen de la actividad: " + e.getMessage(), "SERVICE_UNAVAILABLE");
        }
    }


    @Override
    public ActivityResponseDTO updateActivity(Long id, Map<String, Object> fields) {
        Activity activity = activityDAO.findById(id);
        if (activity == null) {
            throw new AppException("Actividad con id " + id + " no encontrada", "NOT_FOUND");
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
        return activityMapper.entityToDTO(activity);
    }


    @Override
    public byte[] downloadActivityImage(Long activityId) throws AppException {
        // Recuperar la actividad de la base de datos
        Activity activity = activityDAO.findById(activityId);
        if (activity == null || activity.getUrl() == null) {
            throw new AppException("La actividad con id " + activityId + " no tiene una imagen o no existe", "NOT_FOUND");
        }

        // Construir la solicitud para obtener el objeto de S3
        String key = activity.getUrl();
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        try {
            return s3Client.getObjectAsBytes(objectRequest).asByteArray();
        } catch (Exception e) {
            throw new AppException("Error al descargar la imagen de la actividad: " + e.getMessage(), "SERVICE_UNAVAILABLE");
        }
    }


    @Override
    public void deleteActivityImage(Long activityId) throws Exception {
        Activity activityDB = activityDAO.findById(activityId);
        if (activityDB == null) {
            throw new AppException("Actividad con id " + activityId + " no encontrada", "NOT_FOUND");
        }

        String key = activityDB.getUrl();
        if (key == null) {
            throw new AppException("La actividad con id " + activityId + " no tiene una imagen", "NOT_FOUND");
        }

        try {
            s3Client.deleteObject(builder -> builder.bucket(bucketName)
                    .key(key)
                    .build());

            activityDB.setUrl(null);
            activityDAO.save(activityDB);

        } catch (Exception e) {
            throw new AppException("Error al eliminar la imagen de la actividad: " + e.getMessage(), "SERVICE_UNAVAILABLE");
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
            throw new AppException("La actividad con id " + activityId + " no tiene una imagen", "NOT_FOUND");
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
                    .functionName(lambdaFunctionName)
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
