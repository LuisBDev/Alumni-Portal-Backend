package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.ResponseDTO.ApplicationResponseDTO;
import com.alumniportal.unmsm.model.Application;
import com.alumniportal.unmsm.service.interfaces.IApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/application")
public class ApplicationController {


    private final IApplicationService applicationService;

    @GetMapping("/all")
    public List<ApplicationResponseDTO> findAll() {
        return applicationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        ApplicationResponseDTO applicationResponseDTO = applicationService.findById(id);
        if (applicationResponseDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(applicationResponseDTO);
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
    public List<ApplicationResponseDTO> findApplicationsByUserId(@PathVariable Long userId) {
        return applicationService.findApplicationsByUserId(userId);
    }

    @GetMapping("/job-offer/{jobOfferId}")
    public List<ApplicationResponseDTO> findApplicationsByJobOfferId(@PathVariable Long jobOfferId) {
        return applicationService.findApplicationsByJobOfferId(jobOfferId);
    }

    @GetMapping("/user/{userId}/job-offer/{jobOfferId}")
    public ResponseEntity<ApplicationResponseDTO> findApplicationByUserIdAndJobOfferId(@PathVariable Long userId, @PathVariable Long jobOfferId) {
        ApplicationResponseDTO applicationResponseDTO = applicationService.findApplicationByUserIdAndJobOfferId(userId, jobOfferId);
        if (applicationResponseDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(applicationResponseDTO);
    }


}
