package com.alumniportal.unmsm.repository;

import com.alumniportal.unmsm.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findActivitiesByUser_Id(Long userId);
    
}
