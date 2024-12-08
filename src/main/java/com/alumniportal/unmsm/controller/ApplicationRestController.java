package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.request.ApplicationRequestDTO;
import com.alumniportal.unmsm.dto.response.ApplicationResponseDTO;
import com.alumniportal.unmsm.service.interfaces.IApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/application")
public class ApplicationRestController {


    private final IApplicationService applicationService;

    @GetMapping("/all")
    @Operation(summary = "Get all applications - history")
    @ApiResponse(responseCode = "200", description = "List of applications")
    public ResponseEntity<List<ApplicationResponseDTO>> findAll() {
        List<ApplicationResponseDTO> applicationResponseDTOList = applicationService.findAll();
        return ResponseEntity.ok(applicationResponseDTOList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find application by ID")
    @ApiResponse(responseCode = "200", description = "Return application found")
    public ResponseEntity<ApplicationResponseDTO> findById(@PathVariable Long id) {
        ApplicationResponseDTO applicationResponseDTO = applicationService.findById(id);
        return ResponseEntity.ok(applicationResponseDTO);
    }

    @PostMapping("/save")
    @Operation(summary = "Create application for a job offer")
    @ApiResponse(responseCode = "201", description = "Return application created")
    public ResponseEntity<ApplicationResponseDTO> save(@RequestBody ApplicationRequestDTO applicationRequestDTO) {
        ApplicationResponseDTO applicationResponseDTO = applicationService.saveApplication(applicationRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponseDTO);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete application by ID")
    @ApiResponse(responseCode = "204", description = "Application deleted")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        applicationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "List applications by user ID")
    @ApiResponse(responseCode = "200", description = "List of applications by user")
    public ResponseEntity<List<ApplicationResponseDTO>> findApplicationsByUserId(@PathVariable Long userId) {
        List<ApplicationResponseDTO> applicationsByUserId = applicationService.findApplicationsByUserId(userId);
        return ResponseEntity.ok(applicationsByUserId);
    }

    @GetMapping("/job-offer/{jobOfferId}")
    @Operation(summary = "List applications by job offer ID")
    @ApiResponse(responseCode = "200", description = "List of applications by job offer")
    public ResponseEntity<List<ApplicationResponseDTO>> findApplicationsByJobOfferId(@PathVariable Long jobOfferId) {
        List<ApplicationResponseDTO> applicationsByJobOfferId = applicationService.findApplicationsByJobOfferId(jobOfferId);
        return ResponseEntity.ok(applicationsByJobOfferId);
    }

    @GetMapping("/user/{userId}/job-offer/{jobOfferId}")
    @Operation(summary = "Find application by user ID and job offer ID")
    @ApiResponse(responseCode = "200", description = "Return application found")
    public ResponseEntity<ApplicationResponseDTO> findApplicationByUserIdAndJobOfferId(@PathVariable Long userId, @PathVariable Long jobOfferId) {
        ApplicationResponseDTO applicationResponseDTO = applicationService.findApplicationByUserIdAndJobOfferId(userId, jobOfferId);
        return ResponseEntity.ok(applicationResponseDTO);
    }


}
