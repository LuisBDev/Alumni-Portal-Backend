package com.alumniportal.unmsm.persistence;

import com.alumniportal.unmsm.model.Enrollment;

import java.util.List;

public interface IEnrollmentDAO {

    List<Enrollment> findAll();

    Enrollment findById(Long id);

    void save(Enrollment enrollment);

    void deleteById(Long id);

    List<Enrollment> findEnrollmentsByUser_Id(Long userId);

    List<Enrollment> findEnrollmentsByActivity_Id(Long activityId);


}
