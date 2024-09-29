package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.persistence.ICompanyDAO;
import com.alumniportal.unmsm.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class CompanyServiceImpl implements ICompanyService {

    @Autowired
    private ICompanyDAO companyDAO;

    @Override
    public List<Company> findAll() {
        return companyDAO.findAll();
    }

    @Override
    public Company findById(Long id) {
        return companyDAO.findById(id);
    }

    @Override
    public void save(Company company) {

        companyDAO.save(company);
    }

    @Override
    public void deleteById(Long id) {

        companyDAO.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return companyDAO.existsByEmail(email);
    }

    @Override
    public Company findByEmail(String email) {
        return companyDAO.findByEmail(email);
    }

    @Override
    public void saveCompany(Company company) {
        boolean companyDB = companyDAO.existsByEmail(company.getEmail());
        if (companyDB) {
            throw new RuntimeException("Error: Email already exists!");
        }
        company.setCreatedAt(LocalDate.now());
        companyDAO.save(company);
    }

    public void updateCompany(Long id, Map<String, Object> fields) {
        Company companyFound = companyDAO.findById(id);
        if (companyFound == null) {
            throw new RuntimeException("Error: Company not found!");
        }
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Company.class, k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, companyFound, v);
        });
        companyFound.setUpdatedAt(LocalDate.now());
        companyDAO.save(companyFound);
    }


}
