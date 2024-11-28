package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.Certification;
import com.alumniportal.unmsm.persistence.interfaces.ICertificationDAO;
import com.alumniportal.unmsm.repository.ICertificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CertificationDAOImpl implements ICertificationDAO {


    private final ICertificationRepository certificationRepository;

    @Override
    public List<Certification> findAll() {
        return certificationRepository.findAll();
    }

    @Override
    public Certification findById(Long id) {
        return certificationRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Certification certification) {
        certificationRepository.save(certification);
    }

    @Override
    public void deleteById(Long id) {
        certificationRepository.deleteById(id);
    }

    @Override
    public List<Certification> findCertificationsByUserId(Long userId) {
        return certificationRepository.findCertificationsByUserId(userId);
    }
}
