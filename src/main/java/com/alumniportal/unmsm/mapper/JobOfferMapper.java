package com.alumniportal.unmsm.mapper;

import com.alumniportal.unmsm.dto.request.JobOfferRequestDTO;
import com.alumniportal.unmsm.dto.response.JobOfferResponseDTO;
import com.alumniportal.unmsm.model.JobOffer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JobOfferMapper {

    private final ModelMapper modelMapper;

    public JobOfferResponseDTO entityToDTO(JobOffer jobOffer) {
        return modelMapper.map(jobOffer, JobOfferResponseDTO.class);
    }

    public List<JobOfferResponseDTO> entityListToDTOList(List<JobOffer> jobOffers) {
        return jobOffers.stream()
                .map(this::entityToDTO)
                .toList();
    }

    public JobOffer dtoToEntity(JobOfferResponseDTO jobOfferResponseDTO) {
        return modelMapper.map(jobOfferResponseDTO, JobOffer.class);
    }

    public List<JobOffer> dtoListToEntityList(List<JobOfferResponseDTO> jobOfferResponseDTOS) {
        return jobOfferResponseDTOS.stream()
                .map(this::dtoToEntity)
                .toList();
    }

    public JobOffer requestDtoToEntity(JobOfferRequestDTO jobOfferRequestDTO) {
        return modelMapper.map(jobOfferRequestDTO, JobOffer.class);
    }

}
