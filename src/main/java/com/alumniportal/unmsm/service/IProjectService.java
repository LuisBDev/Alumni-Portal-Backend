package com.alumniportal.unmsm.service;

import com.alumniportal.unmsm.dto.ProjectDTO;
import com.alumniportal.unmsm.model.Project;

import java.util.List;

public interface IProjectService {

    List<ProjectDTO> findAll();

    ProjectDTO findById(Long id);

    void save(Project project);

    void deleteById(Long id);

    //    Buscar todos los projects de un usuario
    List<ProjectDTO> findProjectsByUser_Id(Long userId);

    void saveProject(Project project, Long userId);
}
