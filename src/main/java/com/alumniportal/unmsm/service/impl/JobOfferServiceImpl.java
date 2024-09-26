package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.model.JobOffer;
import com.alumniportal.unmsm.persistence.IJobOfferDAO;
import com.alumniportal.unmsm.service.ICompanyService;
import com.alumniportal.unmsm.service.IJobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class JobOfferServiceImpl implements IJobOfferService {

    @Autowired
    private IJobOfferDAO jobOfferDAO;

    @Autowired
    private ICompanyService companyService;

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

    @Override
    public void saveJobOffer(JobOffer jobOffer, Long companyId) {
        Company company = companyService.findById(companyId);
        if (company == null) {
            throw new RuntimeException("Company not found!");
        }
        jobOffer.setCompany(company);
        jobOffer.setCreatedAt(LocalDate.now());
        jobOfferDAO.save(jobOffer);

        company.getJobOfferList().add(jobOffer);
        companyService.save(company);
    }
}
