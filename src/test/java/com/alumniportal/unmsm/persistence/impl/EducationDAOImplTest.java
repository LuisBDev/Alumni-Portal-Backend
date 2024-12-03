package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.Education;
import com.alumniportal.unmsm.repository.IEducationRepository;
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
class EducationDAOImplTest {

    @Mock
    private IEducationRepository educationRepository;

    @InjectMocks
    private EducationDAOImpl educationDAO;

    @Test
    void findAll_ReturnsListOfEducations_WhenEducationsExist() {
        List<Education> educations = List.of(new Education(), new Education());
        when(educationRepository.findAll()).thenReturn(educations);

        List<Education> result = educationDAO.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(educationRepository, times(1)).findAll();
    }

    @Test
    void findAll_ReturnsEmptyList_WhenNoEducationsExist() {
        when(educationRepository.findAll()).thenReturn(List.of());

        List<Education> result = educationDAO.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(educationRepository, times(1)).findAll();
    }

    @Test
    void findById_ReturnsEducation_WhenEducationExists() {
        Education education = new Education();
        education.setId(1L);

        when(educationRepository.findById(1L)).thenReturn(Optional.of(education));

        Education result = educationDAO.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(educationRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ReturnsNull_WhenEducationDoesNotExist() {
        when(educationRepository.findById(1L)).thenReturn(Optional.empty());

        Education result = educationDAO.findById(1L);

        assertNull(result);
        verify(educationRepository, times(1)).findById(1L);
    }

    @Test
    void save_SavesEducationSuccessfully_WhenValidEducationProvided() {
        Education education = new Education();
        education.setId(1L);
        educationDAO.save(education);

        verify(educationRepository, times(1)).save(education);
    }
    

    @Test
    void deleteById_DeletesEducationSuccessfully_WhenEducationExists() {
        doNothing().when(educationRepository).deleteById(1L);

        educationDAO.deleteById(1L);

        verify(educationRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_ThrowsException_WhenEducationDoesNotExist() {
        doThrow(new RuntimeException("Education not found")).when(educationRepository).deleteById(1L);

        assertThrows(RuntimeException.class, () -> educationDAO.deleteById(1L));
        verify(educationRepository, times(1)).deleteById(1L);
    }

    @Test
    void findEducationsByUserId_ReturnsListOfEducations_WhenEducationsExistForUser() {
        List<Education> educations = List.of(new Education(), new Education());
        when(educationRepository.findEducationsByUserId(1L)).thenReturn(educations);

        List<Education> result = educationDAO.findEducationsByUserId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(educationRepository, times(1)).findEducationsByUserId(1L);
    }

    @Test
    void findEducationsByUserId_ReturnsEmptyList_WhenNoEducationsExistForUser() {
        when(educationRepository.findEducationsByUserId(1L)).thenReturn(List.of());

        List<Education> result = educationDAO.findEducationsByUserId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(educationRepository, times(1)).findEducationsByUserId(1L);
    }


}