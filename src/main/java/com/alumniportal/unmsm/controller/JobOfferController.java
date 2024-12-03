package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.ResponseDTO.JobOfferResponseDTO;
import com.alumniportal.unmsm.model.JobOffer;
import com.alumniportal.unmsm.service.interfaces.IJobOfferService;
import lombok.RequiredArgsConstructor;
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
    public List<JobOfferResponseDTO> findAll() {
        return jobOfferService.findAll();
    }

    @GetMapping("/{id}")
    public JobOfferResponseDTO findById(@PathVariable Long id) {
        return jobOfferService.findById(id);
    }

    @PostMapping("/save/{companyId}")
    public ResponseEntity<?> save(@RequestBody JobOffer jobOffer, @PathVariable Long companyId) {

        try {
            jobOfferService.saveJobOffer(jobOffer, companyId);
            return ResponseEntity.ok("JobOffer saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            jobOfferService.updateJobOffer(id, updates);
            return ResponseEntity.ok("JobOffer updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        JobOfferResponseDTO jobOfferResponseDTO = jobOfferService.findById(id);
        if (jobOfferResponseDTO == null) {
            return ResponseEntity.badRequest().body("Error: JobOffer not found!");
        }
        jobOfferService.deleteById(id);
        return ResponseEntity.ok("JobOffer deleted successfully!");
    }

    @GetMapping("/company/{companyId}")
    public List<JobOfferResponseDTO> findJobOffersByCompanyId(@PathVariable Long companyId) {
        return jobOfferService.findJobOffersByCompanyId(companyId);
    }


}
