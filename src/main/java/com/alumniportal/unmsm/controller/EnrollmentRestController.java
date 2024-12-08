package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.request.EnrollmentRequestDTO;
import com.alumniportal.unmsm.dto.response.EnrollmentResponseDTO;
import com.alumniportal.unmsm.service.interfaces.IEnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/enrollment")
public class EnrollmentRestController {

    private final IEnrollmentService enrollmentService;


    @GetMapping("/all")
    @Operation(summary = "Get all enrollments")
    @ApiResponse(responseCode = "200", description = "List of enrollments")
    public ResponseEntity<List<EnrollmentResponseDTO>> findAll() {
        List<EnrollmentResponseDTO> enrollmentResponseDTOList = enrollmentService.findAll();
        return ResponseEntity.ok(enrollmentResponseDTOList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get enrollment by id")
    @ApiResponse(responseCode = "200", description = "Return enrollment found")
    public ResponseEntity<EnrollmentResponseDTO> findById(@PathVariable Long id) {
        EnrollmentResponseDTO enrollmentResponseDTO = enrollmentService.findById(id);
        return ResponseEntity.ok(enrollmentResponseDTO);
    }

    @PostMapping("/save")
    @Operation(summary = "Save enrollment")
    @ApiResponse(responseCode = "201", description = "Return enrollment saved")
    public ResponseEntity<EnrollmentResponseDTO> save(@RequestBody EnrollmentRequestDTO enrollmentRequestDTO) {
        EnrollmentResponseDTO enrollmentResponseDTO = enrollmentService.saveEnrollment(enrollmentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete enrollment by id")
    @ApiResponse(responseCode = "204", description = "Enrollment deleted")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        enrollmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get enrollments by user id")
    @ApiResponse(responseCode = "200", description = "List of enrollments found")
    public ResponseEntity<List<EnrollmentResponseDTO>> findEnrollmentsByUserId(@PathVariable Long userId) {
        List<EnrollmentResponseDTO> enrollmentResponseDTOList = enrollmentService.findEnrollmentsByUserId(userId);
        return ResponseEntity.ok(enrollmentResponseDTOList);
    }

    @GetMapping("/activity/{activityId}")
    @Operation(summary = "Get enrollments by activity id")
    @ApiResponse(responseCode = "200", description = "List of enrollments found")
    public ResponseEntity<List<EnrollmentResponseDTO>> findEnrollmentsByActivityId(@PathVariable Long activityId) {
        List<EnrollmentResponseDTO> enrollmentResponseDTOList = enrollmentService.findEnrollmentsByActivityId(activityId);
        return ResponseEntity.ok(enrollmentResponseDTOList);
    }

    @GetMapping("/user/{userId}/activity/{activityId}")
    @Operation(summary = "Get enrollment by user id and activity id")
    @ApiResponse(responseCode = "200", description = "Return enrollment found")
    public ResponseEntity<EnrollmentResponseDTO> findEnrollmentByUserIdAndActivityId(@PathVariable Long userId, @PathVariable Long activityId) {
        EnrollmentResponseDTO enrollmentResponseDTO = enrollmentService.findEnrollmentByUserIdAndActivityId(userId, activityId);
        return ResponseEntity.ok(enrollmentResponseDTO);
    }


}
