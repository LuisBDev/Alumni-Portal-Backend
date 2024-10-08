package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.ApplicationDTO;
import com.alumniportal.unmsm.model.Application;
import com.alumniportal.unmsm.persistence.IApplicationDAO;
import com.alumniportal.unmsm.persistence.IJobOfferDAO;
import com.alumniportal.unmsm.persistence.IUserDAO;
import com.alumniportal.unmsm.service.IApplicationService;
import com.alumniportal.unmsm.service.IJobOfferService;
import org.modelmapper.ModelMapper;
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
    private IUserDAO userDAO;

    @Autowired
    private IJobOfferDAO jobOfferDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ApplicationDTO> findAll() {
        return applicationDAO.findAll()
                .stream()
                .map(application -> modelMapper.map(application, ApplicationDTO.class))
                .toList();
    }

    @Override
    public ApplicationDTO findById(Long id) {
        Application application = applicationDAO.findById(id);
        if (application == null) {
            return null;
        }
        return modelMapper.map(application, ApplicationDTO.class);
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
    public List<ApplicationDTO> findApplicationsByUser_Id(Long userId) {
        return applicationDAO.findApplicationsByUser_Id(userId)
                .stream()
                .map(application -> modelMapper.map(application, ApplicationDTO.class))
                .toList();
    }

    @Override
    public List<ApplicationDTO> findApplicationsByJobOffer_Id(Long jobOfferId) {
        return applicationDAO.findApplicationsByJobOffer_Id(jobOfferId)
                .stream()
                .map(application -> modelMapper.map(application, ApplicationDTO.class))
                .toList();
    }

    public void saveApplication(Application application) {
        User user = userDAO.findById(application.getUser().getId());
        if (user == null) {
            throw new IllegalArgumentException("Error: User not found!");
        }

        JobOffer jobOffer = jobOfferDAO.findById(application.getJobOffer().getId());
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
        userDAO.save(user);

        // Actualizamos la lista de aplicaciones en el JobOffer
        jobOffer.getApplicationList().add(application);
        jobOfferDAO.save(jobOffer);
    }


}
