package com.alumniportal.unmsm.mapper;

import com.alumniportal.unmsm.dto.request.CompanyFollowerRequestDTO;
import com.alumniportal.unmsm.dto.response.CompanyFollowerResponseDTO;
import com.alumniportal.unmsm.model.CompanyFollower;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompanyFollowerMapper {

    private final ModelMapper modelMapper;

    public CompanyFollowerResponseDTO entityToDTO(CompanyFollower companyFollower) {
        CompanyFollowerResponseDTO dto = new CompanyFollowerResponseDTO();
        dto.setId(companyFollower.getId());
        dto.setFollowedAt(companyFollower.getFollowedAt());
        
        if (companyFollower.getUser() != null) {
            dto.setUserId(companyFollower.getUser().getId());
            dto.setUserName(companyFollower.getUser().getName());
            dto.setUserEmail(companyFollower.getUser().getEmail());
            dto.setUserPaternalSurname(companyFollower.getUser().getPaternalSurname());
            dto.setUserMaternalSurname(companyFollower.getUser().getMaternalSurname());
        }
        
        if (companyFollower.getCompany() != null) {
            dto.setCompanyId(companyFollower.getCompany().getId());
            dto.setCompanyName(companyFollower.getCompany().getName());
            dto.setCompanyEmail(companyFollower.getCompany().getEmail());
            dto.setCompanyRuc(companyFollower.getCompany().getRuc());
            dto.setCompanySector(companyFollower.getCompany().getSector());
            dto.setCompanyPhotoUrl(companyFollower.getCompany().getPhotoUrl());
        }
        
        return dto;
    }

    public List<CompanyFollowerResponseDTO> entityListToDTOList(List<CompanyFollower> companyFollowers) {
        return companyFollowers.stream()
                .map(this::entityToDTO)
                .toList();
    }

    public CompanyFollower dtoToEntity(CompanyFollowerResponseDTO companyFollowerResponseDTO) {
        return modelMapper.map(companyFollowerResponseDTO, CompanyFollower.class);
    }

    public List<CompanyFollower> dtoListToEntityList(List<CompanyFollowerResponseDTO> companyFollowerResponseDTOS) {
        return companyFollowerResponseDTOS.stream()
                .map(this::dtoToEntity)
                .toList();
    }

    public CompanyFollower requestDtoToEntity(CompanyFollowerRequestDTO companyFollowerRequestDTO) {
        return modelMapper.map(companyFollowerRequestDTO, CompanyFollower.class);
    }
}

