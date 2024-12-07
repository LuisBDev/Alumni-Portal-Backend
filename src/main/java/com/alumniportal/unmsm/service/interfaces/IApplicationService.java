package com.alumniportal.unmsm.service.interfaces;

import com.alumniportal.unmsm.dto.request.ApplicationRequestDTO;
import com.alumniportal.unmsm.dto.response.ApplicationResponseDTO;
import com.alumniportal.unmsm.model.Application;

import java.util.List;

public interface IApplicationService {

    List<ApplicationResponseDTO> findAll();

    ApplicationResponseDTO findById(Long id);

    void save(Application application);

    void deleteById(Long id);

    List<ApplicationResponseDTO> findApplicationsByUserId(Long userId);

    List<ApplicationResponseDTO> findApplicationsByJobOfferId(Long jobOfferId);

    ApplicationResponseDTO findApplicationByUserIdAndJobOfferId(Long userId, Long jobOfferId);

    ApplicationResponseDTO saveApplication(ApplicationRequestDTO applicationRequestDTO);
}
