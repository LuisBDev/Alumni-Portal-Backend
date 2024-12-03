package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.ResponseDTO.EducationResponseDTO;
import com.alumniportal.unmsm.model.Education;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.interfaces.IEducationDAO;
import com.alumniportal.unmsm.persistence.interfaces.IUserDAO;
import com.alumniportal.unmsm.service.interfaces.IEducationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements IEducationService {

    private final IEducationDAO educationDAO;

    private final IUserDAO userDAO;

    private final ModelMapper modelMapper;


    @Override
    public List<EducationResponseDTO> findAll() {
        return educationDAO.findAll()
                .stream()
                .map(education -> modelMapper.map(education, EducationResponseDTO.class))
                .toList();
    }

    @Override
    public EducationResponseDTO findById(Long id) {
        Education education = educationDAO.findById(id);
        if (education == null) {
            return null;
        }
        return modelMapper.map(education, EducationResponseDTO.class);
    }

    @Override
    public void save(Education education) {
        educationDAO.save(education);
    }

    @Override
    public void deleteById(Long id) {
        educationDAO.deleteById(id);
    }

    @Override
    public List<EducationResponseDTO> findEducationsByUserId(Long userId) {
        return educationDAO.findEducationsByUserId(userId)
                .stream()
                .map(education -> modelMapper.map(education, EducationResponseDTO.class))
                .toList();
    }

    @Override
    public void saveEducation(Education education, Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        education.setUser(user);
        educationDAO.save(education);

    }

    @Override
    public void updateEducation(Long id, Map<String, Object> fields) {
        Education education = educationDAO.findById(id);
        if (education == null) {
            throw new RuntimeException("Error: education not found!");
        }
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Education.class, k);
            field.setAccessible(true);
            if ("startDate".equals(k) || "endDate".equals(k)) {
                // Convertir el valor de String a LocalDate
                LocalDate date = LocalDate.parse(v.toString());
                ReflectionUtils.setField(field, education, date);
            } else {
                ReflectionUtils.setField(field, education, v);
            }
        });
        educationDAO.save(education);
    }
}
