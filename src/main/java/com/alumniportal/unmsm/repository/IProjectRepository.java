package com.alumniportal.unmsm.repository;

import com.alumniportal.unmsm.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findProjectsByUser_Id(Long userId);
}
