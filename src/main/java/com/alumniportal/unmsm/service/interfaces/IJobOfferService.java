package com.alumniportal.unmsm.service.interfaces;

import com.alumniportal.unmsm.dto.request.JobOfferRequestDTO;
import com.alumniportal.unmsm.dto.response.JobOfferResponseDTO;
import com.alumniportal.unmsm.model.JobOffer;

import java.util.List;
import java.util.Map;

public interface IJobOfferService {
    List<JobOfferResponseDTO> findAll();

    JobOfferResponseDTO findById(Long id);

    void save(JobOffer jobOffer);

    void deleteById(Long id);

    List<JobOfferResponseDTO> findJobOffersByCompanyId(Long id);

    JobOfferResponseDTO saveJobOffer(JobOfferRequestDTO jobOfferRequestDTO, Long companyId);

    JobOfferResponseDTO updateJobOffer(Long id, Map<String, Object> fields);

}
