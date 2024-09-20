package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.model.Education;
import com.alumniportal.unmsm.persistence.IEducationDAO;
import com.alumniportal.unmsm.service.IEducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationServiceImpl implements IEducationService {

    @Autowired
    private IEducationDAO educationDAO;

    @Override
    public List<Education> findAll() {
        return educationDAO.findAll();
    }

    @Override
    public Education findById(Long id) {
        return educationDAO.findById(id);
    }

    @Override
    public void save(Education education) {
        educationDAO.save(education);
    }

    @Override
    public void deleteById(Long id) {
        educationDAO.deleteById(id);
    }

    @Override
    public List<Education> findEducationsByUser_Id(Long userId) {
        return educationDAO.findEducationsByUser_Id(userId);
    }
}
