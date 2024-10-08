package com.alumniportal.unmsm.service;

import com.alumniportal.unmsm.dto.EducationDTO;
import com.alumniportal.unmsm.model.Education;

import java.util.List;

public interface IEducationService {

    List<EducationDTO> findAll();

    EducationDTO findById(Long id);

    void save(Education education);

    void deleteById(Long id);

    List<EducationDTO> findEducationsByUser_Id(Long userId);

    void saveEducation(Education education, Long userId);


}
