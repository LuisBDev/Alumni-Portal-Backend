package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.model.JobOffer;
import com.alumniportal.unmsm.service.IJobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-offer")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class JobOfferController {

    @Autowired
    private IJobOfferService jobOfferService;


    @GetMapping("/all")
    public List<JobOffer> findAll() {
        return jobOfferService.findAll();
    }

    @GetMapping("/{id}")
    public JobOffer findById(@PathVariable Long id) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        JobOffer jobOffer = jobOfferService.findById(id);
        if (jobOffer == null) {
            return ResponseEntity.badRequest().body("Error: JobOffer not found!");
        }
        jobOfferService.deleteById(id);
        return ResponseEntity.ok("JobOffer deleted successfully!");
    }

    @GetMapping("/company/{companyId}")
    public List<JobOffer> findJobOffersByCompany_Id(@PathVariable Long companyId) {
        return jobOfferService.findJobOffersByCompany_Id(companyId);
    }


}
