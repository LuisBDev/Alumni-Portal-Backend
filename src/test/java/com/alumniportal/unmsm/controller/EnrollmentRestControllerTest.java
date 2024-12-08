package com.alumniportal.unmsm.controller;


import com.alumniportal.unmsm.config.security.JwtService;
import com.alumniportal.unmsm.config.security.SecurityTestConfig;
import com.alumniportal.unmsm.dto.request.EnrollmentRequestDTO;
import com.alumniportal.unmsm.dto.response.EnrollmentResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.service.interfaces.IEnrollmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;


@WebMvcTest(EnrollmentRestController.class)
@Import(SecurityTestConfig.class)
class EnrollmentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IEnrollmentService enrollmentService;

    @Test
    void findAllReturnsListOfEnrollments() throws Exception {
        EnrollmentResponseDTO enrollmentResponseDTO = new EnrollmentResponseDTO();
        when(enrollmentService.findAll()).thenReturn(List.of(enrollmentResponseDTO));

        mockMvc.perform(get("/api/enrollment/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(enrollmentService, times(1)).findAll();
    }

    @Test
    void findAllReturnsNotFoundWhenNoEnrollments() throws Exception {
        doThrow(new AppException("No enrollments found", "NOT_FOUND")).when(enrollmentService).findAll();

        mockMvc.perform(get("/api/enrollment/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(enrollmentService, times(1)).findAll();
    }

    @Test
    void findByIdReturnsEnrollment() throws Exception {
        EnrollmentResponseDTO enrollmentResponseDTO = new EnrollmentResponseDTO();
        when(enrollmentService.findById(1L)).thenReturn(enrollmentResponseDTO);

        mockMvc.perform(get("/api/enrollment/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(enrollmentResponseDTO)));

        verify(enrollmentService, times(1)).findById(1L);
    }

    @Test
    void findByIdReturnsNotFoundWhenEnrollmentDoesNotExist() throws Exception {
        doThrow(new AppException("Enrollment not found", "NOT_FOUND")).when(enrollmentService).findById(1L);

        mockMvc.perform(get("/api/enrollment/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(enrollmentService, times(1)).findById(1L);
    }

    @Test
    void saveReturnsCreatedEnrollment() throws Exception {
        EnrollmentRequestDTO enrollmentRequestDTO = new EnrollmentRequestDTO();
        EnrollmentResponseDTO enrollmentResponseDTO = new EnrollmentResponseDTO();
        when(enrollmentService.saveEnrollment(any(EnrollmentRequestDTO.class))).thenReturn(enrollmentResponseDTO);

        mockMvc.perform(post("/api/enrollment/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enrollmentRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(enrollmentResponseDTO)));

        verify(enrollmentService, times(1)).saveEnrollment(any(EnrollmentRequestDTO.class));
    }

    @Test
    void deleteByIdReturnsNoContent() throws Exception {
        doNothing().when(enrollmentService).deleteById(1L);

        mockMvc.perform(delete("/api/enrollment/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(enrollmentService, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdReturnsNotFoundWhenEnrollmentDoesNotExist() throws Exception {
        doThrow(new AppException("Enrollment not found", "NOT_FOUND")).when(enrollmentService).deleteById(1L);

        mockMvc.perform(delete("/api/enrollment/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(enrollmentService, times(1)).deleteById(1L);
    }

    @Test
    void findEnrollmentsByUserIdReturnsListOfEnrollments() throws Exception {
        EnrollmentResponseDTO enrollmentResponseDTO = new EnrollmentResponseDTO();
        when(enrollmentService.findEnrollmentsByUserId(1L)).thenReturn(List.of(enrollmentResponseDTO));

        mockMvc.perform(get("/api/enrollment/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(enrollmentService, times(1)).findEnrollmentsByUserId(1L);
    }

    @Test
    void findEnrollmentsByUserIdReturnsEmptyListWhenNoEnrollments() throws Exception {
        when(enrollmentService.findEnrollmentsByUserId(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/enrollment/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(enrollmentService, times(1)).findEnrollmentsByUserId(1L);
    }

    @Test
    void findEnrollmentsByActivityIdReturnsListOfEnrollments() throws Exception {
        EnrollmentResponseDTO enrollmentResponseDTO = new EnrollmentResponseDTO();
        when(enrollmentService.findEnrollmentsByActivityId(1L)).thenReturn(List.of(enrollmentResponseDTO));

        mockMvc.perform(get("/api/enrollment/activity/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(enrollmentService, times(1)).findEnrollmentsByActivityId(1L);
    }

    @Test
    void findEnrollmentsByActivityIdReturnsEmptyListWhenNoEnrollments() throws Exception {
        when(enrollmentService.findEnrollmentsByActivityId(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/enrollment/activity/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(enrollmentService, times(1)).findEnrollmentsByActivityId(1L);
    }

    @Test
    void findEnrollmentByUserIdAndActivityIdReturnsEnrollment() throws Exception {
        EnrollmentResponseDTO enrollmentResponseDTO = new EnrollmentResponseDTO();
        when(enrollmentService.findEnrollmentByUserIdAndActivityId(1L, 1L)).thenReturn(enrollmentResponseDTO);

        mockMvc.perform(get("/api/enrollment/user/1/activity/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(enrollmentResponseDTO)));

        verify(enrollmentService, times(1)).findEnrollmentByUserIdAndActivityId(1L, 1L);
    }

    @Test
    void findEnrollmentByUserIdAndActivityIdReturnsNotFoundWhenEnrollmentDoesNotExist() throws Exception {
        doThrow(new AppException("Enrollment not found", "NOT_FOUND")).when(enrollmentService).findEnrollmentByUserIdAndActivityId(1L, 1L);

        mockMvc.perform(get("/api/enrollment/user/1/activity/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(enrollmentService, times(1)).findEnrollmentByUserIdAndActivityId(1L, 1L);
    }

}