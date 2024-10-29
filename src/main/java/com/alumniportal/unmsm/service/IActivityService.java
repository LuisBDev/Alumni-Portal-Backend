package com.alumniportal.unmsm.service;

import com.alumniportal.unmsm.dto.ActivityDTO;
import com.alumniportal.unmsm.model.Activity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IActivityService {
    List<ActivityDTO> findAll();

    ActivityDTO findById(Long id);

    void save(Activity activity);

    void deleteById(Long id) throws Exception;

    List<ActivityDTO> findActivitiesByUserId(Long userId);

    List<ActivityDTO> findActivitiesByCompanyId(Long companyId);

    void saveActivityWithImageByUserId(Activity activity, Long userId, MultipartFile image) throws IOException;

    void saveActivityWithImageByCompanyId(Activity activity, Long companyId, MultipartFile image) throws IOException;

    void uploadActivityImage(Long activityId, MultipartFile file) throws IOException;

    byte[] downloadActivityImage(Long activityId) throws Exception;

    void deleteActivityImage(Long activityId) throws Exception;


    // Método adicional para obtener el tipo de contenido
    String getFileContentType(Long activityId);

    String getFileName(Long activityId);
}
