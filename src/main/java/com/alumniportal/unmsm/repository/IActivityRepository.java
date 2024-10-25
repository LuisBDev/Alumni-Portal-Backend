package com.alumniportal.unmsm.repository;

import com.alumniportal.unmsm.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IActivityRepository extends JpaRepository<Activity, Long> {

    @Query("select a from Activity a where a.user.id = ?1")
    List<Activity> findActivitiesByUserId(Long userId);

}
