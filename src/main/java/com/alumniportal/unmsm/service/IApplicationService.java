package com.alumniportal.unmsm.service;

import com.alumniportal.unmsm.model.Application;

import java.util.List;

public interface IApplicationService {

    List<Application> findAll();

    Application findById(Long id);

    void save(Application application);

    void deleteById(Long id);

    List<Application> findApplicationsByUser_Id(Long userId);

    List<Application> findApplicationsByJobOffer_Id(Long jobOfferId);

    public void saveApplication(Application application);
}
