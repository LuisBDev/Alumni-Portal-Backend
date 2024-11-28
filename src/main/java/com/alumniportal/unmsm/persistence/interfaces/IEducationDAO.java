package com.alumniportal.unmsm.persistence.interfaces;

import com.alumniportal.unmsm.model.Education;

import java.util.List;

public interface IEducationDAO {


    List<Education> findAll();

    Education findById(Long id);

    void save(Education education);

    void deleteById(Long id);

    List<Education> findEducationsByUserId(Long userId);


}
