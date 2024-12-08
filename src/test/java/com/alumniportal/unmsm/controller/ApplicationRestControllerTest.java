package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.config.security.JwtService;
import com.alumniportal.unmsm.config.security.SecurityTestConfig;
import com.alumniportal.unmsm.dto.request.ApplicationRequestDTO;
import com.alumniportal.unmsm.dto.response.ApplicationResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.service.interfaces.IApplicationService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(ApplicationRestController.class)
@Import(SecurityTestConfig.class)
class ApplicationRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IApplicationService applicationService;

    @Test
    void findAllReturnsListOfApplications() throws Exception {
        ApplicationResponseDTO applicationResponseDTO = new ApplicationResponseDTO();
        when(applicationService.findAll()).thenReturn(List.of(applicationResponseDTO));

        mockMvc.perform(get("/api/application/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(applicationService, times(1)).findAll();
    }

    @Test
    void findAllReturnsEmptyListWhenNoApplications() throws Exception {
        when(applicationService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/application/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(applicationService, times(1)).findAll();
    }

    @Test
    void findByIdReturnsApplication() throws Exception {
        ApplicationResponseDTO applicationResponseDTO = new ApplicationResponseDTO();
        when(applicationService.findById(1L)).thenReturn(applicationResponseDTO);

        mockMvc.perform(get("/api/application/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(applicationResponseDTO)));

        verify(applicationService, times(1)).findById(1L);
    }

    @Test
    void findByIdReturnsNotFoundWhenApplicationDoesNotExist() throws Exception {
        doThrow(new AppException("Application not found", "NOT_FOUND")).when(applicationService).findById(1L);

        mockMvc.perform(get("/api/application/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(applicationService, times(1)).findById(1L);
    }

    @Test
    void saveReturnsCreatedApplication() throws Exception {
        ApplicationRequestDTO applicationRequestDTO = new ApplicationRequestDTO();
        ApplicationResponseDTO applicationResponseDTO = new ApplicationResponseDTO();
        when(applicationService.saveApplication(any(ApplicationRequestDTO.class))).thenReturn(applicationResponseDTO);

        mockMvc.perform(post("/api/application/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(applicationRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(applicationResponseDTO)));

        verify(applicationService, times(1)).saveApplication(any(ApplicationRequestDTO.class));
    }

    @Test
    void deleteByIdReturnsNoContent() throws Exception {
        doNothing().when(applicationService).deleteById(1L);

        mockMvc.perform(delete("/api/application/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(applicationService, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdReturnsNotFoundWhenApplicationDoesNotExist() throws Exception {
        doThrow(new AppException("Application not found", "NOT_FOUND")).when(applicationService).deleteById(1L);

        mockMvc.perform(delete("/api/application/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(applicationService, times(1)).deleteById(1L);
    }

    @Test
    void findApplicationsByUserIdReturnsListOfApplications() throws Exception {
        ApplicationResponseDTO applicationResponseDTO = new ApplicationResponseDTO();
        when(applicationService.findApplicationsByUserId(1L)).thenReturn(List.of(applicationResponseDTO));

        mockMvc.perform(get("/api/application/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(applicationResponseDTO))));

        verify(applicationService, times(1)).findApplicationsByUserId(1L);
    }


    @Test
    void findApplicationsByJobOfferIdReturnsListOfApplications() throws Exception {
        ApplicationResponseDTO applicationResponseDTO = new ApplicationResponseDTO();
        when(applicationService.findApplicationsByJobOfferId(1L)).thenReturn(List.of(applicationResponseDTO));

        mockMvc.perform(get("/api/application/job-offer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(applicationResponseDTO))));

        verify(applicationService, times(1)).findApplicationsByJobOfferId(1L);
    }

    @Test
    void findApplicationsByJobOfferIdReturnsNotFoundWhenNoApplications() throws Exception {
        doThrow(new AppException("No applications found", "NOT_FOUND")).when(applicationService).findApplicationsByJobOfferId(1L);

        mockMvc.perform(get("/api/application/job-offer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(applicationService, times(1)).findApplicationsByJobOfferId(1L);
    }

    @Test
    void findApplicationByUserIdAndJobOfferIdReturnsApplication() throws Exception {
        ApplicationResponseDTO applicationResponseDTO = new ApplicationResponseDTO();
        when(applicationService.findApplicationByUserIdAndJobOfferId(1L, 1L)).thenReturn(applicationResponseDTO);

        mockMvc.perform(get("/api/application/user/1/job-offer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(applicationResponseDTO)));

        verify(applicationService, times(1)).findApplicationByUserIdAndJobOfferId(1L, 1L);
    }

    @Test
    void findApplicationByUserIdAndJobOfferIdReturnsNotFoundWhenApplicationDoesNotExist() throws Exception {
        doThrow(new AppException("Application not found", "NOT_FOUND")).when(applicationService).findApplicationByUserIdAndJobOfferId(1L, 1L);

        mockMvc.perform(get("/api/application/user/1/job-offer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(applicationService, times(1)).findApplicationByUserIdAndJobOfferId(1L, 1L);
    }

}