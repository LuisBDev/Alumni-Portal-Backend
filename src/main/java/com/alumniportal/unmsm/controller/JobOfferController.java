package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.request.JobOfferRequestDTO;
import com.alumniportal.unmsm.dto.response.JobOfferResponseDTO;
import com.alumniportal.unmsm.service.interfaces.IJobOfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/job-offer")
public class JobOfferController {

    private final IJobOfferService jobOfferService;


    @GetMapping("/all")
    @Operation(summary = "Get all job offers")
    @ApiResponse(responseCode = "200", description = "List of job offers")
    public ResponseEntity<List<JobOfferResponseDTO>> findAll() {
        List<JobOfferResponseDTO> jobOfferResponseDTOList = jobOfferService.findAll();
        return ResponseEntity.ok(jobOfferResponseDTOList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find job offer by ID")
    @ApiResponse(responseCode = "200", description = "Return job offer")
    public ResponseEntity<JobOfferResponseDTO> findById(@PathVariable Long id) {
        JobOfferResponseDTO jobOfferResponseDTO = jobOfferService.findById(id);
        return ResponseEntity.ok(jobOfferResponseDTO);
    }

    @PostMapping("/save/{companyId}")
    @Operation(summary = "Create job offer for a company by company ID")
    @ApiResponse(responseCode = "201", description = "Return saved job offer")
    public ResponseEntity<JobOfferResponseDTO> save(@RequestBody JobOfferRequestDTO jobOfferRequestDTO, @PathVariable Long companyId) {
        JobOfferResponseDTO jobOfferResponseDTO = jobOfferService.saveJobOffer(jobOfferRequestDTO, companyId);
        return ResponseEntity.status(HttpStatus.CREATED).body(jobOfferResponseDTO);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update job offer by job offer ID")
    @ApiResponse(responseCode = "200", description = "Return updated job offer")
    public ResponseEntity<JobOfferResponseDTO> update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        JobOfferResponseDTO jobOfferResponseDTO = jobOfferService.updateJobOffer(id, updates);
        return ResponseEntity.ok(jobOfferResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete job offer by ID")
    @ApiResponse(responseCode = "204", description = "Job offer deleted")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        jobOfferService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "List job offers by company ID")
    @ApiResponse(responseCode = "200", description = "List of job offers by company")
    public ResponseEntity<List<JobOfferResponseDTO>> findJobOffersByCompanyId(@PathVariable Long companyId) {
        List<JobOfferResponseDTO> jobOffersByCompanyId = jobOfferService.findJobOffersByCompanyId(companyId);
        return ResponseEntity.ok(jobOffersByCompanyId);
    }


}
