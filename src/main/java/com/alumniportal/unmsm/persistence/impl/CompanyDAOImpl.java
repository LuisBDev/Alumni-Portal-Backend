package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.persistence.ICompanyDAO;
import com.alumniportal.unmsm.repository.ICompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompanyDAOImpl implements ICompanyDAO {


    private final ICompanyRepository companyRepository;


    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company findById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Company company) {
        companyRepository.save(company);
    }

    @Override
    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return companyRepository.existsByEmail(email);
    }

    @Override
    public Company findByEmail(String email) {
        return companyRepository.findByEmail(email);
    }
}
