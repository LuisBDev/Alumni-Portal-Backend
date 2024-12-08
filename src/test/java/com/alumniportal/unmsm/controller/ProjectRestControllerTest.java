package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.config.security.JwtService;
import com.alumniportal.unmsm.config.security.SecurityTestConfig;
import com.alumniportal.unmsm.dto.request.ProjectRequestDTO;
import com.alumniportal.unmsm.dto.response.ProjectResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.service.interfaces.IProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(ProjectRestController.class)
@Import(SecurityTestConfig.class)
class ProjectRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IProjectService projectService;

    @Test
    void findAllReturnsListOfProjects() throws Exception {
        ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO();
        when(projectService.findAll()).thenReturn(List.of(projectResponseDTO));

        mockMvc.perform(get("/api/project/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(projectService, times(1)).findAll();
    }

    @Test
    void findAllReturnsNotFoundWhenNoProjects() throws Exception {
        doThrow(new AppException("No projects found", "NOT_FOUND")).when(projectService).findAll();

        mockMvc.perform(get("/api/project/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(projectService, times(1)).findAll();
    }

    @Test
    void findByIdReturnsProject() throws Exception {
        ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO();
        when(projectService.findById(1L)).thenReturn(projectResponseDTO);

        mockMvc.perform(get("/api/project/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(projectResponseDTO)));

        verify(projectService, times(1)).findById(1L);
    }

    @Test
    void findByIdReturnsNotFoundWhenProjectDoesNotExist() throws Exception {
        doThrow(new AppException("Project not found", "NOT_FOUND")).when(projectService).findById(1L);

        mockMvc.perform(get("/api/project/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(projectService, times(1)).findById(1L);
    }

    @Test
    void saveReturnsCreatedProject() throws Exception {
        ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO();
        ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO();
        when(projectService.saveProject(any(ProjectRequestDTO.class), eq(1L))).thenReturn(projectResponseDTO);

        mockMvc.perform(post("/api/project/save/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(projectResponseDTO)));

        verify(projectService, times(1)).saveProject(any(ProjectRequestDTO.class), eq(1L));
    }

    @Test
    void updateReturnsUpdatedProject() throws Exception {
        ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO();
        Map<String, Object> updates = Map.of("field", "value");
        when(projectService.updateProject(eq(1L), anyMap())).thenReturn(projectResponseDTO);

        mockMvc.perform(patch("/api/project/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updates)))
                .andExpect(status().isOk());

        verify(projectService, times(1)).updateProject(eq(1L), anyMap());
    }

    @Test
    void deleteByIdReturnsNoContent() throws Exception {
        doNothing().when(projectService).deleteById(1L);

        mockMvc.perform(delete("/api/project/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(projectService, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdReturnsNotFoundWhenProjectDoesNotExist() throws Exception {
        doThrow(new AppException("Project not found", "NOT_FOUND")).when(projectService).deleteById(1L);

        mockMvc.perform(delete("/api/project/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(projectService, times(1)).deleteById(1L);
    }

    @Test
    void findProjectsByUserIdReturnsListOfProjects() throws Exception {
        ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO();
        when(projectService.findProjectsByUserId(1L)).thenReturn(List.of(projectResponseDTO));

        mockMvc.perform(get("/api/project/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(projectService, times(1)).findProjectsByUserId(1L);
    }

    @Test
    void findProjectsByUserIdReturnsNotFoundWhenNoProjects() throws Exception {
        doThrow(new AppException("No projects found", "NOT_FOUND")).when(projectService).findProjectsByUserId(1L);

        mockMvc.perform(get("/api/project/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        
        verify(projectService, times(1)).findProjectsByUserId(1L);
    }

}