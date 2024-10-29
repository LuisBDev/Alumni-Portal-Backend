package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.ActivityDTO;
import com.alumniportal.unmsm.model.Activity;
import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.IActivityDAO;
import com.alumniportal.unmsm.persistence.ICompanyDAO;
import com.alumniportal.unmsm.persistence.IUserDAO;
import com.alumniportal.unmsm.service.IActivityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Setea el usuario y la fecha de creación
        activity.setUser(user);
        activity.setCreatedAt(LocalDate.now());

        // Guarda la actividad antes de subir la imagen
        activityDAO.save(activity);

        // Subir la imagen si está presente
        if (image != null && !image.isEmpty()) {
            uploadActivityImage(activity.getId(), image);
        }

        // Agrega la actividad a la lista de actividades del usuario
        user.getActivityList().add(activity);
        userDAO.save(user);

    }

    @Override
    public void saveActivityWithImageByCompanyId(Activity activity, Long companyId, MultipartFile image) throws IOException {
        Company company = companyDAO.findById(companyId);
        if (company == null) {
            throw new RuntimeException("Company not found");
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

//        company.getActivityList().add(activity);
//        No se agrega la actividad a la lista de actividades de la compañía por el tipo de cascading
//        companyDAO.save(company);
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
