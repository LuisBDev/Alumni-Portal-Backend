package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.ProjectDTO;
import com.alumniportal.unmsm.model.Project;
import com.alumniportal.unmsm.model.Skill;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.IProjectDAO;
import com.alumniportal.unmsm.persistence.IUserDAO;
import com.alumniportal.unmsm.service.IProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    private IProjectDAO projectDAO;

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<ProjectDTO> findAll() {
        return projectDAO.findAll()
                .stream()
                .map(project -> modelMapper.map(project, ProjectDTO.class))
                .toList();
    }

    @Override
    public ProjectDTO findById(Long id) {
        Project project = projectDAO.findById(id);
        if (project == null) {
            return null;
        }
        return modelMapper.map(project, ProjectDTO.class);
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
    public List<ProjectDTO> findProjectsByUser_Id(Long userId) {
        return projectDAO.findProjectsByUser_Id(userId)
                .stream()
                .map(project -> modelMapper.map(project, ProjectDTO.class))
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
