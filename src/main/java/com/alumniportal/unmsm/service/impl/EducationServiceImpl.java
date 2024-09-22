package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.model.Education;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.IEducationDAO;
import com.alumniportal.unmsm.service.IEducationService;
import com.alumniportal.unmsm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationServiceImpl implements IEducationService {

    @Autowired
    private IEducationDAO educationDAO;

    @Autowired
    private IUserService userService;


    @Override
    public List<Education> findAll() {
        return educationDAO.findAll();
    }

    @Override
    public Education findById(Long id) {
        return educationDAO.findById(id);
    }

    @Override
    public void save(Education education) {
        educationDAO.save(education);
    }

    @Override
    public void deleteById(Long id) {
        educationDAO.deleteById(id);
    }

    @Override
    public List<Education> findEducationsByUser_Id(Long userId) {
        return educationDAO.findEducationsByUser_Id(userId);
    }

    @Override
    public void saveEducation(Education education, Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
//        Setteando el usuario en la educacion y persistiendo
        education.setUser(user);
        educationDAO.save(education);
//        Agregando la educacion al usuario y persistiendo
        user.getEducationList().add(education);
        userService.save(user);
    }
}
