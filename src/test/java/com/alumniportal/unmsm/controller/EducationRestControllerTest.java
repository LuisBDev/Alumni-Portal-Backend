package com.alumniportal.unmsm.controller;


import com.alumniportal.unmsm.config.security.JwtService;
import com.alumniportal.unmsm.config.security.SecurityTestConfig;
import com.alumniportal.unmsm.dto.request.EducationRequestDTO;
import com.alumniportal.unmsm.dto.response.EducationResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.service.interfaces.IEducationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;


@WebMvcTest(EducationRestController.class)
@Import(SecurityTestConfig.class)
class EducationRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IEducationService educationService;

    @Test
    void findAllReturnsListOfEducations() throws Exception {
        EducationResponseDTO educationResponseDTO = new EducationResponseDTO();
        when(educationService.findAll()).thenReturn(List.of(educationResponseDTO));

        mockMvc.perform(get("/api/education/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(educationService, times(1)).findAll();
    }

    @Test
    void findAllReturnsNotFoundWhenNoEducations() throws Exception {
        doThrow(new AppException("No educations found", "NOT_FOUND")).when(educationService).findAll();

        mockMvc.perform(get("/api/education/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(educationService, times(1)).findAll();
    }

    @Test
    void findByIdReturnsEducation() throws Exception {
        EducationResponseDTO educationResponseDTO = new EducationResponseDTO();
        when(educationService.findById(1L)).thenReturn(educationResponseDTO);

        mockMvc.perform(get("/api/education/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(educationResponseDTO)));

        verify(educationService, times(1)).findById(1L);
    }

    @Test
    void findByIdReturnsNotFoundWhenEducationDoesNotExist() throws Exception {
        doThrow(new AppException("Education not found", "NOT_FOUND")).when(educationService).findById(1L);

        mockMvc.perform(get("/api/education/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(educationService, times(1)).findById(1L);
    }

    @Test
    void saveReturnsCreatedEducation() throws Exception {
        EducationRequestDTO educationRequestDTO = new EducationRequestDTO();
        EducationResponseDTO educationResponseDTO = new EducationResponseDTO();
        when(educationService.saveEducation(any(EducationRequestDTO.class), eq(1L))).thenReturn(educationResponseDTO);

        mockMvc.perform(post("/api/education/save/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(educationRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(educationResponseDTO)));

        verify(educationService, times(1)).saveEducation(any(EducationRequestDTO.class), eq(1L));
    }

    @Test
    void deleteByIdReturnsNoContent() throws Exception {
        doNothing().when(educationService).deleteById(1L);

        mockMvc.perform(delete("/api/education/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(educationService, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdReturnsNotFoundWhenEducationDoesNotExist() throws Exception {
        doThrow(new AppException("Education not found", "NOT_FOUND")).when(educationService).deleteById(1L);

        mockMvc.perform(delete("/api/education/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(educationService, times(1)).deleteById(1L);
    }

    @Test
    void updateReturnsUpdatedEducation() throws Exception {
        EducationResponseDTO educationResponseDTO = new EducationResponseDTO();
        Map<String, Object> updates = Map.of("field", "value");
        when(educationService.updateEducation(eq(1L), anyMap())).thenReturn(educationResponseDTO);

        mockMvc.perform(patch("/api/education/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updates)))
                .andExpect(status().isOk());

        verify(educationService, times(1)).updateEducation(eq(1L), anyMap());
    }

    @Test
    void findEducationsByUserIdReturnsListOfEducations() throws Exception {
        EducationResponseDTO educationResponseDTO = new EducationResponseDTO();
        when(educationService.findEducationsByUserId(1L)).thenReturn(List.of(educationResponseDTO));

        mockMvc.perform(get("/api/education/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(educationService, times(1)).findEducationsByUserId(1L);
    }

    @Test
    void findEducationsByUserIdReturnsNotFoundWhenNoEducations() throws Exception {
        doThrow(new AppException("No educations found", "NOT_FOUND")).when(educationService).findEducationsByUserId(1L);

        mockMvc.perform(get("/api/education/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(educationService, times(1)).findEducationsByUserId(1L);
    }


}