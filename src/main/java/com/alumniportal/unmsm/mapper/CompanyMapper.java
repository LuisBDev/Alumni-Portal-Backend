package com.alumniportal.unmsm.mapper;

import com.alumniportal.unmsm.dto.request.CompanyRequestDTO;
import com.alumniportal.unmsm.dto.response.AuthCompanyResponseDTO;
import com.alumniportal.unmsm.dto.response.CompanyResponseDTO;
import com.alumniportal.unmsm.model.Company;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompanyMapper {

    private final ModelMapper modelMapper;

    public CompanyResponseDTO entityToDTO(Company company) {
        return modelMapper.map(company, CompanyResponseDTO.class);
    }

    public List<CompanyResponseDTO> entityListToDTOList(List<Company> companies) {
        return companies.stream()
                .map(this::entityToDTO)
                .toList();
    }

    public Company dtoToEntity(CompanyResponseDTO companyResponseDTO) {
        return modelMapper.map(companyResponseDTO, Company.class);
    }

    public List<Company> dtoListToEntityList(List<CompanyResponseDTO> companyResponseDTOS) {
        return companyResponseDTOS.stream()
                .map(this::dtoToEntity)
                .toList();
    }

    public Company requestDtoToEntity(CompanyRequestDTO companyRequestDTO) {
        return modelMapper.map(companyRequestDTO, Company.class);
    }

    public AuthCompanyResponseDTO entityToAuthCompanyResponseDTO(Company company) {
        return modelMapper.map(company, AuthCompanyResponseDTO.class);
    }

}
