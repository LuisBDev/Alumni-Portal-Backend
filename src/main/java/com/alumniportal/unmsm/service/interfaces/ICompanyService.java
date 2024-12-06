package com.alumniportal.unmsm.service.interfaces;

import com.alumniportal.unmsm.dto.response.CompanyResponseDTO;
import com.alumniportal.unmsm.dto.request.PasswordChangeRequestDTO;
import com.alumniportal.unmsm.model.Company;

import java.util.List;
import java.util.Map;

public interface ICompanyService {
    List<CompanyResponseDTO> findAll();

    CompanyResponseDTO findById(Long id);

    void save(Company company);

    void deleteById(Long id);

    boolean existsByEmail(String email);

    CompanyResponseDTO findByEmail(String email);

    void saveCompany(Company company);

    void updateCompany(Long id, Map<String, Object> fields);

    void updatePassword(Long id, PasswordChangeRequestDTO passwordChangeRequestDTO);
}
