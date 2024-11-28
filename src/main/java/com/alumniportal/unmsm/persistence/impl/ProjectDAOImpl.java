package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.Project;
import com.alumniportal.unmsm.persistence.interfaces.IProjectDAO;
import com.alumniportal.unmsm.repository.IProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectDAOImpl implements IProjectDAO {


    private final IProjectRepository projectRepository;


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
    public List<Project> findProjectsByUserId(Long userId) {
        return projectRepository.findProjectsByUserId(userId);
    }
}
