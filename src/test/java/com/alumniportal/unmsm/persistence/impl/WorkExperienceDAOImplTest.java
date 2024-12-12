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
    void findAllReturnsListOfWorkExperiencesWhenWorkExperiencesExist() {
        List<WorkExperience> workExperiences = List.of(new WorkExperience(), new WorkExperience());
        when(workExperienceRepository.findAll()).thenReturn(workExperiences);

        List<WorkExperience> result = workExperienceDAO.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(workExperienceRepository, times(1)).findAll();
    }

    @Test
    void findAllReturnsEmptyListWhenNoWorkExperiencesExist() {
        when(workExperienceRepository.findAll()).thenReturn(List.of());

        List<WorkExperience> result = workExperienceDAO.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(workExperienceRepository, times(1)).findAll();
    }

    @Test
    void findByIdReturnsWorkExperienceWhenWorkExperienceExists() {
        WorkExperience workExperience = new WorkExperience();
        workExperience.setId(1L);

        when(workExperienceRepository.findById(1L)).thenReturn(Optional.of(workExperience));

        WorkExperience result = workExperienceDAO.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(workExperienceRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdReturnsNullWhenWorkExperienceDoesNotExist() {
        when(workExperienceRepository.findById(1L)).thenReturn(Optional.empty());

        WorkExperience result = workExperienceDAO.findById(1L);

        assertNull(result);
        verify(workExperienceRepository, times(1)).findById(1L);
    }

    @Test
    void saveSavesWorkExperienceSuccessfullyWhenValidWorkExperienceProvided() {
        WorkExperience workExperience = new WorkExperience();
        workExperience.setId(1L);

        workExperienceDAO.save(workExperience);

        verify(workExperienceRepository, times(1)).save(workExperience);
    }


    @Test
    void deleteByIdDeletesWorkExperienceSuccessfullyWhenWorkExperienceExists() {
        doNothing().when(workExperienceRepository).deleteById(1L);

        workExperienceDAO.deleteById(1L);

        verify(workExperienceRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdThrowsExceptionWhenWorkExperienceDoesNotExist() {
        doThrow(new RuntimeException("Work Experience not found")).when(workExperienceRepository).deleteById(1L);

        assertThrows(RuntimeException.class, () -> workExperienceDAO.deleteById(1L));
        verify(workExperienceRepository, times(1)).deleteById(1L);
    }

    @Test
    void findWorkExperiencesByUserIdReturnsListOfWorkExperiencesWhenWorkExperiencesExistForUser() {
        List<WorkExperience> workExperiences = List.of(new WorkExperience(), new WorkExperience());
        when(workExperienceRepository.findWorkExperiencesByUserId(1L)).thenReturn(workExperiences);

        List<WorkExperience> result = workExperienceDAO.findWorkExperiencesByUserId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(workExperienceRepository, times(1)).findWorkExperiencesByUserId(1L);
    }

    @Test
    void findWorkExperiencesByUserIdReturnsEmptyListWhenNoWorkExperiencesExistForUser() {
        when(workExperienceRepository.findWorkExperiencesByUserId(1L)).thenReturn(List.of());

        List<WorkExperience> result = workExperienceDAO.findWorkExperiencesByUserId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(workExperienceRepository, times(1)).findWorkExperiencesByUserId(1L);
    }

}