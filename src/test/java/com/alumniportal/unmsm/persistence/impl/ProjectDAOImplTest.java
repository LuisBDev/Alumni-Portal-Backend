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
    void findAll_ReturnsListOfProjects_WhenProjectsExist() {
        List<Project> projects = List.of(new Project(), new Project());
        when(projectRepository.findAll()).thenReturn(projects);

        List<Project> result = projectDAO.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void findAll_ReturnsEmptyList_WhenNoProjectsExist() {
        when(projectRepository.findAll()).thenReturn(List.of());

        List<Project> result = projectDAO.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void findById_ReturnsProject_WhenProjectExists() {
        Project project = new Project();
        project.setId(1L);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Project result = projectDAO.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ReturnsNull_WhenProjectDoesNotExist() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        Project result = projectDAO.findById(1L);

        assertNull(result);
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void save_SavesProjectSuccessfully_WhenValidProjectProvided() {
        Project project = new Project();
        project.setId(1L);
        project.setName("Test Project");

        projectDAO.save(project);

        verify(projectRepository, times(1)).save(project);
    }


    @Test
    void deleteById_DeletesProjectSuccessfully_WhenProjectExists() {
        doNothing().when(projectRepository).deleteById(1L);

        projectDAO.deleteById(1L);

        verify(projectRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_ThrowsException_WhenProjectDoesNotExist() {
        doThrow(new RuntimeException("Project not found")).when(projectRepository).deleteById(1L);

        assertThrows(RuntimeException.class, () -> projectDAO.deleteById(1L));
        verify(projectRepository, times(1)).deleteById(1L);
    }

    @Test
    void findProjectsByUserId_ReturnsListOfProjects_WhenProjectsExistForUser() {
        List<Project> projects = List.of(new Project(), new Project());
        when(projectRepository.findProjectsByUserId(1L)).thenReturn(projects);

        List<Project> result = projectDAO.findProjectsByUserId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(projectRepository, times(1)).findProjectsByUserId(1L);
    }

    @Test
    void findProjectsByUserId_ReturnsEmptyList_WhenNoProjectsExistForUser() {
        when(projectRepository.findProjectsByUserId(1L)).thenReturn(List.of());

        List<Project> result = projectDAO.findProjectsByUserId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(projectRepository, times(1)).findProjectsByUserId(1L);
    }
}