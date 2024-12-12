package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.Project;
import com.alumniportal.unmsm.repository.IProjectRepository;
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
class ProjectDAOImplTest {

    @Mock
    private IProjectRepository projectRepository;

    @InjectMocks
    private ProjectDAOImpl projectDAO;

    @Test
    void findAllReturnsListOfProjectsWhenProjectsExist() {
        List<Project> projects = List.of(new Project(), new Project());
        when(projectRepository.findAll()).thenReturn(projects);

        List<Project> result = projectDAO.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void findAllReturnsEmptyListWhenNoProjectsExist() {
        when(projectRepository.findAll()).thenReturn(List.of());

        List<Project> result = projectDAO.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void findByIdReturnsProjectWhenProjectExists() {
        Project project = new Project();
        project.setId(1L);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Project result = projectDAO.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdReturnsNullWhenProjectDoesNotExist() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        Project result = projectDAO.findById(1L);

        assertNull(result);
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void saveSavesProjectSuccessfullyWhenValidProjectProvided() {
        Project project = new Project();
        project.setId(1L);
        project.setName("Test Project");

        projectDAO.save(project);

        verify(projectRepository, times(1)).save(project);
    }


    @Test
    void deleteByIdDeletesProjectSuccessfullyWhenProjectExists() {
        doNothing().when(projectRepository).deleteById(1L);

        projectDAO.deleteById(1L);

        verify(projectRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdThrowsExceptionWhenProjectDoesNotExist() {
        doThrow(new RuntimeException("Project not found")).when(projectRepository).deleteById(1L);

        assertThrows(RuntimeException.class, () -> projectDAO.deleteById(1L));
        verify(projectRepository, times(1)).deleteById(1L);
    }

    @Test
    void findProjectsByUserIdReturnsListOfProjectsWhenProjectsExistForUser() {
        List<Project> projects = List.of(new Project(), new Project());
        when(projectRepository.findProjectsByUserId(1L)).thenReturn(projects);

        List<Project> result = projectDAO.findProjectsByUserId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(projectRepository, times(1)).findProjectsByUserId(1L);
    }

    @Test
    void findProjectsByUserIdReturnsEmptyListWhenNoProjectsExistForUser() {
        when(projectRepository.findProjectsByUserId(1L)).thenReturn(List.of());

        List<Project> result = projectDAO.findProjectsByUserId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(projectRepository, times(1)).findProjectsByUserId(1L);
    }
}