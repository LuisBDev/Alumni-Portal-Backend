package com.alumniportal.unmsm.service;

import com.alumniportal.unmsm.model.Project;

import java.util.List;

public interface IProjectService {

    List<Project> findAll();

    Project findById(Long id);

    void save(Project project);

    void deleteById(Long id);

    //    Buscar todos los projects de un usuario
    List<Project> findProjectsByUser_Id(Long userId);

    
}
