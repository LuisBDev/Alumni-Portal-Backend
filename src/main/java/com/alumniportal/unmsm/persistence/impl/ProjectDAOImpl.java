package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.Project;
import com.alumniportal.unmsm.persistence.IProjectDAO;
import com.alumniportal.unmsm.repository.IProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectDAOImpl implements IProjectDAO {

    @Autowired
    private IProjectRepository projectRepository;


    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project findById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Project project) {
        projectRepository.save(project);
    }

    @Override
    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public List<Project> findProjectsByUser_Id(Long userId) {
        return projectRepository.findProjectsByUser_Id(userId);
    }
}
