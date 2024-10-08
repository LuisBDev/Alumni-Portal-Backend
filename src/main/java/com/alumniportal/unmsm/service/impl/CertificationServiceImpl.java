package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.CertificationDTO;
import com.alumniportal.unmsm.model.Certification;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.ICertificationDAO;
import com.alumniportal.unmsm.persistence.IUserDAO;
import com.alumniportal.unmsm.service.ICertificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificationServiceImpl implements ICertificationService {

    @Autowired
    private ICertificationDAO certificationDAO;

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<CertificationDTO> findAll() {
        return certificationDAO.findAll()
                .stream()
                .map(certification -> modelMapper.map(certification, CertificationDTO.class))
                .toList();
    }

    @Override
    public CertificationDTO findById(Long id) {
        Certification certification = certificationDAO.findById(id);
        if (certification == null) {
            return null;
        }
        return modelMapper.map(certification, CertificationDTO.class);
    }

    @Override
    public void save(Certification certification) {
        certificationDAO.save(certification);
    }

    @Override
    public void deleteById(Long id) {
        certificationDAO.deleteById(id);
    }

    @Override
    public List<CertificationDTO> findCertificationsByUser_Id(Long userId) {
        return certificationDAO.findCertificationsByUser_Id(userId)
                .stream()
                .map(certification -> modelMapper.map(certification, CertificationDTO.class))
                .toList();
    }

    @Override
    public void saveCertification(Certification certification, Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
//    Asignar el usuario al certificado y persistirlo
        certification.setUser(user);
        certificationDAO.save(certification);

//    Agregar el certificado a la lista de certificados del usuario
        user.getCertificationList().add(certification);
        userDAO.save(user);
    }
}
