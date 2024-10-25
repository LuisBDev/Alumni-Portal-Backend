package com.alumniportal.unmsm.service;

import com.alumniportal.unmsm.dto.JobOfferDTO;
import com.alumniportal.unmsm.model.JobOffer;

import java.util.List;
import java.util.Map;

public interface IJobOfferService {
    List<JobOfferDTO> findAll();

    JobOfferDTO findById(Long id);

    void save(JobOffer jobOffer);

    void deleteById(Long id);

    List<JobOfferDTO> findJobOffersByCompanyId(Long id);

    void saveJobOffer(JobOffer jobOffer, Long companyId);

    void updateJobOffer(Long id, Map<String, Object> fields);

}
