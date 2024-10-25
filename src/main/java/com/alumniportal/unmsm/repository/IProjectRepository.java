package com.alumniportal.unmsm.repository;

import com.alumniportal.unmsm.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProjectRepository extends JpaRepository<Project, Long> {

    @Query("select p from Project p where p.user.id = ?1")
    List<Project> findProjectsByUserId(Long userId);
}
