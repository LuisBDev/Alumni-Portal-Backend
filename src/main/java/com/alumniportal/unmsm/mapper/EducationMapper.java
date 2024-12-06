package com.alumniportal.unmsm.mapper;

import com.alumniportal.unmsm.dto.request.EducationRequestDTO;
import com.alumniportal.unmsm.dto.response.EducationResponseDTO;
import com.alumniportal.unmsm.model.Education;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EducationMapper {

    private final ModelMapper modelMapper;

    public EducationResponseDTO entityToDTO(Education education) {
        return modelMapper.map(education, EducationResponseDTO.class);
    }

    public List<EducationResponseDTO> entityListToDTOList(List<Education> educations) {
        return educations.stream()
                .map(this::entityToDTO)
                .toList();
    }

    public Education dtoToEntity(EducationResponseDTO educationResponseDTO) {
        return modelMapper.map(educationResponseDTO, Education.class);
    }

    public List<Education> dtoListToEntityList(List<EducationResponseDTO> educationResponseDTOS) {
        return educationResponseDTOS.stream()
                .map(this::dtoToEntity)
                .toList();
    }

    public Education requestDtoToEntity(EducationRequestDTO educationRequestDTO) {
        return modelMapper.map(educationRequestDTO, Education.class);
    }

}
