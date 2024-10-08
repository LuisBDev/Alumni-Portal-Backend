package com.alumniportal.unmsm.service;

import com.alumniportal.unmsm.dto.EnrollmentDTO;
import com.alumniportal.unmsm.model.Enrollment;

import java.util.List;

public interface IEnrollmentService {

    List<EnrollmentDTO> findAll();

    EnrollmentDTO findById(Long id);

    void save(Enrollment enrollment);

    void deleteById(Long id);

    List<EnrollmentDTO> findEnrollmentsByUser_Id(Long userId);

    List<EnrollmentDTO> findEnrollmentsByActivity_Id(Long activityId);

    public void saveEnrollment(Enrollment enrollment);

}
