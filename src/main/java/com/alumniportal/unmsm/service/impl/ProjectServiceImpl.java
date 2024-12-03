package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.ResponseDTO.ProjectResponseDTO;
import com.alumniportal.unmsm.model.Project;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.interfaces.IProjectDAO;
import com.alumniportal.unmsm.persistence.interfaces.IUserDAO;
import com.alumniportal.unmsm.service.interfaces.IProjectService;
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
public class ProjectServiceImpl implements IProjectService {

    private final IProjectDAO projectDAO;

    private final IUserDAO userDAO;

    private final ModelMapper modelMapper;


    @Override
    public List<ProjectResponseDTO> findAll() {
        return projectDAO.findAll()
                .stream()
                .map(project -> modelMapper.map(project, ProjectResponseDTO.class))
                .toList();
    }

    @Override
    public ProjectResponseDTO findById(Long id) {
        Project project = projectDAO.findById(id);
        if (project == null) {
            return null;
        }
        return modelMapper.map(project, ProjectResponseDTO.class);
    }

    @Override
    public void save(Project project) {
        projectDAO.save(project);
    }

    @Override
    public void deleteById(Long id) {
        projectDAO.deleteById(id);
    }

    @Override
    public List<ProjectResponseDTO> findProjectsByUserId(Long userId) {
        return projectDAO.findProjectsByUserId(userId)
                .stream()
                .map(project -> modelMapper.map(project, ProjectResponseDTO.class))
                .toList();
    }

    public void saveProject(Project project, Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
//        Asignando el usuario al project y persistiendo
        project.setUser(user);
        projectDAO.save(project);
//        Agregando el project a la lista de projects del usuario
        user.getProjectList().add(project);
        userDAO.save(user);
    }

    @Override
    public void updateProject(Long id, Map<String, Object> fields) {
        Project project = projectDAO.findById(id);
        if (project == null) {
            throw new RuntimeException("Error: project not found!");
        }
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Project.class, k);
            field.setAccessible(true);
            if ("date".equals(k)) {
                // Convertir el valor de String a LocalDate
                LocalDate date = LocalDate.parse(v.toString());
                ReflectionUtils.setField(field, project, date);
            } else {
                ReflectionUtils.setField(field, project, v);
            }
        });
        projectDAO.save(project);
    }

}
