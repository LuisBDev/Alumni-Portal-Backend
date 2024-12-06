package com.alumniportal.unmsm.service.interfaces;

import com.alumniportal.unmsm.dto.request.WorkExperienceRequestDTO;
import com.alumniportal.unmsm.dto.response.WorkExperienceResponseDTO;
import com.alumniportal.unmsm.model.WorkExperience;

import java.util.List;
import java.util.Map;

public interface IWorkExperienceService {


    List<WorkExperienceResponseDTO> findAll();

    WorkExperienceResponseDTO findById(Long id);

    void save(WorkExperience workExperience);

    void deleteById(Long id);

    //    Buscar todas las experiencias laborales de un usuario
    List<WorkExperienceResponseDTO> findWorkExperiencesByUserId(Long userId);

    void saveWorkExperience(WorkExperienceRequestDTO workExperienceRequestDTO, Long userId);

    void updateWorkExperience(Long id, Map<String, Object> fields);


}
