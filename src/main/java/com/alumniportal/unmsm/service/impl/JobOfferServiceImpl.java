package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.model.JobOffer;
import com.alumniportal.unmsm.persistence.IJobOfferDAO;
import com.alumniportal.unmsm.service.IJobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobOfferServiceImpl implements IJobOfferService {

    @Autowired
    private IJobOfferDAO jobOfferDAO;

    @Override
    public List<JobOffer> findAll() {
        return jobOfferDAO.findAll();
    }

    @Override
    public JobOffer findById(Long id) {
        return jobOfferDAO.findById(id);
    }

    @Override
    public void save(JobOffer jobOffer) {
        jobOfferDAO.save(jobOffer);
    }

    @Override
    public void deleteById(Long id) {
        jobOfferDAO.deleteById(id);
    }

    @Override
    public List<JobOffer> findJobOffersByCompany_Id(Long id) {
        return jobOfferDAO.findJobOffersByCompany_Id(id);
    }
}
