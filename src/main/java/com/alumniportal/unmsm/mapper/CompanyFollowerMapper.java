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

        CompanyFollowerResponseDTO.CompanyFollowerResponseDTOBuilder dtoBuilder = CompanyFollowerResponseDTO.builder()
                .id(companyFollower.getId())
                .followedAt(companyFollower.getFollowedAt());

        if (companyFollower.getUser() != null) {
            dtoBuilder.userId(companyFollower.getUser().getId())
                    .userName(companyFollower.getUser().getName())
                    .userEmail(companyFollower.getUser().getEmail())
                    .userPaternalSurname(companyFollower.getUser().getPaternalSurname())
                    .userMaternalSurname(companyFollower.getUser().getMaternalSurname());
        }

        if (companyFollower.getCompany() != null) {
            dtoBuilder.companyId(companyFollower.getCompany().getId())
                    .companyName(companyFollower.getCompany().getName())
                    .companyEmail(companyFollower.getCompany().getEmail())
                    .companyRuc(companyFollower.getCompany().getRuc())
                    .companySector(companyFollower.getCompany().getSector())
                    .companyPhotoUrl(companyFollower.getCompany().getPhotoUrl());
        }

        return dtoBuilder.build();
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
