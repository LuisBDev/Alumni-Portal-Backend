package com.alumniportal.unmsm.mapper;

import com.alumniportal.unmsm.dto.request.WorkExperienceRequestDTO;
import com.alumniportal.unmsm.dto.response.WorkExperienceResponseDTO;
import com.alumniportal.unmsm.model.WorkExperience;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkExperienceMapper {

    private final ModelMapper modelMapper;

    public WorkExperienceResponseDTO entityToDTO(WorkExperience workExperience) {
        return modelMapper.map(workExperience, WorkExperienceResponseDTO.class);
    }

    public List<WorkExperienceResponseDTO> entityListToDTOList(List<WorkExperience> workExperiences) {
        return workExperiences.stream()
                .map(this::entityToDTO)
                .toList();
    }

    public WorkExperience dtoToEntity(WorkExperienceResponseDTO workExperienceResponseDTO) {
        return modelMapper.map(workExperienceResponseDTO, WorkExperience.class);
    }

    public List<WorkExperience> dtoListToEntityList(List<WorkExperienceResponseDTO> workExperienceResponseDTOS) {
        return workExperienceResponseDTOS.stream()
                .map(this::dtoToEntity)
                .toList();
    }

    public WorkExperience requestDtoToEntity(WorkExperienceRequestDTO workExperienceRequestDTO) {
        return modelMapper.map(workExperienceRequestDTO, WorkExperience.class);
    }
    
}
