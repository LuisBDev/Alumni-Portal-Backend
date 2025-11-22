package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.response.CompanyResponseDTO;
import com.alumniportal.unmsm.dto.request.PasswordChangeRequestDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.CompanyMapper;
import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.persistence.interfaces.ICompanyDAO;
import com.alumniportal.unmsm.service.interfaces.IActivityService;
import com.alumniportal.unmsm.service.interfaces.ICompanyService;
import com.alumniportal.unmsm.util.ImageManagement;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements ICompanyService {

    private final ICompanyDAO companyDAO;

    private final CompanyMapper companyMapper;

    private final ImageManagement imageManagement;

    private final IActivityService activityService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<CompanyResponseDTO> findAll() {
        List<Company> companyList = companyDAO.findAll();

        return companyMapper.entityListToDTOList(companyList);
    }

    @Override
    public CompanyResponseDTO findById(Long id) {
        Company company = companyDAO.findById(id);
        if (company == null) {
            throw new AppException("Error: Company not found!", "NOT_FOUND");
        }
        return companyMapper.entityToDTO(company);
    }

    @Override
    public void save(Company company) {
        companyDAO.save(company);
    }

    @Override
    public void deleteById(Long id) {
        Company company = companyDAO.findById(id);
        if (company == null) {
            throw new AppException("Error: Company not found!", "NOT_FOUND");
        }
        if (company.getPhotoUrl() != null) {
            imageManagement.deleteImageByUrl(company.getPhotoUrl());
        }

        if (!company.getActivityList().isEmpty()) {
            company.getActivityList().stream()
                    .filter(activity -> activity.getUrl() != null)
                    .forEach(activity -> {
                        try {
                            activityService.deleteActivityImage(activity.getId());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
        }

        companyDAO.deleteById(id);

    }

    @Override
    public boolean existsByEmail(String email) {
        return companyDAO.existsByEmail(email);
    }

    @Override
    public CompanyResponseDTO findByEmail(String email) {
        Company company = companyDAO.findByEmail(email);
        if (company == null) {
            throw new AppException("Error: Company not found!", "NOT_FOUND");
        }
        return companyMapper.entityToDTO(company);
    }

    @Override
    public CompanyResponseDTO saveCompany(Company company) {
        boolean companyDB = companyDAO.existsByEmail(company.getEmail());
        if (companyDB) {
            throw new AppException("Error: Company already exists!", "CONFLICT");
        }
        company.setCreatedAt(LocalDate.now());
        companyDAO.save(company);
        return companyMapper.entityToDTO(company);
    }

    public CompanyResponseDTO updateCompany(Long id, Map<String, Object> fields) {
        Company companyFound = companyDAO.findById(id);
        if (companyFound == null) {
            throw new AppException("Error: Company not found!", "NOT_FOUND");
        }
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Company.class, k);
            field.setAccessible(true);
            Object valueToSet = (v instanceof String && ((String) v).trim().isEmpty()) ? null : v;
            ReflectionUtils.setField(field, companyFound, valueToSet);

        });
        companyFound.setUpdatedAt(LocalDate.now());
        companyDAO.save(companyFound);
        return companyMapper.entityToDTO(companyFound);
    }


    @Override
    public void updatePassword(Long id, PasswordChangeRequestDTO passwordChangeRequestDTO) {
        Company company = companyDAO.findById(id);
        if (company == null) {
            throw new AppException("Error: Company not found!", "NOT_FOUND");
        }
        if (!company.getEmail().equals(passwordChangeRequestDTO.getEmail())) {
            throw new AppException("Error: Email does not match!", "BAD_REQUEST");
        }

        if (!passwordEncoder.matches(passwordChangeRequestDTO.getPassword(), company.getPassword())) {
            throw new AppException("Error: Old password does not match!", "BAD_REQUEST");
        }
        company.setPassword(passwordEncoder.encode(passwordChangeRequestDTO.getNewPassword()));
        company.setUpdatedAt(LocalDate.now());
        companyDAO.save(company);
    }

}
