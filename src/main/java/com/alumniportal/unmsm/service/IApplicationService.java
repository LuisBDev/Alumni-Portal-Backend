package com.alumniportal.unmsm.service;

import com.alumniportal.unmsm.dto.ApplicationDTO;
import com.alumniportal.unmsm.model.Application;

import java.util.List;

public interface IApplicationService {

    List<ApplicationDTO> findAll();

    ApplicationDTO findById(Long id);

    void save(Application application);

    void deleteById(Long id);

    List<ApplicationDTO> findApplicationsByUser_Id(Long userId);

    List<ApplicationDTO> findApplicationsByJobOffer_Id(Long jobOfferId);

    public void saveApplication(Application application);
}
