package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.model.Application;
import com.alumniportal.unmsm.persistence.IApplicationDAO;
import com.alumniportal.unmsm.service.IApplicationService;
import com.alumniportal.unmsm.service.IJobOfferService;
import com.alumniportal.unmsm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alumniportal.unmsm.model.JobOffer;
import com.alumniportal.unmsm.model.User;


import java.time.LocalDate;
import java.util.List;

@Service
public class ApplicationServiceImpl implements IApplicationService {

    @Autowired
    private IApplicationDAO applicationDAO;

    @Autowired
    private IUserService userService;

    @Autowired
    private IJobOfferService jobOfferService;

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

    public void saveApplication(Application application) {
        User user = userService.findById(application.getUser().getId());
        if (user == null) {
            throw new IllegalArgumentException("Error: User not found!");
        }

        JobOffer jobOffer = jobOfferService.findById(application.getJobOffer().getId());
        if (jobOffer == null) {
            throw new IllegalArgumentException("Error: JobOffer not found!");
        }

        // Establecemos las relaciones
        application.setUser(user);
        application.setJobOffer(jobOffer);
        application.setApplicationDate(LocalDate.now());

        // Persistimos la aplicación
        applicationDAO.save(application);

        // Actualizamos el user con la nueva aplicación
        user.getApplicationList().add(application);
        userService.save(user);

        // Actualizamos la lista de aplicaciones en el JobOffer
        jobOffer.getApplicationList().add(application);
        jobOfferService.save(jobOffer);
    }


}
