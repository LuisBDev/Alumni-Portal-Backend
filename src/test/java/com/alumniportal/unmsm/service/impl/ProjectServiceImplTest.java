package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.request.ProjectRequestDTO;
import com.alumniportal.unmsm.dto.response.ProjectResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.ProjectMapper;
import com.alumniportal.unmsm.model.Project;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.interfaces.IProjectDAO;
import com.alumniportal.unmsm.persistence.interfaces.IUserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {


    @Mock
    private IProjectDAO projectDAO;

    @Mock
    private IUserDAO userDAO;

    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @BeforeEach
    void setUp() {
        projectMapper = new ProjectMapper(new ModelMapper());
        projectService = new ProjectServiceImpl(projectDAO, userDAO, projectMapper);
    }

    @Test
    void findAllReturnsListOfProjectResponseDTOs() {
        Project project = new Project();
        when(projectDAO.findAll()).thenReturn(List.of(project));
        List<ProjectResponseDTO> result = projectService.findAll();
        assertEquals(1, result.size());
        verify(projectDAO, times(1)).findAll();
    }

    @Test
    void findAllThrowsExceptionWhenNoProjectsFound() {
        when(projectDAO.findAll()).thenReturn(Collections.emptyList());
        AppException exception = assertThrows(AppException.class, () -> projectService.findAll());
        assertEquals("No projects found", exception.getMessage());
        verify(projectDAO, times(1)).findAll();
    }

    @Test
    void findByIdReturnsProjectResponseDTO() {
        Project project = new Project();
        when(projectDAO.findById(1L)).thenReturn(project);
        ProjectResponseDTO result = projectService.findById(1L);
        assertNotNull(result);
        verify(projectDAO, times(1)).findById(1L);
    }

    @Test
    void findByIdThrowsExceptionWhenProjectNotFound() {
        when(projectDAO.findById(1L)).thenReturn(null);
        AppException exception = assertThrows(AppException.class, () -> projectService.findById(1L));
        assertEquals("Project not found", exception.getMessage());
        verify(projectDAO, times(1)).findById(1L);
    }

    @Test
    void saveSavesProjectSuccessfully() {
        Project project = new Project();
        projectService.save(project);
        verify(projectDAO, times(1)).save(project);
    }

    @Test
    void deleteByIdDeletesProjectWhenFound() {
        Project project = new Project();
        when(projectDAO.findById(1L)).thenReturn(project);
        projectService.deleteById(1L);
        verify(projectDAO, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdThrowsExceptionWhenProjectNotFound() {
        when(projectDAO.findById(1L)).thenReturn(null);
        AppException exception = assertThrows(AppException.class, () -> projectService.deleteById(1L));
        assertEquals("Project not found", exception.getMessage());
        verify(projectDAO, never()).deleteById(1L);
    }

    @Test
    void findProjectsByUserIdReturnsListOfProjectResponseDTOs() {
        Project project = new Project();
        when(projectDAO.findProjectsByUserId(1L)).thenReturn(List.of(project));
        List<ProjectResponseDTO> result = projectService.findProjectsByUserId(1L);
        assertEquals(1, result.size());
        verify(projectDAO, times(1)).findProjectsByUserId(1L);
    }

    @Test
    void saveProjectSavesProjectWhenUserExists() {
        ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO();
        User user = new User();
        when(userDAO.findById(1L)).thenReturn(user);
        projectService.saveProject(projectRequestDTO, 1L);
        verify(projectDAO, times(1)).save(any(Project.class));
    }

    @Test
    void saveProjectThrowsExceptionWhenUserNotFound() {
        ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO();
        when(userDAO.findById(1L)).thenReturn(null);
        AppException exception = assertThrows(AppException.class, () -> projectService.saveProject(projectRequestDTO, 1L));
        assertEquals("User not found", exception.getMessage());
        verify(projectDAO, never()).save(any(Project.class));
    }

    @Test
    void updateProjectUpdatesFieldsWhenProjectExists() {
        Project project = new Project();
        when(projectDAO.findById(1L)).thenReturn(project);
        Map<String, Object> fields = Map.of("description", "Updated Description");
        projectService.updateProject(1L, fields);
        assertEquals("Updated Description", project.getDescription());
        verify(projectDAO, times(1)).save(project);
    }

    @Test
    void updateProjectThrowsExceptionWhenProjectNotFound() {
        when(projectDAO.findById(1L)).thenReturn(null);
        Map<String, Object> fields = Map.of("description", "Updated Description");
        AppException exception = assertThrows(AppException.class, () -> projectService.updateProject(1L, fields));
        assertEquals("Project not found", exception.getMessage());
        verify(projectDAO, never()).save(any(Project.class));
    }

    @Test
    void updateProjectUpdatesFieldsWhenFieldIsDate() {
        Project project = new Project();
        when(projectDAO.findById(1L)).thenReturn(project);
        Map<String, Object> fields = Map.of("date", "2021-01-01");
        projectService.updateProject(1L, fields);
        assertEquals("2021-01-01", project.getDate().toString());
        verify(projectDAO, times(1)).save(project);
    }

}