package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.ActivityDTO;
import com.alumniportal.unmsm.model.Activity;
import com.alumniportal.unmsm.model.Certification;
import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.IActivityDAO;
import com.alumniportal.unmsm.persistence.ICompanyDAO;
import com.alumniportal.unmsm.persistence.IUserDAO;
import com.alumniportal.unmsm.service.IActivityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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
    private EmailService emailService;

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

        // Configurar el asunto y el cuerpo del correo en formato HTML
        String subject = "Nueva Actividad: " + activity.getTitle();
        String htmlContent = """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <style>
                body { 
                    font-family: Arial, sans-serif;
                    line-height: 1.6;
                    color: #333;
                }
                .container {
                    max-width: 600px;
                    margin: 0 auto;
                    padding: 20px;
                    background-color: #f9f9f9;
                }
                .header {
                    background-color: #4CAF50;
                    color: white;
                    padding: 10px;
                    text-align: center;
                    border-radius: 5px;
                }
                .content {
                    background-color: white;
                    padding: 20px;
                    margin-top: 20px;
                    border-radius: 5px;
                    box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                }
                .details {
                    margin: 15px 0;
                }
                .detail-item {
                    margin: 10px 0;
                }
                .footer {
                    text-align: center;
                    margin-top: 20px;
                    padding: 10px;
                    color: #666;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <div class="header">
                    <h1>Nueva Actividad Publicada</h1>
                </div>
                <div class="content">
                    <h2>%s</h2>
                    <div class="details">
                        <div class="detail-item">
                            <strong>Descripción:</strong>
                            <p>%s</p>
                        </div>
                        <div class="detail-item">
                            <strong>Tipo de evento:</strong> %s
                        </div>
                        <div class="detail-item">
                            <strong>Fecha de inicio:</strong> %s
                        </div>
                        <div class="detail-item">
                            <strong>Fecha de fin:</strong> %s
                        </div>
                        <div class="detail-item">
                            <strong>Ubicación:</strong> %s
                        </div>
                        <div class="detail-item">
                            <strong>Inscripción disponible:</strong> %s
                        </div>
                    </div>
                </div>
                <div class="footer">
                    <p>Este es un correo automático, por favor no responder.</p>
                </div>
            </div>
        </body>
        </html>
        """.formatted(
                activity.getTitle(),
                activity.getDescription(),
                activity.getEventType(),
                activity.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                activity.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                activity.getLocation(),
                activity.isEnrollable() ? "Sí" : "No"
        );

        // Enviar correo a todos los usuarios registrados
        List<User> users = userDAO.findAll();
        for (User recipient : users) {
            try {
                emailService.sendEmail(recipient.getEmail(), subject, htmlContent);
            } catch (Exception e) {
                System.err.println("Error al enviar correo a " + recipient.getEmail() + ": " + e.getMessage());
                // Continuar con el siguiente usuario incluso si falla uno
            }
        }

        // Subir la imagen si está presente
        if (image != null && !image.isEmpty()) {
            uploadActivityImage(activity.getId(), image);
        }
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

        // Configurar el asunto y el cuerpo del correo en formato HTML
        String subject = "Nueva Actividad: " + activity.getTitle();
        String htmlContent = """
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <style>
            body { 
                font-family: Arial, sans-serif;
                line-height: 1.6;
                color: #333;
            }
            .container {
                max-width: 600px;
                margin: 0 auto;
                padding: 20px;
                background-color: #f9f9f9;
            }
            .header {
                background-color: #4CAF50;
                color: white;
                padding: 10px;
                text-align: center;
                border-radius: 5px;
            }
            .content {
                background-color: white;
                padding: 20px;
                margin-top: 20px;
                border-radius: 5px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }
            .details {
                margin: 15px 0;
            }
            .detail-item {
                margin: 10px 0;
            }
            .company-info {
                background-color: #f5f5f5;
                padding: 10px;
                margin: 15px 0;
                border-radius: 5px;
            }
            .footer {
                text-align: center;
                margin-top: 20px;
                padding: 10px;
                color: #666;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <h1>Nueva Actividad Publicada</h1>
            </div>
            <div class="content">
                <div class="company-info">
                    <strong>Publicado por:</strong> %s
                </div>
                <h2>%s</h2>
                <div class="details">
                    <div class="detail-item">
                        <strong>Descripción:</strong>
                        <p>%s</p>
                    </div>
                    <div class="detail-item">
                        <strong>Tipo de evento:</strong> %s
                    </div>
                    <div class="detail-item">
                        <strong>Fecha de inicio:</strong> %s
                    </div>
                    <div class="detail-item">
                        <strong>Fecha de fin:</strong> %s
                    </div>
                    <div class="detail-item">
                        <strong>Ubicación:</strong> %s
                    </div>
                    <div class="detail-item">
                        <strong>Inscripción disponible:</strong> %s
                    </div>
                </div>
            </div>
            <div class="footer">
                <p>Este es un correo automático, por favor no responder.</p>
            </div>
        </div>
    </body>
    </html>
    """.formatted(
                company.getName(), // Agregamos el nombre de la empresa
                activity.getTitle(),
                activity.getDescription(),
                activity.getEventType(),
                activity.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                activity.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                activity.getLocation(),
                activity.isEnrollable() ? "Sí" : "No"
        );

        // Enviar correo a todos los usuarios registrados
        List<User> users = userDAO.findAll();
        for (User user : users) {
            try {
                emailService.sendEmail(user.getEmail(), subject, htmlContent);
                System.out.println("Correo enviado exitosamente a: " + user.getEmail());
            } catch (Exception e) {
                System.err.println("Error al enviar correo a " + user.getEmail() + ": " + e.getMessage());
                // Continuar con el siguiente usuario incluso si falla uno
            }
        }

        // Subir la imagen si está presente
        if (image != null && !image.isEmpty()) {
            uploadActivityImage(activity.getId(), image);
        }
    }
    @Override
    public void uploadActivityImage(Long activityId, MultipartFile file) throws IOException {
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


}
