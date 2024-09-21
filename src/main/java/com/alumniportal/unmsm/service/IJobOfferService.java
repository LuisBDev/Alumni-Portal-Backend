package com.alumniportal.unmsm.service;

import com.alumniportal.unmsm.model.JobOffer;

import java.util.List;

public interface IJobOfferService {
    List<JobOffer> findAll();

    JobOffer findById(Long id);

    void save(JobOffer jobOffer);

    void deleteById(Long id);

    List<JobOffer> findJobOffersByCompany_Id(Long id);

}
