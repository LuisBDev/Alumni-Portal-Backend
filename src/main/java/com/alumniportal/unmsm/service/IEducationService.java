package com.alumniportal.unmsm.service;

import com.alumniportal.unmsm.model.Education;

import java.util.List;

public interface IEducationService {

    List<Education> findAll();

    Education findById(Long id);

    void save(Education education);

    void deleteById(Long id);

    List<Education> findEducationsByUser_Id(Long userId);

    
}
