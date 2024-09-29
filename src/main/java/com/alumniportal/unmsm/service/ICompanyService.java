package com.alumniportal.unmsm.service;

import com.alumniportal.unmsm.model.Company;

import java.util.List;
import java.util.Map;

public interface ICompanyService {
    List<Company> findAll();

    Company findById(Long id);

    void save(Company company);

    void deleteById(Long id);

    boolean existsByEmail(String email);

    Company findByEmail(String email);

    void saveCompany(Company company);

    void updateCompany(Long id, Map<String, Object> fields);
}
