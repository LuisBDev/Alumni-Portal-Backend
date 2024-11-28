package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.repository.ICompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CompanyDAOImplTest {

    @Mock
    private ICompanyRepository companyRepository;

    @InjectMocks
    private CompanyDAOImpl companyDAO;

    @Test
    void findAll_ReturnsListOfCompanies_WhenCompaniesExist() {
        List<Company> companies = List.of(new Company(), new Company());
        when(companyRepository.findAll()).thenReturn(companies);

        List<Company> result = companyDAO.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(companyRepository, times(1)).findAll();
    }

    @Test
    void findAll_ReturnsEmptyList_WhenNoCompaniesExist() {
        when(companyRepository.findAll()).thenReturn(List.of());

        List<Company> result = companyDAO.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(companyRepository, times(1)).findAll();
    }

    @Test
    void findById_ReturnsCompany_WhenCompanyExists() {
        Company company = new Company();
        company.setId(1L);

        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        Company result = companyDAO.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(companyRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ReturnsNull_WhenCompanyDoesNotExist() {
        when(companyRepository.findById(1L)).thenReturn(Optional.empty());

        Company result = companyDAO.findById(1L);

        assertNull(result);
        verify(companyRepository, times(1)).findById(1L);
    }

    @Test
    void save_SavesCompanySuccessfully_WhenValidCompanyProvided() {
        Company company = new Company();
        company.setId(1L);
        company.setName("Test Company");

        companyDAO.save(company);

        verify(companyRepository, times(1)).save(company);
    }


    @Test
    void deleteById_DeletesCompanySuccessfully_WhenCompanyExists() {
        doNothing().when(companyRepository).deleteById(1L);

        companyDAO.deleteById(1L);

        verify(companyRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_ThrowsException_WhenCompanyDoesNotExist() {
        doThrow(new RuntimeException("Company not found")).when(companyRepository).deleteById(1L);

        assertThrows(RuntimeException.class, () -> companyDAO.deleteById(1L));
        verify(companyRepository, times(1)).deleteById(1L);
    }

    @Test
    void existsByEmail_ReturnsTrue_WhenCompanyExistsWithEmail() {
        when(companyRepository.existsByEmail("test@example.com")).thenReturn(true);

        boolean result = companyDAO.existsByEmail("test@example.com");

        assertTrue(result);
        verify(companyRepository, times(1)).existsByEmail("test@example.com");
    }

    @Test
    void existsByEmail_ReturnsFalse_WhenNoCompanyExistsWithEmail() {
        when(companyRepository.existsByEmail("test@example.com")).thenReturn(false);

        boolean result = companyDAO.existsByEmail("test@example.com");

        assertFalse(result);
        verify(companyRepository, times(1)).existsByEmail("test@example.com");
    }

    @Test
    void findByEmail_ReturnsCompany_WhenCompanyExistsWithEmail() {
        Company company = new Company();
        company.setEmail("test@example.com");

        when(companyRepository.findByEmail("test@example.com")).thenReturn(company);

        Company result = companyDAO.findByEmail("test@example.com");

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(companyRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void findByEmail_ReturnsNull_WhenNoCompanyExistsWithEmail() {
        when(companyRepository.findByEmail("test@example.com")).thenReturn(null);

        Company result = companyDAO.findByEmail("test@example.com");

        assertNull(result);
        verify(companyRepository, times(1)).findByEmail("test@example.com");
    }


}