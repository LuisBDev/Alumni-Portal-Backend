package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.WorkExperience;
import com.alumniportal.unmsm.repository.IWorkExperienceRepository;
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
class WorkExperienceDAOImplTest {

    @Mock
    private IWorkExperienceRepository workExperienceRepository;

    @InjectMocks
    private WorkExperienceDAOImpl workExperienceDAO;

    @Test
    void findAll_ReturnsListOfWorkExperiences_WhenWorkExperiencesExist() {
        List<WorkExperience> workExperiences = List.of(new WorkExperience(), new WorkExperience());
        when(workExperienceRepository.findAll()).thenReturn(workExperiences);

        List<WorkExperience> result = workExperienceDAO.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(workExperienceRepository, times(1)).findAll();
    }

    @Test
    void findAll_ReturnsEmptyList_WhenNoWorkExperiencesExist() {
        when(workExperienceRepository.findAll()).thenReturn(List.of());

        List<WorkExperience> result = workExperienceDAO.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(workExperienceRepository, times(1)).findAll();
    }

    @Test
    void findById_ReturnsWorkExperience_WhenWorkExperienceExists() {
        WorkExperience workExperience = new WorkExperience();
        workExperience.setId(1L);

        when(workExperienceRepository.findById(1L)).thenReturn(Optional.of(workExperience));

        WorkExperience result = workExperienceDAO.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(workExperienceRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ReturnsNull_WhenWorkExperienceDoesNotExist() {
        when(workExperienceRepository.findById(1L)).thenReturn(Optional.empty());

        WorkExperience result = workExperienceDAO.findById(1L);

        assertNull(result);
        verify(workExperienceRepository, times(1)).findById(1L);
    }

    @Test
    void save_SavesWorkExperienceSuccessfully_WhenValidWorkExperienceProvided() {
        WorkExperience workExperience = new WorkExperience();
        workExperience.setId(1L);

        workExperienceDAO.save(workExperience);

        verify(workExperienceRepository, times(1)).save(workExperience);
    }


    @Test
    void deleteById_DeletesWorkExperienceSuccessfully_WhenWorkExperienceExists() {
        doNothing().when(workExperienceRepository).deleteById(1L);

        workExperienceDAO.deleteById(1L);

        verify(workExperienceRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_ThrowsException_WhenWorkExperienceDoesNotExist() {
        doThrow(new RuntimeException("Work Experience not found")).when(workExperienceRepository).deleteById(1L);

        assertThrows(RuntimeException.class, () -> workExperienceDAO.deleteById(1L));
        verify(workExperienceRepository, times(1)).deleteById(1L);
    }

    @Test
    void findWorkExperiencesByUserId_ReturnsListOfWorkExperiences_WhenWorkExperiencesExistForUser() {
        List<WorkExperience> workExperiences = List.of(new WorkExperience(), new WorkExperience());
        when(workExperienceRepository.findWorkExperiencesByUserId(1L)).thenReturn(workExperiences);

        List<WorkExperience> result = workExperienceDAO.findWorkExperiencesByUserId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(workExperienceRepository, times(1)).findWorkExperiencesByUserId(1L);
    }

    @Test
    void findWorkExperiencesByUserId_ReturnsEmptyList_WhenNoWorkExperiencesExistForUser() {
        when(workExperienceRepository.findWorkExperiencesByUserId(1L)).thenReturn(List.of());

        List<WorkExperience> result = workExperienceDAO.findWorkExperiencesByUserId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(workExperienceRepository, times(1)).findWorkExperiencesByUserId(1L);
    }

}