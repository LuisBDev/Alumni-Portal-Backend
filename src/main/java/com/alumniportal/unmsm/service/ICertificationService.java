package com.alumniportal.unmsm.service;

import com.alumniportal.unmsm.dto.CertificationDTO;
import com.alumniportal.unmsm.model.Certification;

import java.util.List;

public interface ICertificationService {

    List<CertificationDTO> findAll();

    CertificationDTO findById(Long id);

    void save(Certification certification);

    void deleteById(Long id);

    List<CertificationDTO> findCertificationsByUser_Id(Long userId);

    void saveCertification(Certification certification, Long userId);

}
