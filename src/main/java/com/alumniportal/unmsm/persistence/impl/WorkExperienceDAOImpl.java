package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.WorkExperience;
import com.alumniportal.unmsm.persistence.interfaces.IWorkExperienceDAO;
import com.alumniportal.unmsm.repository.IWorkExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkExperienceDAOImpl implements IWorkExperienceDAO {


    private final IWorkExperienceRepository workExperienceRepository;


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
    public List<WorkExperience> findWorkExperiencesByUserId(Long userId) {
        return workExperienceRepository.findWorkExperiencesByUserId(userId);
    }
}
