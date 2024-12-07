package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.request.JobOfferRequestDTO;
import com.alumniportal.unmsm.dto.response.JobOfferResponseDTO;
import com.alumniportal.unmsm.service.interfaces.IJobOfferService;
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
    public ResponseEntity<List<JobOfferResponseDTO>> findAll() {
        List<JobOfferResponseDTO> jobOfferResponseDTOList = jobOfferService.findAll();
        return ResponseEntity.ok(jobOfferResponseDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobOfferResponseDTO> findById(@PathVariable Long id) {
        JobOfferResponseDTO jobOfferResponseDTO = jobOfferService.findById(id);
        return ResponseEntity.ok(jobOfferResponseDTO);
    }

    @PostMapping("/save/{companyId}")
    public ResponseEntity<JobOfferResponseDTO> save(@RequestBody JobOfferRequestDTO jobOfferRequestDTO, @PathVariable Long companyId) {
        JobOfferResponseDTO jobOfferResponseDTO = jobOfferService.saveJobOffer(jobOfferRequestDTO, companyId);
        return ResponseEntity.status(HttpStatus.CREATED).body(jobOfferResponseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<JobOfferResponseDTO> update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        JobOfferResponseDTO jobOfferResponseDTO = jobOfferService.updateJobOffer(id, updates);
        return ResponseEntity.ok(jobOfferResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        jobOfferService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<JobOfferResponseDTO>> findJobOffersByCompanyId(@PathVariable Long companyId) {
        List<JobOfferResponseDTO> jobOffersByCompanyId = jobOfferService.findJobOffersByCompanyId(companyId);
        return ResponseEntity.ok(jobOffersByCompanyId);
    }


}
