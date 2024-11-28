package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.JobOfferDTO;
import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.model.JobOffer;
import com.alumniportal.unmsm.persistence.interfaces.ICompanyDAO;
import com.alumniportal.unmsm.persistence.interfaces.IJobOfferDAO;
import com.alumniportal.unmsm.service.interfaces.IJobOfferService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JobOfferServiceImpl implements IJobOfferService {

    private final IJobOfferDAO jobOfferDAO;

    private final ICompanyDAO companyDAO;

    private final ModelMapper modelMapper;

    @Override
    public List<JobOfferDTO> findAll() {
        return jobOfferDAO.findAll()
                .stream()
                .map(jobOffer -> modelMapper.map(jobOffer, JobOfferDTO.class))
                .toList();
    }

    @Override
    public JobOfferDTO findById(Long id) {
        JobOffer jobOffer = jobOfferDAO.findById(id);
        if (jobOffer == null) {
            return null;
        }
        return modelMapper.map(jobOffer, JobOfferDTO.class);
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
    public List<JobOfferDTO> findJobOffersByCompanyId(Long id) {
        return jobOfferDAO.findJobOffersByCompanyId(id)
                .stream()
                .map(jobOffer -> modelMapper.map(jobOffer, JobOfferDTO.class))
                .toList();
    }

    @Override
    public void saveJobOffer(JobOffer jobOffer, Long companyId) {
        Company company = companyDAO.findById(companyId);
        if (company == null) {
            throw new RuntimeException("Company not found!");
        }
        jobOffer.setCompany(company);
        jobOffer.setCreatedAt(LocalDate.now());
        jobOfferDAO.save(jobOffer);

        company.getJobOfferList().add(jobOffer);
        companyDAO.save(company);
    }

    @Override
    public void updateJobOffer(Long id, Map<String, Object> fields) {
        JobOffer jobOffer = jobOfferDAO.findById(id);
        if (jobOffer == null) {
            throw new RuntimeException("Error: jobOffer not found!");
        }
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(JobOffer.class, k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, jobOffer, v);
        });
        jobOfferDAO.save(jobOffer);
    }
}
