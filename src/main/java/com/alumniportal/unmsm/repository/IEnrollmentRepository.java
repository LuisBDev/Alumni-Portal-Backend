package com.alumniportal.unmsm.repository;

import com.alumniportal.unmsm.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findEnrollmentsByUser_Id(Long userId);

    List<Enrollment> findEnrollmentsByActivity_Id(Long activityId);


}
