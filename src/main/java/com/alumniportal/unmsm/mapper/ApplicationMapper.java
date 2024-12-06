package com.alumniportal.unmsm.mapper;

import com.alumniportal.unmsm.dto.request.ApplicationRequestDTO;
import com.alumniportal.unmsm.dto.response.ApplicationResponseDTO;
import com.alumniportal.unmsm.model.Application;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicationMapper {

    private final ModelMapper modelMapper;


    public ApplicationResponseDTO entityToDTO(Application application) {
        return modelMapper.map(application, ApplicationResponseDTO.class);
    }

    public List<ApplicationResponseDTO> entityListToDTOList(List<Application> applications) {
        return applications.stream()
                .map(this::entityToDTO)
                .toList();
    }

    public Application dtoToEntity(ApplicationResponseDTO applicationResponseDTO) {
        return modelMapper.map(applicationResponseDTO, Application.class);
    }

    public List<Application> dtoListToEntityList(List<ApplicationResponseDTO> applicationResponseDTOS) {
        return applicationResponseDTOS.stream()
                .map(this::dtoToEntity)
                .toList();
    }

    public Application requestDtoToEntity(ApplicationRequestDTO applicationRequestDTO) {
        return modelMapper.map(applicationRequestDTO, Application.class);
    }

}

