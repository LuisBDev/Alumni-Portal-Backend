package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.model.WorkExperience;
import com.alumniportal.unmsm.persistence.IWorkExperienceDAO;
import com.alumniportal.unmsm.service.IWorkExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkExperienceServiceImpl implements IWorkExperienceService {

    @Autowired
    private IWorkExperienceDAO workExperienceDAO;


    @Override
    public List<WorkExperience> findAll() {
        return workExperienceDAO.findAll();
    }

    @Override
    public WorkExperience findById(Long id) {
        return workExperienceDAO.findById(id);
    }

    @Override
    public void save(WorkExperience workExperience) {
        workExperienceDAO.save(workExperience);
    }

    @Override
    public void deleteById(Long id) {
        workExperienceDAO.deleteById(id);
    }

    @Override
    public List<WorkExperience> findWorkExperiencesByUser_Id(Long userId) {
        return workExperienceDAO.findWorkExperiencesByUser_Id(userId);
    }
}
