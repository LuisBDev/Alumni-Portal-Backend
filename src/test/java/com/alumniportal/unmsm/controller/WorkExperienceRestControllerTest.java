package com.alumniportal.unmsm.controller;


import com.alumniportal.unmsm.config.security.JwtService;
import com.alumniportal.unmsm.config.security.SecurityTestConfig;
import com.alumniportal.unmsm.dto.request.WorkExperienceRequestDTO;
import com.alumniportal.unmsm.dto.response.WorkExperienceResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.service.interfaces.IWorkExperienceService;
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

@WebMvcTest(WorkExperienceRestController.class)
@Import(SecurityTestConfig.class)
class WorkExperienceRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IWorkExperienceService workExperienceService;

    @Test
    void findAllReturnsListOfWorkExperiences() throws Exception {
        WorkExperienceResponseDTO workExperienceResponseDTO = new WorkExperienceResponseDTO();
        when(workExperienceService.findAll()).thenReturn(List.of(workExperienceResponseDTO));

        mockMvc.perform(get("/api/work-experience/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(workExperienceService, times(1)).findAll();
    }

    @Test
    void findAllReturnsEmptyListWhenNoWorkExperiences() throws Exception {
        when(workExperienceService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/work-experience/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(workExperienceService, times(1)).findAll();
    }

    @Test
    void findByIdReturnsWorkExperience() throws Exception {
        WorkExperienceResponseDTO workExperienceResponseDTO = new WorkExperienceResponseDTO();
        when(workExperienceService.findById(1L)).thenReturn(workExperienceResponseDTO);

        mockMvc.perform(get("/api/work-experience/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(workExperienceResponseDTO)));

        verify(workExperienceService, times(1)).findById(1L);
    }

    @Test
    void findByIdReturnsNotFoundWhenWorkExperienceDoesNotExist() throws Exception {
        doThrow(new AppException("Work experience not found", "NOT_FOUND")).when(workExperienceService).findById(1L);

        mockMvc.perform(get("/api/work-experience/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(workExperienceService, times(1)).findById(1L);
    }

    @Test
    void saveReturnsCreatedWorkExperience() throws Exception {
        WorkExperienceRequestDTO workExperienceRequestDTO = new WorkExperienceRequestDTO();
        WorkExperienceResponseDTO workExperienceResponseDTO = new WorkExperienceResponseDTO();
        when(workExperienceService.saveWorkExperience(any(WorkExperienceRequestDTO.class), eq(1L))).thenReturn(workExperienceResponseDTO);

        mockMvc.perform(post("/api/work-experience/save/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workExperienceRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(workExperienceResponseDTO)));

        verify(workExperienceService, times(1)).saveWorkExperience(any(WorkExperienceRequestDTO.class), eq(1L));
    }

    @Test
    void updateReturnsUpdatedWorkExperience() throws Exception {
        WorkExperienceResponseDTO workExperienceResponseDTO = new WorkExperienceResponseDTO();
        Map<String, Object> updates = Map.of("field", "value");
        when(workExperienceService.updateWorkExperience(eq(1L), anyMap())).thenReturn(workExperienceResponseDTO);

        mockMvc.perform(patch("/api/work-experience/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updates)))
                .andExpect(status().isOk());
        verify(workExperienceService, times(1)).updateWorkExperience(eq(1L), anyMap());
    }

    @Test
    void deleteByIdReturnsNoContent() throws Exception {
        doNothing().when(workExperienceService).deleteById(1L);

        mockMvc.perform(delete("/api/work-experience/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(workExperienceService, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdReturnsNotFoundWhenWorkExperienceDoesNotExist() throws Exception {
        doThrow(new AppException("Work experience not found", "NOT_FOUND")).when(workExperienceService).deleteById(1L);

        mockMvc.perform(delete("/api/work-experience/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(workExperienceService, times(1)).deleteById(1L);
    }

    @Test
    void findWorkExperiencesByUserIdReturnsListOfWorkExperiences() throws Exception {
        WorkExperienceResponseDTO workExperienceResponseDTO = new WorkExperienceResponseDTO();
        when(workExperienceService.findWorkExperiencesByUserId(1L)).thenReturn(List.of(workExperienceResponseDTO));

        mockMvc.perform(get("/api/work-experience/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(workExperienceService, times(1)).findWorkExperiencesByUserId(1L);
    }

    @Test
    void findWorkExperiencesByUserIdReturnsEmptyListWhenNoWorkExperiences() throws Exception {
        when(workExperienceService.findWorkExperiencesByUserId(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/work-experience/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(workExperienceService, times(1)).findWorkExperiencesByUserId(1L);
    }

}