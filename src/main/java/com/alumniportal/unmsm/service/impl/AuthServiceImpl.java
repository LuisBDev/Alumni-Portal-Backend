package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.config.SpringSecurity.JwtService;
import com.alumniportal.unmsm.dto.RequestDTO.CompanyRequestDTO;
import com.alumniportal.unmsm.dto.RequestDTO.UserRequestDTO;
import com.alumniportal.unmsm.dto.ResponseDTO.AuthCompanyResponseDTO;
import com.alumniportal.unmsm.dto.ResponseDTO.AuthUserResponseDTO;
import com.alumniportal.unmsm.dto.RequestDTO.LoginRequestDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.CompanyMapper;
import com.alumniportal.unmsm.mapper.UserMapper;
import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.model.Role;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.interfaces.ICompanyDAO;
import com.alumniportal.unmsm.persistence.interfaces.IUserDAO;
import com.alumniportal.unmsm.service.interfaces.IAuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final IUserDAO userDAO;

    private final ICompanyDAO companyDAO;

    private final UserMapper userMapper;

    private final CompanyMapper companyMapper;


    @Override
    public AuthUserResponseDTO loginAcademic(LoginRequestDTO loginRequestDTO) {

        // Autenticar la solicitud usando el authenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
        );

        User user = userDAO.findByEmail(loginRequestDTO.getEmail());
        if (user == null) {
            throw new AppException("User not found with email: " + loginRequestDTO.getEmail(), "NOT_FOUND");
        }

        AuthUserResponseDTO authUserResponseDTO = userMapper.entityToAuthUserResponseDTO(user);
        authUserResponseDTO.setToken(jwtService.generateToken(user));
        return authUserResponseDTO;
    }

    @Override
    public AuthCompanyResponseDTO loginCompany(LoginRequestDTO loginRequestDTO) {

        // Autenticar la solicitud usando el authenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
        );

        Company company = companyDAO.findByEmail(loginRequestDTO.getEmail());
        if (company == null) {
            throw new AppException("Company not found with email: " + loginRequestDTO.getEmail(), "NOT_FOUND");
        }

        AuthCompanyResponseDTO authCompanyResponseDTO = companyMapper.entityToAuthCompanyResponseDTO(company);
        authCompanyResponseDTO.setToken(jwtService.generateToken(company));
        return authCompanyResponseDTO;
    }

    @Override
    public AuthUserResponseDTO registerAcademic(UserRequestDTO userRequestDTO) {

        // Verificar si el usuario ya existe
        if (userDAO.findByEmail(userRequestDTO.getEmail()) != null) {
            throw new AppException("User already exists with email: " + userRequestDTO.getEmail(), "CONFLICT");
        }

        User user = userMapper.requestDtoToEntity(userRequestDTO);

        // Codificar la contraseña y establecer la fecha de creación
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDate.now());
        user.setRole(Role.USER);

        userDAO.save(user);

        AuthUserResponseDTO authUserResponseDTO = userMapper.entityToAuthUserResponseDTO(user);

        authUserResponseDTO.setToken(jwtService.generateToken(user));
        return authUserResponseDTO;
    }

    @Override
    public AuthCompanyResponseDTO registerCompany(CompanyRequestDTO companyRequestDTO) {

        // Verificar si la compañía ya existe
        if (companyDAO.findByEmail(companyRequestDTO.getEmail()) != null) {
            throw new AppException("Company already exists with email: " + companyRequestDTO.getEmail(), "CONFLICT");
        }


        Company company = companyMapper.requestDtoToEntity(companyRequestDTO);

        // Codificar la contraseña y establecer la fecha de creación
        company.setPassword(passwordEncoder.encode(company.getPassword()));
        company.setCreatedAt(LocalDate.now());
        company.setRole(Role.COMPANY);

        // Guardar la compañía y generar el token
        companyDAO.save(company);

        AuthCompanyResponseDTO authCompanyResponseDTO = companyMapper.entityToAuthCompanyResponseDTO(company);

        authCompanyResponseDTO.setToken(jwtService.generateToken(company));
        return authCompanyResponseDTO;
    }

}
