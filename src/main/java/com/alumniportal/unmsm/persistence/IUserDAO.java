package com.alumniportal.unmsm.persistence;

import com.alumniportal.unmsm.model.User;

import java.util.List;

public interface IUserDAO {

    List<User> findAll();

    User findById(Long id);

    void save(User user);

    void deleteById(Long id);

    boolean existsByEmail(String email);

    User findByEmail(String email);

    
}
