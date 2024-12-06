package com.alumniportal.unmsm.service.interfaces;

import com.alumniportal.unmsm.dto.request.EducationRequestDTO;
import com.alumniportal.unmsm.dto.response.EducationResponseDTO;
import com.alumniportal.unmsm.model.Education;

import java.util.List;
import java.util.Map;

public interface IEducationService {

    List<EducationResponseDTO> findAll();

    EducationResponseDTO findById(Long id);

    void save(Education education);

    void deleteById(Long id);

    List<EducationResponseDTO> findEducationsByUserId(Long userId);

    void saveEducation(EducationRequestDTO educationRequestDTO, Long userId);

    void updateEducation(Long id, Map<String, Object> fields);


}
