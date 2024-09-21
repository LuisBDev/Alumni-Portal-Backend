package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.model.Application;
import com.alumniportal.unmsm.service.IApplicationService;
import com.alumniportal.unmsm.service.IJobOfferService;
import com.alumniportal.unmsm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/application")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ApplicationController {

    @Autowired
    private IApplicationService applicationService;


    @GetMapping("/all")
    public List<Application> findAll() {
        return applicationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Application application = applicationService.findById(id);
        if (application == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(application);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Application application) {
        try {
            // Delegamos la lógica de guardado en el servicio
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
    public List<Application> findApplicationsByUser_Id(@PathVariable Long userId) {
        return applicationService.findApplicationsByUser_Id(userId);
    }

    @GetMapping("/job-offer/{jobOfferId}")
    public List<Application> findApplicationsByJobOffer_Id(@PathVariable Long jobOfferId) {
        return applicationService.findApplicationsByJobOffer_Id(jobOfferId);
    }


}
