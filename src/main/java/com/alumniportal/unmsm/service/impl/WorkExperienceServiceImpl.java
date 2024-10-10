package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.WorkExperienceDTO;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.model.WorkExperience;
import com.alumniportal.unmsm.persistence.IUserDAO;
import com.alumniportal.unmsm.persistence.IWorkExperienceDAO;
import com.alumniportal.unmsm.service.IWorkExperienceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class WorkExperienceServiceImpl implements IWorkExperienceService {

    @Autowired
    private IWorkExperienceDAO workExperienceDAO;

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<WorkExperienceDTO> findAll() {
        return workExperienceDAO.findAll()
                .stream()
                .map(workExperience -> modelMapper.map(workExperience, WorkExperienceDTO.class))
                .toList();
    }


    @Override
    public WorkExperienceDTO findById(Long id) {
        WorkExperience workExperience = workExperienceDAO.findById(id);
        if (workExperience == null) {
            return null;
        }
        return modelMapper.map(workExperience, WorkExperienceDTO.class);
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
    public List<WorkExperienceDTO> findWorkExperiencesByUser_Id(Long userId) {
        return workExperienceDAO.findWorkExperiencesByUser_Id(userId)
                .stream()
                .map(workExperience -> modelMapper.map(workExperience, WorkExperienceDTO.class))
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
            ReflectionUtils.setField(field, workExperience, v);
        });
        workExperienceDAO.save(workExperience);

    }
}
