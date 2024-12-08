package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.config.security.JwtService;
import com.alumniportal.unmsm.config.security.SecurityTestConfig;
import com.alumniportal.unmsm.dto.request.CompanyRequestDTO;
import com.alumniportal.unmsm.dto.request.LoginRequestDTO;
import com.alumniportal.unmsm.dto.request.UserRequestDTO;
import com.alumniportal.unmsm.dto.response.AuthCompanyResponseDTO;
import com.alumniportal.unmsm.dto.response.AuthUserResponseDTO;
import com.alumniportal.unmsm.service.interfaces.IAuthService;
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


@WebMvcTest(AuthRestController.class)
@Import(SecurityTestConfig.class)
class AuthRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IAuthService authService;

    @Test
    void loginAcademicReturnsAuthenticatedUser() throws Exception {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        AuthUserResponseDTO authUserResponseDTO = new AuthUserResponseDTO();
        when(authService.loginAcademic(any(LoginRequestDTO.class))).thenReturn(authUserResponseDTO);

        mockMvc.perform(post("/api/user/loginAcademic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(authUserResponseDTO)));

        verify(authService, times(1)).loginAcademic(any(LoginRequestDTO.class));
    }

    @Test
    void registerAcademicReturnsCreatedUser() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        AuthUserResponseDTO authUserResponseDTO = new AuthUserResponseDTO();
        when(authService.registerAcademic(any(UserRequestDTO.class))).thenReturn(authUserResponseDTO);

        mockMvc.perform(post("/api/user/registerAcademic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(authUserResponseDTO)));

        verify(authService, times(1)).registerAcademic(any(UserRequestDTO.class));
    }

    @Test
    void loginCompanyReturnsAuthenticatedCompany() throws Exception {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        AuthCompanyResponseDTO authCompanyResponseDTO = new AuthCompanyResponseDTO();
        when(authService.loginCompany(any(LoginRequestDTO.class))).thenReturn(authCompanyResponseDTO);

        mockMvc.perform(post("/api/company/loginCompany")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(authCompanyResponseDTO)));

        verify(authService, times(1)).loginCompany(any(LoginRequestDTO.class));
    }

    @Test
    void registerCompanyReturnsCreatedCompany() throws Exception {
        CompanyRequestDTO companyRequestDTO = new CompanyRequestDTO();
        AuthCompanyResponseDTO authCompanyResponseDTO = new AuthCompanyResponseDTO();
        when(authService.registerCompany(any(CompanyRequestDTO.class))).thenReturn(authCompanyResponseDTO);

        mockMvc.perform(post("/api/company/registerCompany")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(authCompanyResponseDTO)));

        verify(authService, times(1)).registerCompany(any(CompanyRequestDTO.class));
    }

}