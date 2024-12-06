package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.request.CertificationRequestDTO;
import com.alumniportal.unmsm.dto.response.CertificationResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.CertificationMapper;
import com.alumniportal.unmsm.model.Certification;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.interfaces.ICertificationDAO;
import com.alumniportal.unmsm.persistence.interfaces.IUserDAO;
import com.alumniportal.unmsm.service.interfaces.ICertificationService;
import lombok.RequiredArgsConstructor;
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

    private final CertificationMapper certificationMapper;


    @Override
    public List<CertificationResponseDTO> findAll() {
        List<Certification> certificationList = certificationDAO.findAll();
        if (certificationList.isEmpty()) {
            throw new AppException("No certifications found!", "NOT_FOUND");
        }
        return certificationMapper.entityListToDTOList(certificationList);
    }

    @Override
    public CertificationResponseDTO findById(Long id) {
        Certification certification = certificationDAO.findById(id);
        if (certification == null) {
            throw new AppException("Certification with id " + id + " not found!", "NOT_FOUND");
        }

        return certificationMapper.entityToDTO(certification);
    }

    @Override
    public void save(Certification certification) {
        certificationDAO.save(certification);
    }

    @Override
    public void deleteById(Long id) {
        Certification certification = certificationDAO.findById(id);
        if (certification == null) {
            throw new AppException("Certification with id " + id + " not found!", "NOT_FOUND");
        }
        certificationDAO.deleteById(id);
    }

    @Override
    public List<CertificationResponseDTO> findCertificationsByUserId(Long userId) {
        List<Certification> certificationsByUserId = certificationDAO.findCertificationsByUserId(userId);
        if (certificationsByUserId.isEmpty()) {
            throw new AppException("User with id " + userId + " has no certifications!", "NOT_FOUND");
        }
        return certificationMapper.entityListToDTOList(certificationsByUserId);
    }

    @Override
    public void saveCertification(CertificationRequestDTO certificationRequestDTO, Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new AppException("User with id " + userId + " not found!", "NOT_FOUND");
        }

        Certification certification = certificationMapper.requestDtoToEntity(certificationRequestDTO);

//    Asignar el usuario al certificado y persistirlo
        certification.setUser(user);
        certificationDAO.save(certification);

    }

    @Override
    public void updateCertification(Long id, Map<String, Object> fields) {
        Certification certification = certificationDAO.findById(id);
        if (certification == null) {
            throw new AppException("Certification with id " + id + " not found!", "NOT_FOUND");
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
