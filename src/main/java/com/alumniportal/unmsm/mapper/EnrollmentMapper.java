package com.alumniportal.unmsm.mapper;

import com.alumniportal.unmsm.dto.request.EnrollmentRequestDTO;
import com.alumniportal.unmsm.dto.response.EnrollmentResponseDTO;
import com.alumniportal.unmsm.model.Enrollment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EnrollmentMapper {

    private final ModelMapper modelMapper;


    public EnrollmentResponseDTO entityToDTO(Enrollment enrollment) {
        return modelMapper.map(enrollment, EnrollmentResponseDTO.class);
    }

    public List<EnrollmentResponseDTO> entityListToDTOList(List<Enrollment> enrollments) {
        return enrollments.stream()
                .map(this::entityToDTO)
                .toList();
    }

    public Enrollment dtoToEntity(EnrollmentResponseDTO enrollmentResponseDTO) {
        return modelMapper.map(enrollmentResponseDTO, Enrollment.class);
    }

    public List<Enrollment> dtoListToEntityList(List<EnrollmentResponseDTO> enrollmentResponseDTOS) {
        return enrollmentResponseDTOS.stream()
                .map(this::dtoToEntity)
                .toList();
    }

    public Enrollment requestDtoToEntity(EnrollmentRequestDTO enrollmentRequestDTO) {
        return modelMapper.map(enrollmentRequestDTO, Enrollment.class);
    }
}
