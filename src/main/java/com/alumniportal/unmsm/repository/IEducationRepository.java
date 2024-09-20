package com.alumniportal.unmsm.repository;

import com.alumniportal.unmsm.model.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEducationRepository extends JpaRepository<Education, Long> {

    List<Education> findEducationsByUser_Id(Long userId);

}
