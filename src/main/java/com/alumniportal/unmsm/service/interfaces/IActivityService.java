package com.alumniportal.unmsm.service.interfaces;

import com.alumniportal.unmsm.dto.response.ActivityResponseDTO;
import com.alumniportal.unmsm.dto.request.ActivityRequestDTO;
import com.alumniportal.unmsm.model.Activity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IActivityService {
    List<ActivityResponseDTO> findAll();

    ActivityResponseDTO findById(Long id);

    void save(Activity activity);

    void deleteById(Long id) throws Exception;

    List<ActivityResponseDTO> findActivitiesByUserId(Long userId);

    List<ActivityResponseDTO> findActivitiesByCompanyId(Long companyId);

    void saveActivityWithImageByUserId(ActivityRequestDTO activityRequestDTO, Long userId, MultipartFile image) throws IOException;

    void saveActivityWithImageByCompanyId(Activity activity, Long companyId, MultipartFile image) throws IOException;

    void uploadActivityImage(Long activityId, MultipartFile file) throws IOException;

    ActivityResponseDTO updateActivity(Long id, Map<String, Object> fields);

    byte[] downloadActivityImage(Long activityId) throws Exception;

    void deleteActivityImage(Long activityId) throws Exception;


    // Método adicional para obtener el tipo de contenido
    String getFileContentType(Long activityId);

    String getFileName(Long activityId);


    ActivityResponseDTO saveActivityByUserId(ActivityRequestDTO activityRequestDTO, Long userId);

    ActivityResponseDTO saveActivityByCompanyId(ActivityRequestDTO activityRequestDTO, Long companyId);
}
