package com.alumniportal.unmsm.mapper;

import com.alumniportal.unmsm.dto.RequestDTO.CertificationRequestDTO;
import com.alumniportal.unmsm.dto.ResponseDTO.CertificationResponseDTO;
import com.alumniportal.unmsm.model.Certification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CertificationMapper {
    private final ModelMapper modelMapper;


    public CertificationResponseDTO entityToDTO(Certification certification) {
        return modelMapper.map(certification, CertificationResponseDTO.class);
    }

    public List<CertificationResponseDTO> entityListToDTOList(List<Certification> certifications) {
        return certifications.stream()
                .map(this::entityToDTO)
                .toList();
    }

    public Certification dtoToEntity(CertificationResponseDTO certificationResponseDTO) {
        return modelMapper.map(certificationResponseDTO, Certification.class);
    }

    public List<Certification> dtoListToEntityList(List<CertificationResponseDTO> certificationResponseDTOS) {
        return certificationResponseDTOS.stream()
                .map(this::dtoToEntity)
                .toList();
    }

    public Certification requestDtoToEntity(CertificationRequestDTO certificationRequestDTO) {
        return modelMapper.map(certificationRequestDTO, Certification.class);
    }

}
