package com.alumniportal.unmsm.persistence;

import com.alumniportal.unmsm.model.Enrollment;

import java.util.List;

public interface IEnrollmentDAO {

    List<Enrollment> findAll();

    Enrollment findById(Long id);

    void save(Enrollment enrollment);

    void deleteById(Long id);

    List<Enrollment> findEnrollmentsByUserId(Long userId);

    List<Enrollment> findEnrollmentsByActivityId(Long activityId);

    Enrollment findEnrollmentByUserIdAndActivityId(Long userId, Long activityId);


}
