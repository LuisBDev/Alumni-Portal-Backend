package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.persistence.ICompanyDAO;
import com.alumniportal.unmsm.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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


}
