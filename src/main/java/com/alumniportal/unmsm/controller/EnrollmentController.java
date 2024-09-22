package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.model.Enrollment;
import com.alumniportal.unmsm.service.IEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollment")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class EnrollmentController {

    @Autowired
    private IEnrollmentService enrollmentService;


    @GetMapping("/all")
    public List<Enrollment> findAll() {
        return enrollmentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Enrollment enrollment = enrollmentService.findById(id);
        if (enrollment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(enrollment);
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
    public List<Enrollment> findEnrollmentsByUser_Id(@PathVariable Long userId) {
        return enrollmentService.findEnrollmentsByUser_Id(userId);
    }

    @GetMapping("/activity/{activityId}")
    public List<Enrollment> findEnrollmentsByActivity_Id(@PathVariable Long activityId) {
        return enrollmentService.findEnrollmentsByActivity_Id(activityId);
    }


}
