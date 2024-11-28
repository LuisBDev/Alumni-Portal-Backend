package com.alumniportal.unmsm.service.interfaces;

import com.alumniportal.unmsm.dto.CompanyDTO;
import com.alumniportal.unmsm.dto.PasswordChangeDTO;
import com.alumniportal.unmsm.model.Company;

import java.util.List;
import java.util.Map;

public interface ICompanyService {
    List<CompanyDTO> findAll();

    CompanyDTO findById(Long id);

    void save(Company company);

    void deleteById(Long id);

    boolean existsByEmail(String email);

    CompanyDTO findByEmail(String email);

    void saveCompany(Company company);

    void updateCompany(Long id, Map<String, Object> fields);

    CompanyDTO validateLogin(String email, String password);

    void updatePassword(Long id, PasswordChangeDTO passwordChangeDTO);
}
