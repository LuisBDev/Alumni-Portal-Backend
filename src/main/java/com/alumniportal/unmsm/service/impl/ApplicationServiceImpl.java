package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.model.Application;
import com.alumniportal.unmsm.persistence.IApplicationDAO;
import com.alumniportal.unmsm.service.IApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements IApplicationService {

    @Autowired
    private IApplicationDAO applicationDAO;

    @Override
    public List<Application> findAll() {
        return applicationDAO.findAll();
    }

    @Override
    public Application findById(Long id) {
        return applicationDAO.findById(id);
    }

    @Override
    public void save(Application application) {
        applicationDAO.save(application);
    }

    @Override
    public void deleteById(Long id) {
        applicationDAO.deleteById(id);
    }

    @Override
    public List<Application> findApplicationsByUser_Id(Long userId) {
        return applicationDAO.findApplicationsByUser_Id(userId);
    }

    @Override
    public List<Application> findApplicationsByJobOffer_Id(Long jobOfferId) {
        return applicationDAO.findApplicationsByJobOffer_Id(jobOfferId);
    }


}
