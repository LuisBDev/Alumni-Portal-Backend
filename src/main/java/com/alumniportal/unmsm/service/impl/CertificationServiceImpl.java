package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.CertificationDTO;
import com.alumniportal.unmsm.model.Certification;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.ICertificationDAO;
import com.alumniportal.unmsm.persistence.IUserDAO;
import com.alumniportal.unmsm.service.ICertificationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CertificationServiceImpl implements ICertificationService {

    private final ICertificationDAO certificationDAO;

    private final IUserDAO userDAO;

    private final ModelMapper modelMapper;


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
    public List<CertificationDTO> findCertificationsByUserId(Long userId) {
        return certificationDAO.findCertificationsByUserId(userId)
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

    @Override
    public void updateCertification(Long id, Map<String, Object> fields) {
        Certification certification = certificationDAO.findById(id);
        if (certification == null) {
            throw new RuntimeException("Error: certification not found!");
        }

        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Certification.class, k);
            field.setAccessible(true);

            // Si el valor es una cadena vacía, asignar null
            Object valueToSet = (v instanceof String && ((String) v).isEmpty()) ? null : v;

            // Convertir a LocalDate solo para campos de fecha
            if ((k.equals("issueDate") || k.equals("expirationDate")) && valueToSet != null) {
                valueToSet = LocalDate.parse(valueToSet.toString());
            }

            ReflectionUtils.setField(field, certification, valueToSet);
        });

        certificationDAO.save(certification);
    }


}
