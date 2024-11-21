package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.Application;
import com.alumniportal.unmsm.persistence.IApplicationDAO;
import com.alumniportal.unmsm.repository.IApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicationDAOImpl implements IApplicationDAO {


    private final IApplicationRepository applicationRepository;

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
    public List<Application> findApplicationsByUserId(Long userId) {
        return applicationRepository.findApplicationsByUserId(userId);
    }

    @Override
    public List<Application> findApplicationsByJobOfferId(Long jobOfferId) {
        return applicationRepository.findApplicationsByJobOfferId(jobOfferId);
    }

    @Override
    public Application findApplicationByUserIdAndJobOfferId(Long userId, Long jobOfferId) {
        return applicationRepository.findApplicationByUserIdAndJobOfferId(userId, jobOfferId);
    }


}
