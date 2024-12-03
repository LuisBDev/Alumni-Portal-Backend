package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.ResponseDTO.WorkExperienceResponseDTO;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.model.WorkExperience;
import com.alumniportal.unmsm.persistence.interfaces.IUserDAO;
import com.alumniportal.unmsm.persistence.interfaces.IWorkExperienceDAO;
import com.alumniportal.unmsm.service.interfaces.IWorkExperienceService;
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
public class WorkExperienceServiceImpl implements IWorkExperienceService {

    private final IWorkExperienceDAO workExperienceDAO;

    private final IUserDAO userDAO;

    private final ModelMapper modelMapper;


    @Override
    public List<WorkExperienceResponseDTO> findAll() {
        return workExperienceDAO.findAll()
                .stream()
                .map(workExperience -> modelMapper.map(workExperience, WorkExperienceResponseDTO.class))
                .toList();
    }


    @Override
    public WorkExperienceResponseDTO findById(Long id) {
        WorkExperience workExperience = workExperienceDAO.findById(id);
        if (workExperience == null) {
            return null;
        }
        return modelMapper.map(workExperience, WorkExperienceResponseDTO.class);
    }

    @Override
    public void save(WorkExperience workExperience) {
        workExperienceDAO.save(workExperience);
    }

    @Override
    public void deleteById(Long id) {
        workExperienceDAO.deleteById(id);
    }

    @Override
    public List<WorkExperienceResponseDTO> findWorkExperiencesByUserId(Long userId) {
        return workExperienceDAO.findWorkExperiencesByUserId(userId)
                .stream()
                .map(workExperience -> modelMapper.map(workExperience, WorkExperienceResponseDTO.class))
                .toList();
    }

    @Override
    public void saveWorkExperience(WorkExperience workExperience, Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
//        asignando el usuario a la experiencia laboral y persistiendo
        workExperience.setUser(user);
        workExperienceDAO.save(workExperience);

//        agregando la experiencia laboral al usuario y persistiendo
        user.getWorkExperienceList().add(workExperience);
        userDAO.save(user);

    }

    @Override
    public void updateWorkExperience(Long id, Map<String, Object> fields) {
        WorkExperience workExperience = workExperienceDAO.findById(id);
        if (workExperience == null) {
            throw new RuntimeException("Error: workExperience not found!");
        }
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(WorkExperience.class, k);
            field.setAccessible(true);
            if ("startDate".equals(k) || "endDate".equals(k)) {
                // Convertir el valor de String a LocalDate
                LocalDate date = LocalDate.parse(v.toString());
                ReflectionUtils.setField(field, workExperience, date);
            } else {
                ReflectionUtils.setField(field, workExperience, v);
            }
        });
        workExperienceDAO.save(workExperience);

    }
}
