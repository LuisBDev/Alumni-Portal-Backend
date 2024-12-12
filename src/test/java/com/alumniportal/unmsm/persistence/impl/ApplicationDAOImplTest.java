package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.Application;
import com.alumniportal.unmsm.repository.IApplicationRepository;
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
class ApplicationDAOImplTest {

    @Mock
    private IApplicationRepository applicationRepository;

    @InjectMocks
    private ApplicationDAOImpl applicationDAO;


    @Test
    void findAllReturnsListOfApplicationsWhenApplicationsExist() {
        List<Application> applications = List.of(new Application(), new Application());
        when(applicationRepository.findAll()).thenReturn(applications);

        List<Application> result = applicationDAO.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(applicationRepository, times(1)).findAll();
    }

    @Test
    void findAllReturnsEmptyListWhenNoApplicationsExist() {
        when(applicationRepository.findAll()).thenReturn(List.of());

        List<Application> result = applicationDAO.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(applicationRepository, times(1)).findAll();
    }

    @Test
    void findByIdReturnsApplicationWhenApplicationExists() {
        Application application = new Application();
        application.setId(1L);

        when(applicationRepository.findById(1L)).thenReturn(Optional.of(application));

        Application result = applicationDAO.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(applicationRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdReturnsNullWhenApplicationDoesNotExist() {
        when(applicationRepository.findById(1L)).thenReturn(Optional.empty());

        Application result = applicationDAO.findById(1L);

        assertNull(result);
        verify(applicationRepository, times(1)).findById(1L);
    }

    @Test
    void saveSavesApplicationSuccessfullyWhenValidApplicationProvided() {
        Application application = new Application();
        application.setId(1L);


        applicationDAO.save(application);

        verify(applicationRepository, times(1)).save(application);
    }


    @Test
    void deleteByIdDeletesApplicationSuccessfullyWhenApplicationExists() {
        doNothing().when(applicationRepository).deleteById(1L);

        applicationDAO.deleteById(1L);

        verify(applicationRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdThrowsExceptionWhenApplicationDoesNotExist() {
        doThrow(new RuntimeException("Application not found")).when(applicationRepository).deleteById(1L);

        assertThrows(RuntimeException.class, () -> applicationDAO.deleteById(1L));
        verify(applicationRepository, times(1)).deleteById(1L);
    }

    @Test
    void findApplicationsByUserIdReturnsListOfApplicationsWhenApplicationsExistForUser() {
        List<Application> applications = List.of(new Application(), new Application());
        when(applicationRepository.findApplicationsByUserId(1L)).thenReturn(applications);

        List<Application> result = applicationDAO.findApplicationsByUserId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(applicationRepository, times(1)).findApplicationsByUserId(1L);
    }

    @Test
    void findApplicationsByUserIdReturnsEmptyListWhenNoApplicationsExistForUser() {
        when(applicationRepository.findApplicationsByUserId(1L)).thenReturn(List.of());

        List<Application> result = applicationDAO.findApplicationsByUserId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(applicationRepository, times(1)).findApplicationsByUserId(1L);
    }

    @Test
    void findApplicationsByJobOfferIdReturnsListOfApplicationsWhenApplicationsExistForJobOffer() {
        List<Application> applications = List.of(new Application(), new Application());
        when(applicationRepository.findApplicationsByJobOfferId(1L)).thenReturn(applications);

        List<Application> result = applicationDAO.findApplicationsByJobOfferId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(applicationRepository, times(1)).findApplicationsByJobOfferId(1L);
    }

    @Test
    void findApplicationsByJobOfferIdReturnsEmptyListWhenNoApplicationsExistForJobOffer() {
        when(applicationRepository.findApplicationsByJobOfferId(1L)).thenReturn(List.of());

        List<Application> result = applicationDAO.findApplicationsByJobOfferId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(applicationRepository, times(1)).findApplicationsByJobOfferId(1L);
    }

    @Test
    void findApplicationByUserIdAndJobOfferIdReturnsApplicationWhenApplicationExistsForUserAndJobOffer() {
        Application application = new Application();
        application.setId(1L);

        when(applicationRepository.findApplicationByUserIdAndJobOfferId(1L, 1L)).thenReturn(application);

        Application result = applicationDAO.findApplicationByUserIdAndJobOfferId(1L, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(applicationRepository, times(1)).findApplicationByUserIdAndJobOfferId(1L, 1L);
    }

    @Test
    void findApplicationByUserIdAndJobOfferIdReturnsNullWhenNoApplicationExistsForUserAndJobOffer() {
        when(applicationRepository.findApplicationByUserIdAndJobOfferId(1L, 1L)).thenReturn(null);

        Application result = applicationDAO.findApplicationByUserIdAndJobOfferId(1L, 1L);

        assertNull(result);
        verify(applicationRepository, times(1)).findApplicationByUserIdAndJobOfferId(1L, 1L);
    }

}