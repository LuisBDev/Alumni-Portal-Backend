package com.alumniportal.unmsm.repository;

import com.alumniportal.unmsm.model.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWorkExperienceRepository extends JpaRepository<WorkExperience, Long> {

    @Query("select w from WorkExperience w where w.user.id = ?1")
    List<WorkExperience> findWorkExperiencesByUserId(Long userId);

}
