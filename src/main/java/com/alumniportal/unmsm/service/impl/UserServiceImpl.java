package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.IUserDAO;
import com.alumniportal.unmsm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDAO userDAO;

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public User findById(Long id) {
        return userDAO.findById(id);
    }

    @Override
    public void save(User user) {
        userDAO.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userDAO.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDAO.existsByEmail(email);
    }

    @Override
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        boolean emailExists = userDAO.existsByEmail(user.getEmail());
        if (emailExists) {
            throw new RuntimeException("Error: Email already exists!");
        } else {
            user.setCreatedAt(LocalDate.now());
            userDAO.save(user);
        }
    }

    @Override
    public void updateUser(Long id, Map<String, Object> fields) {
        User user = userDAO.findById(id);
        if (user == null) {
            throw new RuntimeException("Error: User not found!");
        }
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(User.class, k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, user, v);
        });
        user.setUpdatedAt(LocalDate.now());
        userDAO.save(user);

    }
}
