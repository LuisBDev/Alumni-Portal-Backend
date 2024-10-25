package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.JobOffer;
import com.alumniportal.unmsm.persistence.IJobOfferDAO;
import com.alumniportal.unmsm.repository.IJobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobOfferDAOImpl implements IJobOfferDAO {

    @Autowired
    private IJobOfferRepository jobOfferRepository;


    @Override
    public List<JobOffer> findAll() {
        return jobOfferRepository.findAll();
    }

    @Override
    public JobOffer findById(Long id) {
        return jobOfferRepository.findById(id).orElse(null);
    }

    @Override
    public void save(JobOffer jobOffer) {

        jobOfferRepository.save(jobOffer);
    }

    @Override
    public void deleteById(Long id) {
        jobOfferRepository.deleteById(id);
    }

    @Override
    public List<JobOffer> findJobOffersByCompanyId(Long id) {
        return jobOfferRepository.findJobOffersByCompanyId(id);
    }
}
