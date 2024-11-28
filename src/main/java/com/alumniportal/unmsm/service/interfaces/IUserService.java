package com.alumniportal.unmsm.service.interfaces;

import com.alumniportal.unmsm.dto.PasswordChangeDTO;
import com.alumniportal.unmsm.dto.UserCVDTO;
import com.alumniportal.unmsm.dto.UserDTO;
import com.alumniportal.unmsm.model.User;

import java.util.List;
import java.util.Map;

public interface IUserService {

    List<UserDTO> findAll();

    UserDTO findById(Long id);

    void save(User user);

    void deleteById(Long id);

    boolean existsByEmail(String email);

    UserDTO findByEmail(String email);

    void saveUser(User user);

    void updateUser(Long id, Map<String, Object> fields);

    UserDTO validateLogin(String email, String password);

    UserCVDTO getUserCV(Long userId);

    void updatePassword(Long id, PasswordChangeDTO passwordChangeDTO);

}
