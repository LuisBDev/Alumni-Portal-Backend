package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.Certification;
import com.alumniportal.unmsm.repository.ICertificationRepository;
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
class CertificationDAOImplTest {

    @Mock
    private ICertificationRepository certificationRepository;

    @InjectMocks
    private CertificationDAOImpl certificationDAO;

    @Test
    void findAll_ReturnsListOfCertifications_WhenCertificationsExist() {
        List<Certification> certifications = List.of(new Certification(), new Certification());
        when(certificationRepository.findAll()).thenReturn(certifications);

        List<Certification> result = certificationDAO.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(certificationRepository, times(1)).findAll();
    }

    @Test
    void findAll_ReturnsEmptyList_WhenNoCertificationsExist() {
        when(certificationRepository.findAll()).thenReturn(List.of());

        List<Certification> result = certificationDAO.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(certificationRepository, times(1)).findAll();
    }

    @Test
    void findById_ReturnsCertification_WhenCertificationExists() {
        Certification certification = new Certification();
        certification.setId(1L);

        when(certificationRepository.findById(1L)).thenReturn(Optional.of(certification));

        Certification result = certificationDAO.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(certificationRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ReturnsNull_WhenCertificationDoesNotExist() {
        when(certificationRepository.findById(1L)).thenReturn(Optional.empty());

        Certification result = certificationDAO.findById(1L);

        assertNull(result);
        verify(certificationRepository, times(1)).findById(1L);
    }

    @Test
    void save_SavesCertificationSuccessfully_WhenValidCertificationProvided() {
        Certification certification = new Certification();
        certification.setId(1L);
        certification.setName("Test Certification");

        certificationDAO.save(certification);

        verify(certificationRepository, times(1)).save(certification);
    }
    

    @Test
    void deleteById_DeletesCertificationSuccessfully_WhenCertificationExists() {
        doNothing().when(certificationRepository).deleteById(1L);

        certificationDAO.deleteById(1L);

        verify(certificationRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_ThrowsException_WhenCertificationDoesNotExist() {
        doThrow(new RuntimeException("Certification not found")).when(certificationRepository).deleteById(1L);

        assertThrows(RuntimeException.class, () -> certificationDAO.deleteById(1L));
        verify(certificationRepository, times(1)).deleteById(1L);
    }

    @Test
    void findCertificationsByUserId_ReturnsListOfCertifications_WhenCertificationsExistForUser() {
        List<Certification> certifications = List.of(new Certification(), new Certification());
        when(certificationRepository.findCertificationsByUserId(1L)).thenReturn(certifications);

        List<Certification> result = certificationDAO.findCertificationsByUserId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(certificationRepository, times(1)).findCertificationsByUserId(1L);
    }

    @Test
    void findCertificationsByUserId_ReturnsEmptyList_WhenNoCertificationsExistForUser() {
        when(certificationRepository.findCertificationsByUserId(1L)).thenReturn(List.of());

        List<Certification> result = certificationDAO.findCertificationsByUserId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(certificationRepository, times(1)).findCertificationsByUserId(1L);
    }

}