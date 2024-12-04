package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.Data.CertificationProvider;
import com.alumniportal.unmsm.Data.UserProvider;
import com.alumniportal.unmsm.dto.RequestDTO.CertificationRequestDTO;
import com.alumniportal.unmsm.dto.ResponseDTO.CertificationResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.CertificationMapper;
import com.alumniportal.unmsm.model.Certification;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.interfaces.ICertificationDAO;
import com.alumniportal.unmsm.persistence.interfaces.IUserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CertificationServiceImplTest {

    @Mock
    private ICertificationDAO certificationDAO;

    @Mock
    private IUserDAO userDAO;

    //    Real model mapper
    private CertificationMapper certificationMapper;

    @InjectMocks
    private CertificationServiceImpl certificationService;


    @BeforeEach
    void setUp() {

        certificationMapper = new CertificationMapper(new ModelMapper());
        certificationService = new CertificationServiceImpl(certificationDAO, userDAO, certificationMapper);
    }

    @Test
    void testFindAll_ReturnsListOfCertification_WhenCertificationsExist() {

        when(certificationDAO.findAll()).thenReturn(CertificationProvider.certificationList());

        List<CertificationResponseDTO> result = certificationService.findAll();

        assertEquals(3, result.size());
        verify(certificationDAO, times(1)).findAll();
    }

    @Test
    void testFindAll_ReturnsEmptyList_WhenNoCertificationsExist() {
        when(certificationDAO.findAll()).thenReturn(List.of());

        assertThrows(AppException.class, () -> certificationService.findAll());

        verify(certificationDAO, times(1)).findAll();
    }


    @Test
    void testFindById_ReturnsCertificationDTO_WhenCertificationExists() {
        Certification certification = CertificationProvider.certificationOne();

        when(certificationDAO.findById(anyLong())).thenReturn(certification);

        CertificationResponseDTO result = certificationService.findById(1L);

        assertNotNull(result);
        assertEquals("Python", result.getName());
        verify(certificationDAO, times(1)).findById(anyLong());
    }

    @Test
    void testFindById_ReturnsNull_WhenCertificationDoesNotExist() {
        when(certificationDAO.findById(anyLong())).thenReturn(null);

        assertThrows(AppException.class, () -> certificationService.findById(1L));

        verify(certificationDAO, times(1)).findById(anyLong());
    }

    @Test
    void testSavesCertificationSuccessfully() {
        Certification certification = CertificationProvider.certificationOne();

        doNothing().when(certificationDAO).save(certification);

        certificationService.save(certification);

        verify(certificationDAO, times(1)).save(certification);
    }

    @Test
    void testDeleteById() {
        Long certificationId = 1L;

        doNothing().when(certificationDAO).deleteById(anyLong());
        when(certificationDAO.findById(certificationId)).thenReturn(CertificationProvider.certificationOne());

        certificationService.deleteById(certificationId);

        verify(certificationDAO).deleteById(certificationId);

    }

    @Test
    void testDeleteById_WhenCertificationDoesNotExist() {
        Long certificationId = 1L;

        when(certificationDAO.findById(certificationId)).thenReturn(null);

        assertThrows(AppException.class, () -> certificationService.deleteById(certificationId));

        verify(certificationDAO, never()).deleteById(certificationId);
    }


    @Test
    void findCertificationsByUserId_ReturnsListOfCertificationDTOs_WhenCertificationsExist() {

        when(certificationDAO.findCertificationsByUserId(anyLong())).thenReturn(CertificationProvider.certificationList());

        List<CertificationResponseDTO> result = certificationService.findCertificationsByUserId(1L);

        assertEquals(3, result.size());
        verify(certificationDAO, times(1)).findCertificationsByUserId(1L);
    }

    @Test
    void findCertificationsByUserId_ReturnsEmptyList_WhenNoCertificationsExist() {
        when(certificationDAO.findCertificationsByUserId(anyLong())).thenReturn(List.of());

        assertThrows(AppException.class, () -> certificationService.findCertificationsByUserId(1L));

        verify(certificationDAO, times(1)).findCertificationsByUserId(1L);
    }

    @Test
    void testSaveCertification_WhenUserExists() {
        User user = UserProvider.userOne();
        CertificationRequestDTO certification = new CertificationRequestDTO();

        when(userDAO.findById(anyLong())).thenReturn(user);

        doNothing().when(certificationDAO).save(any(Certification.class));

        certificationService.saveCertification(certification, user.getId());

        verify(userDAO, times(1)).findById(anyLong());
        verify(certificationDAO, times(1)).save(any(Certification.class));
    }

    @Test
    void testSaveCertification_WhenUserDoesNotExist() {
        CertificationRequestDTO certification = new CertificationRequestDTO();

        when(userDAO.findById(anyLong())).thenReturn(null);

        assertThrows(RuntimeException.class, () -> certificationService.saveCertification(certification, 1L));

        verify(userDAO, times(1)).findById(anyLong());
        verify(certificationDAO, never()).save(any(Certification.class));
    }

    @Test
    void updateCertification_UpdatesFieldsSuccessfully_WhenCertificationExists() {
        Certification certification = CertificationProvider.certificationOne();
        Map<String, Object> fields = Map.of("name", "Updated Certification", "issueDate", "2020-10-01");

        when(certificationDAO.findById(anyLong())).thenReturn(certification);

        certificationService.updateCertification(1L, fields);

        assertEquals("Updated Certification", certification.getName());
        assertEquals(LocalDate.of(2020, 10, 1), certification.getIssueDate());
        verify(certificationDAO, times(1)).save(certification);
    }

    @Test
    void updateCertification_ThrowsException_WhenCertificationDoesNotExist() {
        Map<String, Object> fields = Map.of("name", "Updated Certification");

        when(certificationDAO.findById(anyLong())).thenReturn(null);

        assertThrows(RuntimeException.class, () -> certificationService.updateCertification(1L, fields));
        verify(certificationDAO, never()).save(any(Certification.class));
    }

    @Test
    void updateCertification_SetsFieldToNull_WhenEmptyStringProvided() {
        Certification certification = CertificationProvider.certificationOne();
        Map<String, Object> fields = Map.of("name", "");

        when(certificationDAO.findById(anyLong())).thenReturn(certification);

        certificationService.updateCertification(1L, fields);

        assertNull(certification.getName());
        verify(certificationDAO, times(1)).save(certification);
    }

    @Test
    void updateCertification_ParsesLocalDateFieldsCorrectly() {
        Certification certification = CertificationProvider.certificationOne();
        Map<String, Object> fields = Map.of("issueDate", "2023-10-01", "expirationDate", "2024-10-01");

        when(certificationDAO.findById(anyLong())).thenReturn(certification);

        certificationService.updateCertification(1L, fields);

        assertEquals(LocalDate.of(2023, 10, 1), certification.getIssueDate());
        assertEquals(LocalDate.of(2024, 10, 1), certification.getExpirationDate());
        verify(certificationDAO, times(1)).save(certification);
    }


}