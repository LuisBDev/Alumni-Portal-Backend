package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.EducationDTO;
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
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EducationServiceImplTest {

    @Mock
    private IEducationDAO educationDAO;

    @Mock
    private IUserDAO userDAO;


    private ModelMapper modelMapper;

    @InjectMocks
    private EducationServiceImpl educationService;


    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        educationService = new EducationServiceImpl(educationDAO, userDAO, modelMapper);
    }


    @Test
    void findAllReturnsListOfEducationDTOs() {
        Education education = new Education();
        when(educationDAO.findAll()).thenReturn(List.of(education));
        List<EducationDTO> result = educationService.findAll();
        assertEquals(1, result.size());
        verify(educationDAO, times(1)).findAll();
    }

    @Test
    void findAllReturnsEmptyListWhenNoEducations() {
        when(educationDAO.findAll()).thenReturn(Collections.emptyList());
        List<EducationDTO> result = educationService.findAll();
        assertTrue(result.isEmpty());
        verify(educationDAO, times(1)).findAll();
    }

    @Test
    void findByIdReturnsEducationDTOWhenFound() {
        Education education = new Education();
        when(educationDAO.findById(1L)).thenReturn(education);
        EducationDTO result = educationService.findById(1L);
        assertNotNull(result);
        verify(educationDAO, times(1)).findById(1L);
    }

    @Test
    void findByIdReturnsNullWhenNotFound() {
        when(educationDAO.findById(1L)).thenReturn(null);
        EducationDTO result = educationService.findById(1L);
        assertNull(result);
        verify(educationDAO, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        Education education = new Education();
        educationService.save(education);
        verify(educationDAO, times(1)).save(education);
    }

    @Test
    void deleteByIdCallsEducationDAODeleteById() {
        educationService.deleteById(1L);
        verify(educationDAO, times(1)).deleteById(1L);
    }

    @Test
    void findEducationsByUserIdReturnsListOfEducationDTOs() {
        Education education = new Education();
        when(educationDAO.findEducationsByUserId(1L)).thenReturn(List.of(education));
        List<EducationDTO> result = educationService.findEducationsByUserId(1L);
        assertEquals(1, result.size());
        verify(educationDAO, times(1)).findEducationsByUserId(1L);
    }

    @Test
    void findEducationsByUserIdReturnsEmptyListWhenNoEducations() {
        when(educationDAO.findEducationsByUserId(1L)).thenReturn(Collections.emptyList());
        List<EducationDTO> result = educationService.findEducationsByUserId(1L);
        assertTrue(result.isEmpty());
        verify(educationDAO, times(1)).findEducationsByUserId(1L);
    }

    @Test
    void saveEducationThrowsExceptionWhenUserNotFound() {
        when(userDAO.findById(1L)).thenReturn(null);
        Education education = new Education();
        assertThrows(RuntimeException.class, () -> educationService.saveEducation(education, 1L));
        verify(userDAO, times(1)).findById(1L);
    }

    @Test
    void saveEducation() {
        User user = new User();
        when(userDAO.findById(1L)).thenReturn(user);
        Education education = new Education();
        educationService.saveEducation(education, 1L);
        verify(educationDAO, times(1)).save(education);
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



}