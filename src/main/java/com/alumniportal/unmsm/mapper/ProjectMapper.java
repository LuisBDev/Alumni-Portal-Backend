package com.alumniportal.unmsm.mapper;

import com.alumniportal.unmsm.dto.request.ProjectRequestDTO;
import com.alumniportal.unmsm.dto.response.ProjectResponseDTO;
import com.alumniportal.unmsm.model.Project;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectMapper {
    private final ModelMapper modelMapper;


    public ProjectResponseDTO entityToDTO(Project project) {
        return modelMapper.map(project, ProjectResponseDTO.class);
    }

    public List<ProjectResponseDTO> entityListToDTOList(List<Project> projects) {
        return projects.stream()
                .map(this::entityToDTO)
                .toList();
    }

    public Project dtoToEntity(ProjectResponseDTO projectResponseDTO) {
        return modelMapper.map(projectResponseDTO, Project.class);
    }

    public List<Project> dtoListToEntityList(List<ProjectResponseDTO> projectResponseDTOS) {
        return projectResponseDTOS.stream()
                .map(this::dtoToEntity)
                .toList();
    }

    public Project requestDtoToEntity(ProjectRequestDTO projectRequestDTO) {
        return modelMapper.map(projectRequestDTO, Project.class);
    }

}
