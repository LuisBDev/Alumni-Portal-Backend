package com.alumniportal.unmsm.service;

import com.alumniportal.unmsm.model.Certification;

import java.util.List;

public interface ICertificationService {

    List<Certification> findAll();

    Certification findById(Long id);

    void save(Certification certification);

    void deleteById(Long id);

    List<Certification> findCertificationsByUser_Id(Long userId);

    void saveCertification(Certification certification, Long userId);

}
