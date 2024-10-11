package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.CompanyDTO;
import com.alumniportal.unmsm.dto.PasswordChangeDTO;
import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.ICompanyDAO;
import com.alumniportal.unmsm.service.ICompanyService;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CompanyDTO> findAll() {
        return companyDAO.findAll()
                .stream()
                .map(company -> modelMapper.map(company, CompanyDTO.class))
                .toList();
    }

    @Override
    public CompanyDTO findById(Long id) {
        Company company = companyDAO.findById(id);
        if (company == null) {
            return null;
        }
        return modelMapper.map(company, CompanyDTO.class);
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
    public CompanyDTO findByEmail(String email) {
        Company company = companyDAO.findByEmail(email);
        if (company == null) {
            return null;
        }
        return modelMapper.map(company, CompanyDTO.class);
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

    @Override
    public CompanyDTO validateLogin(String email, String password) {
        Company company = companyDAO.findByEmail(email);
        if (company == null) {
            throw new RuntimeException("Error: Company not found!");
        }
        if (!company.getPassword().equals(password)) {
            throw new RuntimeException("Error: Password incorrect!");
        }
        return modelMapper.map(company, CompanyDTO.class);
    }

    @Override
    public void updatePassword(Long id, PasswordChangeDTO passwordChangeDTO) {
        Company company = companyDAO.findById(id);
        if (company == null) {
            throw new RuntimeException("Error: Company not found!");
        }
        if(!company.getEmail().equals(passwordChangeDTO.getEmail())){
            throw new RuntimeException("Error: Invalid email!");
        }

        if (!company.getPassword().equals(passwordChangeDTO.getPassword())) {
            throw new RuntimeException("Error: Tu old password no coincide en la bd!");
        }
        company.setPassword(passwordChangeDTO.getNewPassword());
        company.setUpdatedAt(LocalDate.now());
        companyDAO.save(company);
    }

}
