package com.alumniportal.unmsm.service.interfaces;

import com.alumniportal.unmsm.dto.EnrollmentDTO;
import com.alumniportal.unmsm.model.Enrollment;

import java.util.List;

public interface IEnrollmentService {

    List<EnrollmentDTO> findAll();

    EnrollmentDTO findById(Long id);

    void save(Enrollment enrollment);

    void deleteById(Long id);

    List<EnrollmentDTO> findEnrollmentsByUserId(Long userId);

    List<EnrollmentDTO> findEnrollmentsByActivityId(Long activityId);

    EnrollmentDTO findEnrollmentByUserIdAndActivityId(Long userId, Long activityId);

    public void saveEnrollment(Enrollment enrollment);

}
