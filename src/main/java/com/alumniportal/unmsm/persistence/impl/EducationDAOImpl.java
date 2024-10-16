package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.Education;
import com.alumniportal.unmsm.persistence.IEducationDAO;
import com.alumniportal.unmsm.repository.IEducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EducationDAOImpl implements IEducationDAO {

    @Autowired
    private IEducationRepository educationRepository;


    @Override
    public List<Education> findAll() {
        return educationRepository.findAll();
    }

    @Override
    public Education findById(Long id) {
        return educationRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Education education) {
        educationRepository.save(education);
    }

    @Override
    public void deleteById(Long id) {
        educationRepository.deleteById(id);
    }

    @Override
    public List<Education> findEducationsByUser_Id(Long userId) {
        return educationRepository.findEducationsByUser_Id(userId);
    }
}
