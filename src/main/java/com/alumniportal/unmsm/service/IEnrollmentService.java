package com.alumniportal.unmsm.service;

import com.alumniportal.unmsm.model.Enrollment;

import java.util.List;

public interface IEnrollmentService {
    List<Enrollment> findAll();

    Enrollment findById(Long id);

    void save(Enrollment enrollment);

    void deleteById(Long id);

    List<Enrollment> findEnrollmentsByUser_Id(Long userId);

    List<Enrollment> findEnrollmentsByActivity_Id(Long activityId);

    public void saveEnrollment(Enrollment enrollment);

}
