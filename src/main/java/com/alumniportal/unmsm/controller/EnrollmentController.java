package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.RequestDTO.EnrollmentRequestDTO;
import com.alumniportal.unmsm.dto.ResponseDTO.EnrollmentResponseDTO;
import com.alumniportal.unmsm.model.Enrollment;
import com.alumniportal.unmsm.service.interfaces.IEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/enrollment")
public class EnrollmentController {

    private final IEnrollmentService enrollmentService;


    @GetMapping("/all")
    public List<EnrollmentResponseDTO> findAll() {
        return enrollmentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        EnrollmentResponseDTO enrollmentResponseDTO = enrollmentService.findById(id);
        if (enrollmentResponseDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(enrollmentResponseDTO);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody EnrollmentRequestDTO enrollmentRequestDTO) {

        try {
            enrollmentService.saveEnrollment(enrollmentRequestDTO);
            return ResponseEntity.ok("Enrollment saved successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while saving the enrollment");
        }

    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        enrollmentService.deleteById(id);
    }

    @GetMapping("/user/{userId}")
    public List<EnrollmentResponseDTO> findEnrollmentsByUserId(@PathVariable Long userId) {
        return enrollmentService.findEnrollmentsByUserId(userId);
    }

    @GetMapping("/activity/{activityId}")
    public List<EnrollmentResponseDTO> findEnrollmentsByActivityId(@PathVariable Long activityId) {
        return enrollmentService.findEnrollmentsByActivityId(activityId);
    }

    @GetMapping("/user/{userId}/activity/{activityId}")
    public ResponseEntity<EnrollmentResponseDTO> findEnrollmentByUserIdAndActivityId(@PathVariable Long userId, @PathVariable Long activityId) {
        EnrollmentResponseDTO enrollmentResponseDTO = enrollmentService.findEnrollmentByUserIdAndActivityId(userId, activityId);
        if (enrollmentResponseDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(enrollmentResponseDTO);
    }


}
