package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.request.JobOfferRequestDTO;
import com.alumniportal.unmsm.dto.response.JobOfferResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.JobOfferMapper;
import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.model.JobOffer;
import com.alumniportal.unmsm.persistence.interfaces.ICompanyDAO;
import com.alumniportal.unmsm.persistence.interfaces.IJobOfferDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobOfferServiceImplTest {

    @Mock
    private IJobOfferDAO jobOfferDAO;

    @Mock
    private ICompanyDAO companyDAO;


    private JobOfferMapper jobOfferMapper;

    @InjectMocks
    private JobOfferServiceImpl jobOfferService;

    @BeforeEach
    void setUp() {

        jobOfferMapper = new JobOfferMapper(new ModelMapper());
        jobOfferService = new JobOfferServiceImpl(jobOfferDAO, companyDAO, jobOfferMapper);
    }


    @Test
    void findAllReturnsListOfJobOfferResponseDTOs() {
        JobOffer jobOffer = new JobOffer();
        when(jobOfferDAO.findAll()).thenReturn(List.of(jobOffer));
        List<JobOfferResponseDTO> result = jobOfferService.findAll();
        assertEquals(1, result.size());
        verify(jobOfferDAO, times(1)).findAll();
    }

    @Test
    void findAllThrowsExceptionWhenNoJobOffersFound() {
        when(jobOfferDAO.findAll()).thenReturn(Collections.emptyList());
        AppException exception = assertThrows(AppException.class, () -> jobOfferService.findAll());
        assertEquals("No job offers found!", exception.getMessage());
        verify(jobOfferDAO, times(1)).findAll();
    }

    @Test
    void findByIdReturnsJobOfferResponseDTO() {
        JobOffer jobOffer = new JobOffer();
        when(jobOfferDAO.findById(1L)).thenReturn(jobOffer);
        JobOfferResponseDTO result = jobOfferService.findById(1L);
        assertNotNull(result);
        verify(jobOfferDAO, times(1)).findById(1L);
    }

    @Test
    void findByIdThrowsExceptionWhenJobOfferNotFound() {
        when(jobOfferDAO.findById(1L)).thenReturn(null);
        AppException exception = assertThrows(AppException.class, () -> jobOfferService.findById(1L));
        assertEquals("Job offer not found!", exception.getMessage());
        verify(jobOfferDAO, times(1)).findById(1L);
    }

    @Test
    void saveSavesJobOfferSuccessfully() {
        JobOffer jobOffer = new JobOffer();
        jobOfferService.save(jobOffer);
        verify(jobOfferDAO, times(1)).save(jobOffer);
    }

    @Test
    void deleteByIdDeletesJobOfferWhenFound() {
        JobOffer jobOffer = new JobOffer();
        when(jobOfferDAO.findById(1L)).thenReturn(jobOffer);
        jobOfferService.deleteById(1L);
        verify(jobOfferDAO, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdThrowsExceptionWhenJobOfferNotFound() {
        when(jobOfferDAO.findById(1L)).thenReturn(null);
        AppException exception = assertThrows(AppException.class, () -> jobOfferService.deleteById(1L));
        assertEquals("Job offer not found!", exception.getMessage());
        verify(jobOfferDAO, never()).deleteById(1L);
    }

    @Test
    void findJobOffersByCompanyIdReturnsListOfJobOfferResponseDTOs() {
        JobOffer jobOffer = new JobOffer();
        when(jobOfferDAO.findJobOffersByCompanyId(1L)).thenReturn(List.of(jobOffer));
        List<JobOfferResponseDTO> result = jobOfferService.findJobOffersByCompanyId(1L);
        assertEquals(1, result.size());
        verify(jobOfferDAO, times(1)).findJobOffersByCompanyId(1L);
    }

    @Test
    void findJobOffersByCompanyIdThrowsExceptionWhenNoJobOffersFound() {
        when(jobOfferDAO.findJobOffersByCompanyId(1L)).thenReturn(Collections.emptyList());
        AppException exception = assertThrows(AppException.class, () -> jobOfferService.findJobOffersByCompanyId(1L));
        assertEquals("No job offers found for this company!", exception.getMessage());
        verify(jobOfferDAO, times(1)).findJobOffersByCompanyId(1L);
    }

    @Test
    void saveJobOfferSavesJobOfferWhenCompanyExists() {
        JobOfferRequestDTO jobOfferRequestDTO = new JobOfferRequestDTO();
        Company company = new Company();
        when(companyDAO.findById(1L)).thenReturn(company);
        jobOfferService.saveJobOffer(jobOfferRequestDTO, 1L);
        verify(jobOfferDAO, times(1)).save(any(JobOffer.class));
    }

    @Test
    void saveJobOfferThrowsExceptionWhenCompanyNotFound() {
        JobOfferRequestDTO jobOfferRequestDTO = new JobOfferRequestDTO();
        when(companyDAO.findById(1L)).thenReturn(null);
        AppException exception = assertThrows(AppException.class, () -> jobOfferService.saveJobOffer(jobOfferRequestDTO, 1L));
        assertEquals("Company not found!", exception.getMessage());
        verify(jobOfferDAO, never()).save(any(JobOffer.class));
    }

    @Test
    void updateJobOfferUpdatesFieldsWhenJobOfferExists() {
        JobOffer jobOffer = new JobOffer();
        when(jobOfferDAO.findById(1L)).thenReturn(jobOffer);
        Map<String, Object> fields = Map.of("title", "Updated Title");
        jobOfferService.updateJobOffer(1L, fields);
        assertEquals("Updated Title", jobOffer.getTitle());
        verify(jobOfferDAO, times(1)).save(jobOffer);
    }

    @Test
    void updateJobOfferThrowsExceptionWhenJobOfferNotFound() {
        when(jobOfferDAO.findById(1L)).thenReturn(null);
        Map<String, Object> fields = Map.of("title", "Updated Title");
        AppException exception = assertThrows(AppException.class, () -> jobOfferService.updateJobOffer(1L, fields));
        assertEquals("Job offer not found!", exception.getMessage());
        verify(jobOfferDAO, never()).save(any(JobOffer.class));
    }


}