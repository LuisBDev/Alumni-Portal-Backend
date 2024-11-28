package com.alumniportal.unmsm.persistence.interfaces;

import com.alumniportal.unmsm.model.Company;

import java.util.List;

public interface ICompanyDAO {

    List<Company> findAll();

    Company findById(Long id);

    void save(Company company);

    void deleteById(Long id);

    boolean existsByEmail(String email);

    Company findByEmail(String email);
}
