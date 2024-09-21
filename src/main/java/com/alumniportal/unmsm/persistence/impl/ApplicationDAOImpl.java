package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.Application;
import com.alumniportal.unmsm.persistence.IApplicationDAO;
import com.alumniportal.unmsm.repository.IApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationDAOImpl implements IApplicationDAO {

    @Autowired
    private IApplicationRepository applicationRepository;

    @Override
    public List<Application> findAll() {
        return applicationRepository.findAll();
    }

    @Override
    public Application findById(Long id) {
        return applicationRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Application application) {
        applicationRepository.save(application);
    }

    @Override
    public void deleteById(Long id) {
        applicationRepository.deleteById(id);
    }

    @Override
    public List<Application> findApplicationsByUser_Id(Long userId) {
        return applicationRepository.findApplicationsByUser_Id(userId);
    }

    @Override
    public List<Application> findApplicationsByJobOffer_Id(Long jobOfferId) {
        return applicationRepository.findApplicationsByJobOffer_Id(jobOfferId);
    }


}
