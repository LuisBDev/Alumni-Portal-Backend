package com.alumniportal.unmsm.service.interfaces;

import com.alumniportal.unmsm.dto.RequestDTO.CertificationRequestDTO;
import com.alumniportal.unmsm.dto.ResponseDTO.CertificationResponseDTO;
import com.alumniportal.unmsm.model.Certification;

import java.util.List;
import java.util.Map;

public interface ICertificationService {

    List<CertificationResponseDTO> findAll();

    CertificationResponseDTO findById(Long id);

    void save(Certification certification);

    void deleteById(Long id);

    List<CertificationResponseDTO> findCertificationsByUserId(Long userId);

    void saveCertification(CertificationRequestDTO certificationRequestDTO, Long userId);

    void updateCertification(Long id, Map<String, Object> fields);

}
