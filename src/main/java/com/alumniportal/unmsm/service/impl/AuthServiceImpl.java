package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.config.JwtService;
import com.alumniportal.unmsm.dto.AuthResponseDTO;
import com.alumniportal.unmsm.dto.LoginRequestDTO;
import com.alumniportal.unmsm.model.AbstractEntity;
import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.model.Role;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.ICompanyDAO;
import com.alumniportal.unmsm.persistence.IUserDAO;
import com.alumniportal.unmsm.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private ICompanyDAO companyDAO;

    @Override
    public <T extends AbstractEntity> AuthResponseDTO login(LoginRequestDTO loginRequestDTO, Class<T> clazz) {
        // Autenticar la solicitud usando el authenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
        );

        // Encontrar la entidad (User o Company) basada en el email
        AbstractEntity entity = findEntityByEmail(loginRequestDTO.getEmail(), clazz);
        if (entity == null) {
            throw new IllegalArgumentException("Entity not found with email: " + loginRequestDTO.getEmail());
        }

        return AuthResponseDTO.builder()
                .token(generateToken(entity))
                .build();

    }

    private String generateToken(AbstractEntity entity) {
        if (entity instanceof User) {
            return jwtService.generateToken((User) entity);
        } else if (entity instanceof Company) {
            return jwtService.generateToken((Company) entity);
        }
        throw new IllegalArgumentException("Unsupported class: " + entity.getClass());
    }

    @Override
    public <T extends AbstractEntity> AuthResponseDTO register(T entity, Class<T> clazz) {
        // Verificar si la entidad ya existe
        if (findEntityByEmail(entity.getEmail(), clazz) != null) {
            throw new IllegalArgumentException("Entity already exists with email: " + entity.getEmail());
        }

        // Codificar la contraseña y establecer la fecha de creación
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setCreatedAt(LocalDate.now());

        // Guardar la entidad y generar el token
        String token = saveEntityAndGenerateToken(entity, clazz);

        return AuthResponseDTO.builder()
                .token(token)
                .build();
    }

    private <T extends AbstractEntity> AbstractEntity findEntityByEmail(String email, Class<T> clazz) {
        if (clazz == User.class) {
            return clazz.cast(userDAO.findByEmail(email));
        } else if (clazz == Company.class) {
            return clazz.cast(companyDAO.findByEmail(email));
        }
        throw new IllegalArgumentException("Unsupported class: " + clazz);
    }

    private <T extends AbstractEntity> String saveEntityAndGenerateToken(T entity, Class<T> clazz) {
        if (clazz == User.class) {
            User user = (User) entity;
            user.setRole(Role.USER);  // Asignar el rol USER a los usuarios
            userDAO.save(user);
            return jwtService.generateToken(user);
        } else if (clazz == Company.class) {
            Company company = (Company) entity;
            company.setRole(Role.COMPANY);  // Asignar el rol COMPANY a las compañías
            companyDAO.save(company);
            return jwtService.generateToken(company);
        } else {
            throw new IllegalArgumentException("Unsupported class: " + clazz);
        }
    }
}
