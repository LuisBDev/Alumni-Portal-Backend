package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.Enrollment;
import com.alumniportal.unmsm.persistence.IEnrollmentDAO;
import com.alumniportal.unmsm.repository.IEnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnrollmentDAOImpl implements IEnrollmentDAO {

    @Autowired
    private IEnrollmentRepository enrollmentRepository;

    @Override
    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    @Override
    public Enrollment findById(Long id) {
        return enrollmentRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Enrollment enrollment) {
        enrollmentRepository.save(enrollment);
    }

    @Override
    public void deleteById(Long id) {
        enrollmentRepository.deleteById(id);
    }

    @Override
    public List<Enrollment> findEnrollmentsByUser_Id(Long userId) {
        return enrollmentRepository.findEnrollmentsByUser_Id(userId);
    }

    @Override
    public List<Enrollment> findEnrollmentsByActivity_Id(Long activityId) {
        return enrollmentRepository.findEnrollmentsByActivity_Id(activityId);
    }
}
