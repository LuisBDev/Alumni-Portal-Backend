package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.model.Application;
import com.alumniportal.unmsm.model.JobOffer;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.service.IApplicationService;
import com.alumniportal.unmsm.service.IJobOfferService;
import com.alumniportal.unmsm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/application")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ApplicationController {

    @Autowired
    private IApplicationService applicationService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IJobOfferService jobOfferService;

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
        User user = userService.findById(application.getUser().getId());
        if (user == null) {
            return ResponseEntity.badRequest().body("Error: User not found!");
        }
        JobOffer jobOffer = jobOfferService.findById(application.getJobOffer().getId());
        if (jobOffer == null) {
            return ResponseEntity.badRequest().body("Error: JobOffer not found!");
        }

//        Persistir application
        application.setUser(user);
        application.setJobOffer(jobOffer);
        application.setApplicationDate(LocalDate.now());
        applicationService.save(application);

//        Actualizar user
        user.getApplicationList().add(application);
        userService.save(user);

//        Actualizar application
        jobOffer.getApplicationList().add(application);
        applicationService.save(application);

        return ResponseEntity.ok("Application saved successfully!");

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
