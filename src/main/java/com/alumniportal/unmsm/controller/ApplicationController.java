package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.ApplicationDTO;
import com.alumniportal.unmsm.model.Application;
import com.alumniportal.unmsm.service.IApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/application")
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class ApplicationController {

    @Autowired
    private IApplicationService applicationService;

    @GetMapping("/all")
    public List<ApplicationDTO> findAll() {
        return applicationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        ApplicationDTO applicationDTO = applicationService.findById(id);
        if (applicationDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(applicationDTO);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Application application) {
        try {
            applicationService.saveApplication(application);
            return ResponseEntity.ok("Application saved successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while saving the application");
        }

    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        applicationService.deleteById(id);
    }

    @GetMapping("/user/{userId}")
    public List<ApplicationDTO> findApplicationsByUserId(@PathVariable Long userId) {
        return applicationService.findApplicationsByUserId(userId);
    }

    @GetMapping("/job-offer/{jobOfferId}")
    public List<ApplicationDTO> findApplicationsByJobOfferId(@PathVariable Long jobOfferId) {
        return applicationService.findApplicationsByJobOfferId(jobOfferId);
    }

    @GetMapping("/user/{userId}/job-offer/{jobOfferId}")
    public ResponseEntity<ApplicationDTO> findApplicationByUserIdAndJobOfferId(@PathVariable Long userId, @PathVariable Long jobOfferId) {
        ApplicationDTO applicationDTO = applicationService.findApplicationByUserIdAndJobOfferId(userId, jobOfferId);
        if (applicationDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(applicationDTO);
    }


}
