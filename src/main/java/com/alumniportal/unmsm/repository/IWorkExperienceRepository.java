package com.alumniportal.unmsm.repository;

import com.alumniportal.unmsm.model.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWorkExperienceRepository extends JpaRepository<WorkExperience, Long> {

    List<WorkExperience> findWorkExperiencesByUser_Id(Long userId);

}
