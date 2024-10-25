package com.alumniportal.unmsm.repository;

import com.alumniportal.unmsm.model.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEducationRepository extends JpaRepository<Education, Long> {

    @Query("select e from Education e where e.user.id = ?1")
    List<Education> findEducationsByUserId(Long userId);

}
