package com.alumniportal.unmsm.controller;


import com.alumniportal.unmsm.config.security.JwtService;
import com.alumniportal.unmsm.config.security.SecurityTestConfig;
import com.alumniportal.unmsm.dto.request.JobOfferRequestDTO;
import com.alumniportal.unmsm.dto.response.JobOfferResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.service.interfaces.IJobOfferService;
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

@WebMvcTest(JobOfferRestController.class)
@Import(SecurityTestConfig.class)
class JobOfferRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IJobOfferService jobOfferService;

    @Test
    void findAllReturnsListOfJobOffers() throws Exception {
        JobOfferResponseDTO jobOfferResponseDTO = new JobOfferResponseDTO();
        when(jobOfferService.findAll()).thenReturn(List.of(jobOfferResponseDTO));

        mockMvc.perform(get("/api/job-offer/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(jobOfferService, times(1)).findAll();
    }

    @Test
    void findAllReturnsNotFoundWhenNoJobOffers() throws Exception {
        doThrow(new AppException("No job offers found", "NOT_FOUND")).when(jobOfferService).findAll();
        mockMvc.perform(get("/api/job-offer/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(jobOfferService, times(1)).findAll();
    }

    @Test
    void findByIdReturnsJobOffer() throws Exception {
        JobOfferResponseDTO jobOfferResponseDTO = new JobOfferResponseDTO();
        when(jobOfferService.findById(1L)).thenReturn(jobOfferResponseDTO);

        mockMvc.perform(get("/api/job-offer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(jobOfferResponseDTO)));

        verify(jobOfferService, times(1)).findById(1L);
    }

    @Test
    void findByIdReturnsNotFoundWhenJobOfferDoesNotExist() throws Exception {
        doThrow(new AppException("Job offer not found", "NOT_FOUND")).when(jobOfferService).findById(1L);

        mockMvc.perform(get("/api/job-offer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(jobOfferService, times(1)).findById(1L);
    }

    @Test
    void saveReturnsCreatedJobOffer() throws Exception {
        JobOfferRequestDTO jobOfferRequestDTO = new JobOfferRequestDTO();
        JobOfferResponseDTO jobOfferResponseDTO = new JobOfferResponseDTO();
        when(jobOfferService.saveJobOffer(any(JobOfferRequestDTO.class), eq(1L))).thenReturn(jobOfferResponseDTO);

        mockMvc.perform(post("/api/job-offer/save/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobOfferRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(jobOfferResponseDTO)));

        verify(jobOfferService, times(1)).saveJobOffer(any(JobOfferRequestDTO.class), eq(1L));
    }

    @Test
    void updateReturnsUpdatedJobOffer() throws Exception {
        JobOfferResponseDTO jobOfferResponseDTO = new JobOfferResponseDTO();
        Map<String, Object> updates = Map.of("field", "value");
        when(jobOfferService.updateJobOffer(eq(1L), anyMap())).thenReturn(jobOfferResponseDTO);

        mockMvc.perform(patch("/api/job-offer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updates)))
                .andExpect(status().isOk());

        verify(jobOfferService, times(1)).updateJobOffer(eq(1L), anyMap());
    }

    @Test
    void deleteByIdReturnsNoContent() throws Exception {
        doNothing().when(jobOfferService).deleteById(1L);

        mockMvc.perform(delete("/api/job-offer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(jobOfferService, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdReturnsNotFoundWhenJobOfferDoesNotExist() throws Exception {
        doThrow(new AppException("Job Offer not found", "NOT_FOUND")).when(jobOfferService).deleteById(1L);

        mockMvc.perform(delete("/api/job-offer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(jobOfferService, times(1)).deleteById(1L);
    }

    @Test
    void findJobOffersByCompanyIdReturnsListOfJobOffers() throws Exception {
        JobOfferResponseDTO jobOfferResponseDTO = new JobOfferResponseDTO();
        when(jobOfferService.findJobOffersByCompanyId(1L)).thenReturn(List.of(jobOfferResponseDTO));

        mockMvc.perform(get("/api/job-offer/company/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(jobOfferService, times(1)).findJobOffersByCompanyId(1L);
    }

    @Test
    void findJobOffersByCompanyIdReturnsNotFoundWhenNoJobOffers() throws Exception {
        doThrow(new AppException("No job offers found", "NOT_FOUND")).when(jobOfferService).findJobOffersByCompanyId(1L);

        mockMvc.perform(get("/api/job-offer/company/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(jobOfferService, times(1)).findJobOffersByCompanyId(1L);
    }

}