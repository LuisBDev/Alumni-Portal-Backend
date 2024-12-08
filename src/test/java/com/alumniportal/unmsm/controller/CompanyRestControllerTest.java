package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.config.security.JwtService;
import com.alumniportal.unmsm.config.security.SecurityTestConfig;
import com.alumniportal.unmsm.dto.request.PasswordChangeRequestDTO;
import com.alumniportal.unmsm.dto.response.CompanyResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.service.interfaces.ICompanyService;
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

@WebMvcTest(CompanyRestController.class)
@Import(SecurityTestConfig.class)
class CompanyRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ICompanyService companyService;

    @Test
    void findAllReturnsListOfCompanies() throws Exception {
        CompanyResponseDTO companyResponseDTO = new CompanyResponseDTO();
        when(companyService.findAll()).thenReturn(List.of(companyResponseDTO));

        mockMvc.perform(get("/api/company/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(companyService, times(1)).findAll();
    }

    @Test
    void findAllReturnsNotFoundWhenNoCompaniesExist() throws Exception {
        doThrow(new AppException("No company found", "NOT_FOUND")).when(companyService).findAll();

        mockMvc.perform(get("/api/company/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(companyService, times(1)).findAll();
    }

    @Test
    void findByIdReturnsCompany() throws Exception {
        CompanyResponseDTO companyResponseDTO = new CompanyResponseDTO();
        when(companyService.findById(1L)).thenReturn(companyResponseDTO);

        mockMvc.perform(get("/api/company/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(companyResponseDTO)));

        verify(companyService, times(1)).findById(1L);
    }

    @Test
    void findByIdReturnsNotFoundWhenCompanyDoesNotExist() throws Exception {
        doThrow(new AppException("Company not found", "NOT_FOUND")).when(companyService).findById(1L);

        mockMvc.perform(get("/api/company/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(companyService, times(1)).findById(1L);
    }

    @Test
    void deleteByIdReturnsNoContent() throws Exception {
        doNothing().when(companyService).deleteById(1L);

        mockMvc.perform(delete("/api/company/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(companyService, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdReturnsNotFoundWhenCompanyDoesNotExist() throws Exception {
        doThrow(new AppException("Company not found", "NOT_FOUND")).when(companyService).deleteById(1L);

        mockMvc.perform(delete("/api/company/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(companyService, times(1)).deleteById(1L);
    }

    @Test
    void findByEmailReturnsCompany() throws Exception {
        CompanyResponseDTO companyResponseDTO = new CompanyResponseDTO();
        when(companyService.findByEmail("test@example.com")).thenReturn(companyResponseDTO);

        mockMvc.perform(get("/api/company/email/test@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(companyResponseDTO)));

        verify(companyService, times(1)).findByEmail("test@example.com");
    }


    @Test
    void updateReturnsUpdatedCompany() throws Exception {
        CompanyResponseDTO companyResponseDTO = new CompanyResponseDTO();
        Map<String, Object> updates = Map.of("field", "value");
        when(companyService.updateCompany(eq(1L), anyMap())).thenReturn(companyResponseDTO);

        mockMvc.perform(patch("/api/company/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updates)))
                .andExpect(status().isOk());

        verify(companyService, times(1)).updateCompany(eq(1L), anyMap());
    }

    @Test
    void updatePasswordReturnsNoContent() throws Exception {
        PasswordChangeRequestDTO passwordChangeRequestDTO = new PasswordChangeRequestDTO();
        doNothing().when(companyService).updatePassword(eq(1L), any(PasswordChangeRequestDTO.class));

        mockMvc.perform(post("/api/company/updatePassword/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordChangeRequestDTO)))
                .andExpect(status().isNoContent());

        verify(companyService, times(1)).updatePassword(eq(1L), any(PasswordChangeRequestDTO.class));
    }

}