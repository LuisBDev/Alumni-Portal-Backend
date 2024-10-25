package com.alumniportal.unmsm.persistence;

import com.alumniportal.unmsm.model.Project;

import java.util.List;

public interface IProjectDAO {

    List<Project> findAll();

    Project findById(Long id);

    void save(Project project);

    void deleteById(Long id);

    //    Buscar todos los projects de un usuario
    List<Project> findProjectsByUserId(Long userId);


}
