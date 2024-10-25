package com.alumniportal.unmsm.service;

import com.alumniportal.unmsm.dto.ApplicationDTO;
import com.alumniportal.unmsm.model.Application;

import java.util.List;

public interface IApplicationService {

    List<ApplicationDTO> findAll();

    ApplicationDTO findById(Long id);

    void save(Application application);

    void deleteById(Long id);

    List<ApplicationDTO> findApplicationsByUserId(Long userId);

    List<ApplicationDTO> findApplicationsByJobOfferId(Long jobOfferId);

    ApplicationDTO findApplicationByUserIdAndJobOfferId(Long userId, Long jobOfferId);

    public void saveApplication(Application application);
}
