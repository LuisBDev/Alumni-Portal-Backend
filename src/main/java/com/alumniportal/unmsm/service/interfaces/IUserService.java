package com.alumniportal.unmsm.service.interfaces;

import com.alumniportal.unmsm.dto.RequestDTO.PasswordChangeRequestDTO;
import com.alumniportal.unmsm.dto.ResponseDTO.UserCVResponseDTO;
import com.alumniportal.unmsm.dto.ResponseDTO.UserResponseDTO;
import com.alumniportal.unmsm.model.User;

import java.util.List;
import java.util.Map;

public interface IUserService {

    List<UserResponseDTO> findAll();

    UserResponseDTO findById(Long id);

    void save(User user);

    void deleteById(Long id);

    boolean existsByEmail(String email);

    UserResponseDTO findByEmail(String email);

    void saveUser(User user);

    void updateUser(Long id, Map<String, Object> fields);

    UserResponseDTO validateLogin(String email, String password);

    UserCVResponseDTO getUserCV(Long userId);

    void updatePassword(Long id, PasswordChangeRequestDTO passwordChangeRequestDTO);

}