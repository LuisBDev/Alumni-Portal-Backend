package com.alumniportal.unmsm.service;

import com.alumniportal.unmsm.model.User;

import java.util.List;
import java.util.Map;

public interface IUserService {

    List<User> findAll();

    User findById(Long id);

    void save(User user);

    void deleteById(Long id);

    boolean existsByEmail(String email);

    User findByEmail(String email);

    void saveUser(User user);

    void updateUser(Long id, Map<String, Object> fields);

}
