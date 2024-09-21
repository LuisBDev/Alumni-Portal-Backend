package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.model.Enrollment;
import com.alumniportal.unmsm.persistence.IEnrollmentDAO;
import com.alumniportal.unmsm.service.IEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentServiceImpl implements IEnrollmentService {

    @Autowired
    private IEnrollmentDAO enrollmentDAO;

    @Override
    public List<Enrollment> findAll() {
        return enrollmentDAO.findAll();
    }

    @Override
    public Enrollment findById(Long id) {
        return enrollmentDAO.findById(id);
    }

    @Override
    public void save(Enrollment enrollment) {
        enrollmentDAO.save(enrollment);
    }

    @Override
    public void deleteById(Long id) {
        enrollmentDAO.deleteById(id);
    }

    @Override
    public List<Enrollment> findEnrollmentsByUser_Id(Long userId) {
        return enrollmentDAO.findEnrollmentsByUser_Id(userId);
    }

    @Override
    public List<Enrollment> findEnrollmentsByActivity_Id(Long activityId) {
        return enrollmentDAO.findEnrollmentsByActivity_Id(activityId);
    }
}
