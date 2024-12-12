package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.config.security.JwtService;
import com.alumniportal.unmsm.dto.request.CompanyRequestDTO;
import com.alumniportal.unmsm.dto.request.UserRequestDTO;
import com.alumniportal.unmsm.dto.response.AuthCompanyResponseDTO;
import com.alumniportal.unmsm.dto.response.AuthUserResponseDTO;
import com.alumniportal.unmsm.dto.request.LoginRequestDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.CompanyMapper;
import com.alumniportal.unmsm.mapper.UserMapper;
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
    private UserMapper userMapper;

    private CompanyMapper companyMapper;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {

        userMapper = new UserMapper(new ModelMapper());
        companyMapper = new CompanyMapper(new ModelMapper());
        authService = new AuthServiceImpl(authenticationManager, jwtService, passwordEncoder, userDAO, companyDAO, userMapper, companyMapper);
    }

    @Test
    void loginAcademicReturnsAuthUserResponseWhenCredentialsAreValid() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("user@example.com", "password");
        User user = new User();
        user.setEmail("user@example.com");
        when(userDAO.findByEmail("user@example.com")).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("token");

        AuthUserResponseDTO response = authService.loginAcademic(loginRequestDTO);

        assertNotNull(response);
        assertEquals("token", response.getToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void loginAcademicThrowsExceptionWhenUserNotFound() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("user@example.com", "password");
        when(userDAO.findByEmail("user@example.com")).thenReturn(null);

        Exception exception = assertThrows(AppException.class, () -> authService.loginAcademic(loginRequestDTO));

        assertEquals("User not found with email: user@example.com", exception.getMessage());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void loginAcademicThrowsExceptionWhenAuthenticationFails() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("user@example.com", "wrongpassword");
        doThrow(new RuntimeException("Authentication failed")).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        Exception exception = assertThrows(RuntimeException.class, () -> authService.loginAcademic(loginRequestDTO));

        assertEquals("Authentication failed", exception.getMessage());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void loginCompanyReturnsAuthCompanyResponseWhenCredentialsAreValid() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("company@example.com", "password");
        Company company = new Company();
        company.setEmail("company@example.com");

        when(companyDAO.findByEmail("company@example.com")).thenReturn(company);
        when(jwtService.generateToken(company)).thenReturn("token");

        AuthCompanyResponseDTO response = authService.loginCompany(loginRequestDTO);

        assertNotNull(response);
        assertEquals("token", response.getToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void loginCompanyThrowsExceptionWhenCompanyNotFound() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("company@example.com", "password");
        when(companyDAO.findByEmail("company@example.com")).thenReturn(null);

        Exception exception = assertThrows(AppException.class, () -> authService.loginCompany(loginRequestDTO));

        assertEquals("Company not found with email: company@example.com", exception.getMessage());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void registerAcademicReturnsAuthUserResponseWhenUserIsRegisteredSuccessfully() {
        UserRequestDTO user = new UserRequestDTO();
        user.setEmail("newuser@example.com");
        user.setPassword("password");
        when(userDAO.findByEmail("newuser@example.com")).thenReturn(null);
        when(jwtService.generateToken(any(User.class))).thenReturn("token");

        AuthUserResponseDTO response = authService.registerAcademic(user);

        assertNotNull(response);
        assertEquals("token", response.getToken());
    }

    @Test
    void registerAcademicThrowsExceptionWhenUserAlreadyExists() {
        UserRequestDTO user = new UserRequestDTO();
        user.setEmail("existinguser@example.com");

        User existingUser = new User();

        when(userDAO.findByEmail("existinguser@example.com")).thenReturn(existingUser);

        Exception exception = assertThrows(AppException.class, () -> authService.registerAcademic(user));

        assertEquals("User already exists with email: existinguser@example.com", exception.getMessage());
        verify(userDAO, never()).save(any(User.class));
    }

    @Test
    void registerCompanyReturnsAuthCompanyResponseWhenCompanyIsRegisteredSuccessfully() {
        CompanyRequestDTO company = new CompanyRequestDTO();
        company.setEmail("newcompany@example.com");
        company.setPassword("password");
        when(companyDAO.findByEmail("newcompany@example.com")).thenReturn(null);
        when(jwtService.generateToken(any(Company.class))).thenReturn("token");

        AuthCompanyResponseDTO response = authService.registerCompany(company);

        assertNotNull(response);
        assertEquals("token", response.getToken());

    }

    @Test
    void registerCompanyThrowsExceptionWhenCompanyAlreadyExists() {
        CompanyRequestDTO company = new CompanyRequestDTO();
        company.setEmail("existingcompany@example.com");

        Company existingCompany = new Company();

        when(companyDAO.findByEmail("existingcompany@example.com")).thenReturn(existingCompany);

        Exception exception = assertThrows(AppException.class, () -> authService.registerCompany(company));

        assertEquals("Company already exists with email: existingcompany@example.com", exception.getMessage());
        verify(companyDAO, never()).save(any(Company.class));
    }


}