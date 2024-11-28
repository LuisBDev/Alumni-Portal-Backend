package com.alumniportal.unmsm.persistence.interfaces;

import com.alumniportal.unmsm.model.WorkExperience;

import java.util.List;

public interface IWorkExperienceDAO {

    List<WorkExperience> findAll();

    WorkExperience findById(Long id);

    void save(WorkExperience workExperience);

    void deleteById(Long id);

    //    Buscar todas las experiencias laborales de un usuario
    List<WorkExperience> findWorkExperiencesByUserId(Long userId);


}
