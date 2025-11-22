package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.request.JobOfferRequestDTO;
import com.alumniportal.unmsm.dto.response.JobOfferResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.JobOfferMapper;
import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.model.JobOffer;
import com.alumniportal.unmsm.persistence.interfaces.ICompanyDAO;
import com.alumniportal.unmsm.persistence.interfaces.IJobOfferDAO;
import com.alumniportal.unmsm.service.interfaces.IJobOfferService;
import lombok.RequiredArgsConstructor;
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

    private final JobOfferMapper jobOfferMapper;

    @Override
    public List<JobOfferResponseDTO> findAll() {
        List<JobOffer> jobOfferList = jobOfferDAO.findAll();

        return jobOfferMapper.entityListToDTOList(jobOfferList);
    }

    @Override
    public JobOfferResponseDTO findById(Long id) {
        JobOffer jobOffer = jobOfferDAO.findById(id);
        if (jobOffer == null) {
            throw new AppException("Job offer not found!", "NOT_FOUND");
        }
        return jobOfferMapper.entityToDTO(jobOffer);
    }

    @Override
    public void save(JobOffer jobOffer) {
        jobOfferDAO.save(jobOffer);
    }

    @Override
    public void deleteById(Long id) {
        JobOffer jobOffer = jobOfferDAO.findById(id);
        if (jobOffer == null) {
            throw new AppException("Job offer not found!", "NOT_FOUND");
        }
        jobOfferDAO.deleteById(id);
    }

    @Override
    public List<JobOfferResponseDTO> findJobOffersByCompanyId(Long id) {
        List<JobOffer> jobOffersByCompanyId = jobOfferDAO.findJobOffersByCompanyId(id);

        return jobOfferMapper.entityListToDTOList(jobOffersByCompanyId);
    }

    @Override
    public JobOfferResponseDTO saveJobOffer(JobOfferRequestDTO jobOfferRequestDTO, Long companyId) {
        Company company = companyDAO.findById(companyId);
        if (company == null) {
            throw new AppException("Company not found!", "NOT_FOUND");
        }

        JobOffer jobOffer = jobOfferMapper.requestDtoToEntity(jobOfferRequestDTO);

        jobOffer.setCompany(company);
        jobOffer.setCreatedAt(LocalDate.now());
        jobOfferDAO.save(jobOffer);

        return jobOfferMapper.entityToDTO(jobOffer);
    }

    @Override
    public JobOfferResponseDTO updateJobOffer(Long id, Map<String, Object> fields) {
        JobOffer jobOffer = jobOfferDAO.findById(id);
        if (jobOffer == null) {
            throw new AppException("Job offer not found!", "NOT_FOUND");
        }
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(JobOffer.class, k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, jobOffer, v);
        });
        jobOffer.setUpdatedAt(LocalDate.now());
        jobOfferDAO.save(jobOffer);
        return jobOfferMapper.entityToDTO(jobOffer);
    }
}
