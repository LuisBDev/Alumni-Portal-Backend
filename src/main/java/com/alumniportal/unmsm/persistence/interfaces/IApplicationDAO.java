package com.alumniportal.unmsm.persistence.interfaces;

import com.alumniportal.unmsm.model.Application;

import java.util.List;

public interface IApplicationDAO {
    List<Application> findAll();

    Application findById(Long id);

    void save(Application application);

    void deleteById(Long id);

    List<Application> findApplicationsByUserId(Long userId);

    List<Application> findApplicationsByJobOfferId(Long jobOfferId);

    Application findApplicationByUserIdAndJobOfferId(Long userId, Long jobOfferId);


}
