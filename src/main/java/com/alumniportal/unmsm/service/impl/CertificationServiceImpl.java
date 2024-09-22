package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.model.Certification;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.ICertificationDAO;
import com.alumniportal.unmsm.service.ICertificationService;
import com.alumniportal.unmsm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificationServiceImpl implements ICertificationService {

    @Autowired
    private ICertificationDAO certificationDAO;

    @Autowired
    private IUserService userService;


    @Override
    public List<Certification> findAll() {
        return certificationDAO.findAll();
    }

    @Override
    public Certification findById(Long id) {
        return certificationDAO.findById(id);
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
    public List<Certification> findCertificationsByUser_Id(Long userId) {
        return certificationDAO.findCertificationsByUser_Id(userId);
    }

    @Override
    public void saveCertification(Certification certification, Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
//    Asignar el usuario al certificado y persistirlo
        certification.setUser(user);
        certificationDAO.save(certification);

//    Agregar el certificado a la lista de certificados del usuario
        user.getCertificationList().add(certification);
        userService.save(user);
    }
}
