package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.EducationDTO;
import com.alumniportal.unmsm.model.Education;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.IEducationDAO;
import com.alumniportal.unmsm.persistence.IUserDAO;
import com.alumniportal.unmsm.service.IEducationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationServiceImpl implements IEducationService {

    @Autowired
    private IEducationDAO educationDAO;

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<EducationDTO> findAll() {
        return educationDAO.findAll()
                .stream()
                .map(education -> modelMapper.map(education, EducationDTO.class))
                .toList();
    }

    @Override
    public EducationDTO findById(Long id) {
        Education education = educationDAO.findById(id);
        if (education == null) {
            return null;
        }
        return modelMapper.map(education, EducationDTO.class);
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
    public List<EducationDTO> findEducationsByUser_Id(Long userId) {
        return educationDAO.findEducationsByUser_Id(userId)
                .stream()
                .map(education -> modelMapper.map(education, EducationDTO.class))
                .toList();
    }

    @Override
    public void saveEducation(Education education, Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
//        Setteando el usuario en la educacion y persistiendo
        education.setUser(user);
        educationDAO.save(education);
//        Agregando la educacion al usuario y persistiendo
        user.getEducationList().add(education);
        userDAO.save(user);
    }
}
