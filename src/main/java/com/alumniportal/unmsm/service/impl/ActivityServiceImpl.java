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
    public void deleteById(Long id) {
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
    public void saveActivityByUserId(Activity activity, Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
//        Setteando el usuario y fecha de creacion en la actividad
        activity.setUser(user);
        activity.setCreatedAt(LocalDate.now());
        activityDAO.save(activity);

//        Agregando la actividad a la lista de actividades del usuario
        user.getActivityList().add(activity);
        userDAO.save(user);
        System.out.println(user.getActivityList());

    }

    @Override
    public void saveActivityByCompanyId(Activity activity, Long companyId) {
        Company company = companyDAO.findById(companyId);
        if (company == null) {
            throw new RuntimeException("Company not found");
        }

        activity.setCompany(company);
        activity.setCreatedAt(LocalDate.now());
        activityDAO.save(activity);
        System.out.println(company.getActivityList());


//        No se agrega la actividad a la lista de actividades de la empresa por el cascading de la relación

    }

    @Override
    public String uploadActivityImage(Long activityId, MultipartFile file) throws IOException {
        Activity activityDB = activityDAO.findById(activityId);
        try {
            String fileName = file.getOriginalFilename();

            String carpeta = "activityimages/";
            String key = carpeta + activityDB.getId() + "_" + fileName;

            // Se verifica si la activity ya tiene imagen y eliminarla si es necesario
            if (activityDB.getUrl() != null) {
                s3Client.deleteObject(builder -> builder.bucket("alumniportals3").key(activityDB.getUrl()).build());
            }

            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket("alumniportals3")
                    .key(key)
                    .build();
            s3Client.putObject(objectRequest, RequestBody.fromBytes(file.getBytes()));

            activityDB.setUrl(key);
            activityDAO.save(activityDB);

            return "File uploaded successfully";

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
