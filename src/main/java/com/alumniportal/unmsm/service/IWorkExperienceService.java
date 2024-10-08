package com.alumniportal.unmsm.service;

import com.alumniportal.unmsm.dto.WorkExperienceDTO;
import com.alumniportal.unmsm.model.WorkExperience;

import java.util.List;

public interface IWorkExperienceService {


    List<WorkExperienceDTO> findAll();

    WorkExperienceDTO findById(Long id);

    void save(WorkExperience workExperience);

    void deleteById(Long id);

    //    Buscar todas las experiencias laborales de un usuario
    List<WorkExperienceDTO> findWorkExperiencesByUser_Id(Long userId);

    void saveWorkExperience(WorkExperience workExperience, Long userId);


}
