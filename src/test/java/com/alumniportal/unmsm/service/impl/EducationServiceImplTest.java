package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.request.EducationRequestDTO;
import com.alumniportal.unmsm.dto.response.EducationResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.EducationMapper;
import com.alumniportal.unmsm.model.Education;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.interfaces.IEducationDAO;
import com.alumniportal.unmsm.persistence.interfaces.IUserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EducationServiceImplTest {

    @Mock
    private IEducationDAO educationDAO;

    @Mock
    private IUserDAO userDAO;


    private EducationMapper educationMapper;

    @InjectMocks
    private EducationServiceImpl educationService;


    @BeforeEach
    void setUp() {
        educationMapper = new EducationMapper(new ModelMapper());
        educationService = new EducationServiceImpl(educationDAO, userDAO, educationMapper);
    }


    @Test
    void findAllReturnsListOfEducationDTOs() {
        Education education = new Education();
        when(educationDAO.findAll()).thenReturn(List.of(education));
        List<EducationResponseDTO> result = educationService.findAll();
        assertEquals(1, result.size());
        verify(educationDAO, times(1)).findAll();
    }

    @Test
    void findAllReturnsThrowsExceptionWhenNoEducationsFound() {
        when(educationDAO.findAll()).thenReturn(Collections.emptyList());
        assertThrows(AppException.class, () -> educationService.findAll());
        verify(educationDAO, times(1)).findAll();
    }


    @Test
    void findByIdReturnsEducationDTOWhenFound() {
        Education education = new Education();
        when(educationDAO.findById(1L)).thenReturn(education);
        EducationResponseDTO result = educationService.findById(1L);
        assertNotNull(result);
        verify(educationDAO, times(1)).findById(1L);
    }

    @Test
    void findByIdThrowsExceptionWhenEducationNotFound() {
        when(educationDAO.findById(1L)).thenReturn(null);
        assertThrows(AppException.class, () -> educationService.findById(1L));
        verify(educationDAO, times(1)).findById(1L);
    }

    @Test
    void saveSavesEducationSuccessfully() {
        Education education = new Education();
        educationService.save(education);
        verify(educationDAO, times(1)).save(education);
    }

    @Test
    void deleteByIdCallsEducationDAODeleteById() {

        when(educationDAO.findById(1L)).thenReturn(new Education());
        educationService.deleteById(1L);
        verify(educationDAO, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdThrowsExceptionWhenEducationNotFound() {
        when(educationDAO.findById(1L)).thenReturn(null);
        assertThrows(AppException.class, () -> educationService.deleteById(1L));
        verify(educationDAO, never()).deleteById(1L);
    }

    @Test
    void findEducationsByUserIdReturnsListOfEducationDTOs() {
        Education education = new Education();
        when(educationDAO.findEducationsByUserId(1L)).thenReturn(List.of(education));
        List<EducationResponseDTO> result = educationService.findEducationsByUserId(1L);
        assertEquals(1, result.size());
        verify(educationDAO, times(1)).findEducationsByUserId(1L);
    }

    @Test
    void findEducationsByUserIdThrowsExceptionWhenNoEducationsFound() {
        when(educationDAO.findEducationsByUserId(1L)).thenReturn(Collections.emptyList());
        assertThrows(AppException.class, () -> educationService.findEducationsByUserId(1L));
        verify(educationDAO, times(1)).findEducationsByUserId(1L);
    }


    @Test
    void saveEducationThrowsExceptionWhenUserNotFound() {
        when(userDAO.findById(1L)).thenReturn(null);
        EducationRequestDTO education = new EducationRequestDTO();
        assertThrows(AppException.class, () -> educationService.saveEducation(education, 1L));
        verify(userDAO, times(1)).findById(1L);
    }

    @Test
    void saveEducationSavesEducationSuccessfully() {
        User user = new User();
        when(userDAO.findById(1L)).thenReturn(user);
        EducationRequestDTO education = new EducationRequestDTO();
        educationService.saveEducation(education, 1L);
        verify(educationDAO, times(1)).save(any(Education.class));
    }

    @Test
    void updateEducationUpdatesFieldsCorrectly() {
        Education education = new Education();
        when(educationDAO.findById(1L)).thenReturn(education);
        Map<String, Object> fields = Map.of("degree", "Updated Degree", "startDate", "2023-01-01");
        educationService.updateEducation(1L, fields);
        assertEquals("Updated Degree", education.getDegree());
        assertEquals(LocalDate.of(2023, 1, 1), education.getStartDate());
        verify(educationDAO, times(1)).save(education);
    }

    @Test
    void updateEducationThrowsExceptionWhenEducationNotFound() {
        when(educationDAO.findById(1L)).thenReturn(null);
        Map<String, Object> fields = Map.of("degree", "Updated Degree");
        assertThrows(RuntimeException.class, () -> educationService.updateEducation(1L, fields));
        verify(educationDAO, never()).save(any(Education.class));
    }

    @Test
    void updateEducationWhenFieldIsStartDateAndEndDate() {
        Education education = new Education();
        when(educationDAO.findById(1L)).thenReturn(education);
        Map<String, Object> fields = Map.of("startDate", "2023-01-01", "endDate", "2024-01-01");
        educationService.updateEducation(1L, fields);
        assertEquals(LocalDate.of(2023, 1, 1), education.getStartDate());
        assertEquals(LocalDate.of(2024, 1, 1), education.getEndDate());
        verify(educationDAO, times(1)).save(education);
    }


}