package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.config.security.JwtService;
import com.alumniportal.unmsm.config.security.SecurityTestConfig;
import com.alumniportal.unmsm.dto.request.CertificationRequestDTO;
import com.alumniportal.unmsm.dto.response.CertificationResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.service.interfaces.ICertificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(CertificationRestController.class)
@Import(SecurityTestConfig.class)
class CertificationRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;


    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ICertificationService certificationService;

    @Test
    void findAllReturnsListOfCertifications() throws Exception {
        CertificationResponseDTO certificationResponseDTO = new CertificationResponseDTO();
        when(certificationService.findAll()).thenReturn(List.of(certificationResponseDTO));

        mockMvc.perform(get("/api/certification/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(certificationService, times(1)).findAll();
    }


    @Test
    void findByIdReturnsCertification() throws Exception {
        CertificationResponseDTO certificationResponseDTO = new CertificationResponseDTO();
        when(certificationService.findById(1L)).thenReturn(certificationResponseDTO);

        mockMvc.perform(get("/api/certification/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(certificationResponseDTO)));

        verify(certificationService, times(1)).findById(1L);
    }

    @Test
    void findByIdReturnsNotFoundWhenCertificationDoesNotExist() throws Exception {
        doThrow(new AppException("Certification not found", "NOT_FOUND")).when(certificationService).findById(1L);

        mockMvc.perform(get("/api/certification/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(certificationService, times(1)).findById(1L);
    }

    @Test
    void saveReturnsCreatedCertification() throws Exception {
        CertificationRequestDTO certificationRequestDTO = new CertificationRequestDTO();
        CertificationResponseDTO certificationResponseDTO = new CertificationResponseDTO();
        when(certificationService.saveCertification(any(CertificationRequestDTO.class), eq(1L))).thenReturn(certificationResponseDTO);

        mockMvc.perform(post("/api/certification/save/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(certificationRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(certificationResponseDTO)));

        verify(certificationService, times(1)).saveCertification(any(CertificationRequestDTO.class), eq(1L));
    }

    @Test
    void deleteByIdReturnsNoContent() throws Exception {
        doNothing().when(certificationService).deleteById(1L);

        mockMvc.perform(delete("/api/certification/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(certificationService, times(1)).deleteById(1L);
    }


    @Test
    void updateCertificationReturnsUpdatedCertification() throws Exception {
        CertificationResponseDTO certificationResponseDTO = new CertificationResponseDTO();
        Map<String, Object> updates = Map.of("name", "newName");
        when(certificationService.updateCertification(eq(1L), anyMap())).thenReturn(certificationResponseDTO);

        mockMvc.perform(patch("/api/certification/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updates)))
                .andExpect(status().isOk());

        verify(certificationService, times(1)).updateCertification(eq(1L), anyMap());
    }

    @Test
    void findCertificationsByUserIdReturnsListOfCertifications() throws Exception {
        CertificationResponseDTO certificationResponseDTO = new CertificationResponseDTO();
        when(certificationService.findCertificationsByUserId(1L)).thenReturn(List.of(certificationResponseDTO));

        mockMvc.perform(get("/api/certification/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(certificationService, times(1)).findCertificationsByUserId(1L);
    }


}