package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.request.ProjectRequestDTO;
import com.alumniportal.unmsm.dto.response.ProjectResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.ProjectMapper;
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

    private final ProjectMapper projectMapper;


    @Override
    public List<ProjectResponseDTO> findAll() {
        List<Project> projectList = projectDAO.findAll();

        return projectMapper.entityListToDTOList(projectList);
    }

    @Override
    public ProjectResponseDTO findById(Long id) {
        Project project = projectDAO.findById(id);
        if (project == null) {
            throw new AppException("Project not found", "NOT_FOUND");
        }
        return projectMapper.entityToDTO(project);
    }

    @Override
    public void save(Project project) {
        projectDAO.save(project);
    }

    @Override
    public void deleteById(Long id) {
        Project project = projectDAO.findById(id);
        if (project == null) {
            throw new AppException("Project not found", "NOT_FOUND");
        }
        projectDAO.deleteById(id);
    }

    @Override
    public List<ProjectResponseDTO> findProjectsByUserId(Long userId) {
        List<Project> projectsByUserId = projectDAO.findProjectsByUserId(userId);
        return projectMapper.entityListToDTOList(projectsByUserId);
    }

    public ProjectResponseDTO saveProject(ProjectRequestDTO projectRequestDTO, Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new AppException("User not found", "NOT_FOUND");
        }

        Project project = projectMapper.requestDtoToEntity(projectRequestDTO);

//        Asignando el usuario al project y persistiendo
        project.setUser(user);
        projectDAO.save(project);
        return projectMapper.entityToDTO(project);
    }

    @Override
    public ProjectResponseDTO updateProject(Long id, Map<String, Object> fields) {
        Project project = projectDAO.findById(id);
        if (project == null) {
            throw new AppException("Project not found", "NOT_FOUND");
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
        return projectMapper.entityToDTO(project);
    }

}
