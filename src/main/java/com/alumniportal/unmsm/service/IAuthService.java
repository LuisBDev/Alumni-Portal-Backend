package com.alumniportal.unmsm.service;

import com.alumniportal.unmsm.dto.AuthResponseDTO;
import com.alumniportal.unmsm.dto.LoginRequestDTO;
import com.alumniportal.unmsm.model.AbstractEntity;
import com.alumniportal.unmsm.model.User;

public interface IAuthService {


    <T extends AbstractEntity> AuthResponseDTO login(LoginRequestDTO loginRequestDTO, Class<T> clazz);


    <T extends AbstractEntity> AuthResponseDTO register(T entity, Class<T> clazz);
}
