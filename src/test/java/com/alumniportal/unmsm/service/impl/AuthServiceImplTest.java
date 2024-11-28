package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.config.SpringSecurity.JwtService;
import com.alumniportal.unmsm.dto.AuthCompanyResponse;
import com.alumniportal.unmsm.dto.AuthUserResponse;
import com.alumniportal.unmsm.dto.LoginRequestDTO;
import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.interfaces.ICompanyDAO;
import com.alumniportal.unmsm.persistence.interfaces.IUserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private IUserDAO userDAO;

    @Mock
    private ICompanyDAO companyDAO;

    //    Real modelMapper
    private ModelMapper modelMapper;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {

        modelMapper = new ModelMapper();
        authService = new AuthServiceImpl(authenticationManager, jwtService, passwordEncoder, userDAO, companyDAO, modelMapper);
    }

    @Test
    void loginAcademic_ReturnsAuthUserResponse_WhenCredentialsAreValid() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("user@example.com", "password");
        User user = new User();
        user.setEmail("user@example.com");
        when(userDAO.findByEmail("user@example.com")).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("token");

        AuthUserResponse response = authService.loginAcademic(loginRequestDTO);

        assertNotNull(response);
        assertEquals("token", response.getToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void loginAcademic_ThrowsException_WhenUserNotFound() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("user@example.com", "password");
        when(userDAO.findByEmail("user@example.com")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> authService.loginAcademic(loginRequestDTO));

        assertEquals("User not found with email: user@example.com", exception.getMessage());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void loginAcademic_ThrowsException_WhenAuthenticationFails() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("user@example.com", "wrongpassword");
        doThrow(new RuntimeException("Authentication failed")).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        Exception exception = assertThrows(RuntimeException.class, () -> authService.loginAcademic(loginRequestDTO));

        assertEquals("Authentication failed", exception.getMessage());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void loginCompany_ReturnsAuthCompanyResponse_WhenCredentialsAreValid() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("company@example.com", "password");
        Company company = new Company();
        company.setEmail("company@example.com");

        when(companyDAO.findByEmail("company@example.com")).thenReturn(company);
        when(jwtService.generateToken(company)).thenReturn("token");

        AuthCompanyResponse response = authService.loginCompany(loginRequestDTO);

        assertNotNull(response);
        assertEquals("token", response.getToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void loginCompany_ThrowsException_WhenCompanyNotFound() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("company@example.com", "password");
        when(companyDAO.findByEmail("company@example.com")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> authService.loginCompany(loginRequestDTO));

        assertEquals("Company not found with email: company@example.com", exception.getMessage());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void registerAcademic_ReturnsAuthUserResponse_WhenUserIsRegisteredSuccessfully() {
        User user = new User();
        user.setEmail("newuser@example.com");
        user.setPassword("password");
        when(userDAO.findByEmail("newuser@example.com")).thenReturn(null);
        when(jwtService.generateToken(user)).thenReturn("token");

        AuthUserResponse response = authService.registerAcademic(user);

        assertNotNull(response);
        assertEquals("token", response.getToken());
        verify(userDAO, times(1)).save(user);
    }

    @Test
    void registerAcademic_ThrowsException_WhenUserAlreadyExists() {
        User user = new User();
        user.setEmail("existinguser@example.com");
        when(userDAO.findByEmail("existinguser@example.com")).thenReturn(user);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> authService.registerAcademic(user));

        assertEquals("User already exists with email: existinguser@example.com", exception.getMessage());
        verify(userDAO, never()).save(any(User.class));
    }

    @Test
    void registerCompany_ReturnsAuthCompanyResponse_WhenCompanyIsRegisteredSuccessfully() {
        Company company = new Company();
        company.setEmail("newcompany@example.com");
        company.setPassword("password");
        when(companyDAO.findByEmail("newcompany@example.com")).thenReturn(null);
        when(jwtService.generateToken(company)).thenReturn("token");

        AuthCompanyResponse response = authService.registerCompany(company);

        assertNotNull(response);
        assertEquals("token", response.getToken());
        verify(companyDAO, times(1)).save(company);
    }

    @Test
    void registerCompany_ThrowsException_WhenCompanyAlreadyExists() {
        Company company = new Company();
        company.setEmail("existingcompany@example.com");
        when(companyDAO.findByEmail("existingcompany@example.com")).thenReturn(company);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> authService.registerCompany(company));

        assertEquals("Company already exists with email: existingcompany@example.com", exception.getMessage());
        verify(companyDAO, never()).save(any(Company.class));
    }


}