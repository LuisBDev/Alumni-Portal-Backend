package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.RequestDTO.EnrollmentRequestDTO;
import com.alumniportal.unmsm.dto.ResponseDTO.EnrollmentResponseDTO;
import com.alumniportal.unmsm.model.Enrollment;
import com.alumniportal.unmsm.service.interfaces.IEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/enrollment")
public class EnrollmentController {

    private final IEnrollmentService enrollmentService;


    @GetMapping("/all")
    public ResponseEntity<List<EnrollmentResponseDTO>> findAll() {
        List<EnrollmentResponseDTO> enrollmentResponseDTOList = enrollmentService.findAll();
        return ResponseEntity.ok(enrollmentResponseDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDTO> findById(@PathVariable Long id) {
        EnrollmentResponseDTO enrollmentResponseDTO = enrollmentService.findById(id);
        return ResponseEntity.ok(enrollmentResponseDTO);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody EnrollmentRequestDTO enrollmentRequestDTO) {
        enrollmentService.saveEnrollment(enrollmentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        enrollmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EnrollmentResponseDTO>> findEnrollmentsByUserId(@PathVariable Long userId) {
        List<EnrollmentResponseDTO> enrollmentResponseDTOList = enrollmentService.findEnrollmentsByUserId(userId);
        return ResponseEntity.ok(enrollmentResponseDTOList);
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<EnrollmentResponseDTO>> findEnrollmentsByActivityId(@PathVariable Long activityId) {
        List<EnrollmentResponseDTO> enrollmentResponseDTOList = enrollmentService.findEnrollmentsByActivityId(activityId);
        return ResponseEntity.ok(enrollmentResponseDTOList);
    }

    @GetMapping("/user/{userId}/activity/{activityId}")
    public ResponseEntity<EnrollmentResponseDTO> findEnrollmentByUserIdAndActivityId(@PathVariable Long userId, @PathVariable Long activityId) {
        EnrollmentResponseDTO enrollmentResponseDTO = enrollmentService.findEnrollmentByUserIdAndActivityId(userId, activityId);
        return ResponseEntity.ok(enrollmentResponseDTO);
    }


}
