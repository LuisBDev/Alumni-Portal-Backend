package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.WorkExperience;
import com.alumniportal.unmsm.persistence.IWorkExperienceDAO;
import com.alumniportal.unmsm.repository.IUserRepository;
import com.alumniportal.unmsm.repository.IWorkExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkExperienceDAOImpl implements IWorkExperienceDAO {

    @Autowired
    private IWorkExperienceRepository workExperienceRepository;


    @Override
    public List<WorkExperience> findAll() {
        return workExperienceRepository.findAll();
    }

    @Override
    public WorkExperience findById(Long id) {
        return workExperienceRepository.findById(id).orElse(null);
    }

    @Override
    public void save(WorkExperience workExperience) {
        workExperienceRepository.save(workExperience);
    }

    @Override
    public void deleteById(Long id) {
        workExperienceRepository.deleteById(id);
    }

    @Override
    public List<WorkExperience> findWorkExperiencesByUser_Id(Long userId) {
        return workExperienceRepository.findWorkExperiencesByUser_Id(userId);
    }
}
