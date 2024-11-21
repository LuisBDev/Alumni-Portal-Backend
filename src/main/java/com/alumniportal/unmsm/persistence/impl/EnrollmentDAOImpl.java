package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.Enrollment;
import com.alumniportal.unmsm.persistence.IEnrollmentDAO;
import com.alumniportal.unmsm.repository.IEnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EnrollmentDAOImpl implements IEnrollmentDAO {


    private final IEnrollmentRepository enrollmentRepository;

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
    public List<Enrollment> findEnrollmentsByUserId(Long userId) {
        return enrollmentRepository.findEnrollmentsByUserId(userId);
    }

    @Override
    public List<Enrollment> findEnrollmentsByActivityId(Long activityId) {
        return enrollmentRepository.findEnrollmentsByActivityId(activityId);
    }

    @Override
    public Enrollment findEnrollmentByUserIdAndActivityId(Long userId, Long activityId) {
        return enrollmentRepository.findEnrollmentByUserIdAndActivityId(userId, activityId);
    }
}
