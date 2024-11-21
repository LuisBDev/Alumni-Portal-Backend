package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.EnrollmentDTO;
import com.alumniportal.unmsm.model.Enrollment;
import com.alumniportal.unmsm.service.IEnrollmentService;
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
    public List<EnrollmentDTO> findAll() {
        return enrollmentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        EnrollmentDTO enrollmentDTO = enrollmentService.findById(id);
        if (enrollmentDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(enrollmentDTO);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Enrollment enrollment) {

        try {
            enrollmentService.saveEnrollment(enrollment);
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
    public List<EnrollmentDTO> findEnrollmentsByUserId(@PathVariable Long userId) {
        return enrollmentService.findEnrollmentsByUserId(userId);
    }

    @GetMapping("/activity/{activityId}")
    public List<EnrollmentDTO> findEnrollmentsByActivityId(@PathVariable Long activityId) {
        return enrollmentService.findEnrollmentsByActivityId(activityId);
    }

    @GetMapping("/user/{userId}/activity/{activityId}")
    public ResponseEntity<EnrollmentDTO> findEnrollmentByUserIdAndActivityId(@PathVariable Long userId, @PathVariable Long activityId) {
        EnrollmentDTO enrollmentDTO = enrollmentService.findEnrollmentByUserIdAndActivityId(userId, activityId);
        if (enrollmentDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(enrollmentDTO);
    }


}
