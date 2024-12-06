package com.alumniportal.unmsm.service.interfaces;

import com.alumniportal.unmsm.dto.request.PasswordChangeRequestDTO;
import com.alumniportal.unmsm.dto.response.UserCVResponseDTO;
import com.alumniportal.unmsm.dto.response.UserResponseDTO;
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
    
    UserCVResponseDTO getUserCV(Long userId);

    void updatePassword(Long id, PasswordChangeRequestDTO passwordChangeRequestDTO);

}
