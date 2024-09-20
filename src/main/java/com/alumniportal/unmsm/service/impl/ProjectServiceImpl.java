package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.model.Project;
import com.alumniportal.unmsm.persistence.IProjectDAO;
import com.alumniportal.unmsm.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    private IProjectDAO projectDAO;


    @Override
    public List<Project> findAll() {
        return projectDAO.findAll();
    }

    @Override
    public Project findById(Long id) {
        return projectDAO.findById(id);
    }

    @Override
    public void save(Project project) {
        projectDAO.save(project);
    }

    @Override
    public void deleteById(Long id) {
        projectDAO.deleteById(id);
    }

    @Override
    public List<Project> findProjectsByUser_Id(Long userId) {
        return projectDAO.findProjectsByUser_Id(userId);
    }
}
