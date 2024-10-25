package com.alumniportal.unmsm.service;

import com.alumniportal.unmsm.dto.CertificationDTO;
import com.alumniportal.unmsm.model.Certification;

import java.util.List;
import java.util.Map;

public interface ICertificationService {

    List<CertificationDTO> findAll();

    CertificationDTO findById(Long id);

    void save(Certification certification);

    void deleteById(Long id);

    List<CertificationDTO> findCertificationsByUserId(Long userId);

    void saveCertification(Certification certification, Long userId);

    void updateCertification(Long id, Map<String, Object> fields);

}
