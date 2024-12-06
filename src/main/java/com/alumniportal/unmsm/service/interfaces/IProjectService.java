package com.alumniportal.unmsm.service.interfaces;

import com.alumniportal.unmsm.dto.request.ProjectRequestDTO;
import com.alumniportal.unmsm.dto.response.ProjectResponseDTO;
import com.alumniportal.unmsm.model.Project;

import java.util.List;
import java.util.Map;

public interface IProjectService {

    List<ProjectResponseDTO> findAll();

    ProjectResponseDTO findById(Long id);

    void save(Project project);

    void deleteById(Long id);

    //    Buscar todos los projects de un usuario
    List<ProjectResponseDTO> findProjectsByUserId(Long userId);

    void saveProject(ProjectRequestDTO projectRequestDTO, Long userId);

    void updateProject(Long id, Map<String, Object> fields);
}
