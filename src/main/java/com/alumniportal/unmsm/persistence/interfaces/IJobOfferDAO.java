package com.alumniportal.unmsm.persistence.interfaces;

import com.alumniportal.unmsm.model.JobOffer;

import java.util.List;

public interface IJobOfferDAO {

    List<JobOffer> findAll();

    JobOffer findById(Long id);

    void save(JobOffer jobOffer);

    void deleteById(Long id);

    List<JobOffer> findJobOffersByCompanyId(Long id);


}
