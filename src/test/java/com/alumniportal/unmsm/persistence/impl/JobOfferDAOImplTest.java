package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.JobOffer;
import com.alumniportal.unmsm.repository.IJobOfferRepository;
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
class JobOfferDAOImplTest {

    @Mock
    private IJobOfferRepository jobOfferRepository;

    @InjectMocks
    private JobOfferDAOImpl jobOfferDAO;

    @Test
    void findAll_ReturnsListOfJobOffers_WhenJobOffersExist() {
        List<JobOffer> jobOffers = List.of(new JobOffer(), new JobOffer());
        when(jobOfferRepository.findAll()).thenReturn(jobOffers);

        List<JobOffer> result = jobOfferDAO.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jobOfferRepository, times(1)).findAll();
    }

    @Test
    void findAll_ReturnsEmptyList_WhenNoJobOffersExist() {
        when(jobOfferRepository.findAll()).thenReturn(List.of());

        List<JobOffer> result = jobOfferDAO.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(jobOfferRepository, times(1)).findAll();
    }

    @Test
    void findById_ReturnsJobOffer_WhenJobOfferExists() {
        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(1L);

        when(jobOfferRepository.findById(1L)).thenReturn(Optional.of(jobOffer));

        JobOffer result = jobOfferDAO.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(jobOfferRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ReturnsNull_WhenJobOfferDoesNotExist() {
        when(jobOfferRepository.findById(1L)).thenReturn(Optional.empty());

        JobOffer result = jobOfferDAO.findById(1L);

        assertNull(result);
        verify(jobOfferRepository, times(1)).findById(1L);
    }

    @Test
    void save_SavesJobOfferSuccessfully_WhenValidJobOfferProvided() {
        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(1L);
        jobOffer.setTitle("Test Job Offer");

        jobOfferDAO.save(jobOffer);

        verify(jobOfferRepository, times(1)).save(jobOffer);
    }
    

    @Test
    void deleteById_DeletesJobOfferSuccessfully_WhenJobOfferExists() {
        doNothing().when(jobOfferRepository).deleteById(1L);

        jobOfferDAO.deleteById(1L);

        verify(jobOfferRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_ThrowsException_WhenJobOfferDoesNotExist() {
        doThrow(new RuntimeException("Job Offer not found")).when(jobOfferRepository).deleteById(1L);

        assertThrows(RuntimeException.class, () -> jobOfferDAO.deleteById(1L));
        verify(jobOfferRepository, times(1)).deleteById(1L);
    }

    @Test
    void findJobOffersByCompanyId_ReturnsListOfJobOffers_WhenJobOffersExistForCompany() {
        List<JobOffer> jobOffers = List.of(new JobOffer(), new JobOffer());
        when(jobOfferRepository.findJobOffersByCompanyId(1L)).thenReturn(jobOffers);

        List<JobOffer> result = jobOfferDAO.findJobOffersByCompanyId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jobOfferRepository, times(1)).findJobOffersByCompanyId(1L);
    }

    @Test
    void findJobOffersByCompanyId_ReturnsEmptyList_WhenNoJobOffersExistForCompany() {
        when(jobOfferRepository.findJobOffersByCompanyId(1L)).thenReturn(List.of());

        List<JobOffer> result = jobOfferDAO.findJobOffersByCompanyId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(jobOfferRepository, times(1)).findJobOffersByCompanyId(1L);
    }


}