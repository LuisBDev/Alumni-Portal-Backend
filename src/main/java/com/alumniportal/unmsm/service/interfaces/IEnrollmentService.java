package com.alumniportal.unmsm.service.interfaces;

import com.alumniportal.unmsm.dto.request.EnrollmentRequestDTO;
import com.alumniportal.unmsm.dto.response.EnrollmentResponseDTO;
import com.alumniportal.unmsm.model.Enrollment;

import java.util.List;

public interface IEnrollmentService {

    List<EnrollmentResponseDTO> findAll();

    EnrollmentResponseDTO findById(Long id);

    void save(Enrollment enrollment);

    void deleteById(Long id);

    List<EnrollmentResponseDTO> findEnrollmentsByUserId(Long userId);

    List<EnrollmentResponseDTO> findEnrollmentsByActivityId(Long activityId);

    EnrollmentResponseDTO findEnrollmentByUserIdAndActivityId(Long userId, Long activityId);

    public void saveEnrollment(EnrollmentRequestDTO enrollmentRequestDTO);

}
