package com.alumniportal.unmsm.persistence;

import com.alumniportal.unmsm.model.Application;

import java.util.List;

public interface IApplicationDAO {
    List<Application> findAll();

    Application findById(Long id);

    void save(Application application);

    void deleteById(Long id);

    List<Application> findApplicationsByUser_Id(Long userId);

    List<Application> findApplicationsByJobOffer_Id(Long jobOfferId);


}
