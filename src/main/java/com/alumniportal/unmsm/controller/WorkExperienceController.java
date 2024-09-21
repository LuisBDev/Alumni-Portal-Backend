package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.model.WorkExperience;
import com.alumniportal.unmsm.service.IUserService;
import com.alumniportal.unmsm.service.IWorkExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-experience")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

public class WorkExperienceController {

    @Autowired
    private IWorkExperienceService workExperienceService;

    @Autowired
    private IUserService userService;

    @GetMapping("/all")
    public List<WorkExperience> findAll() {
        return workExperienceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        WorkExperience workExperience = workExperienceService.findById(id);
        if (workExperience == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(workExperience);
    }

    @PostMapping("/save/{userId}")
    public ResponseEntity<?> save(@RequestBody WorkExperience workExperience, @PathVariable Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body("Error: User not found!");
        }
        workExperience.setUser(user);
        workExperienceService.save(workExperience);

        user.getWorkExperienceList().add(workExperience);
        userService.save(user);

        return ResponseEntity.ok("WorkExperience saved successfully!");

    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        workExperienceService.deleteById(id);
    }

    @GetMapping("/user/{userId}")
    public List<WorkExperience> findWorkExperiencesByUser_Id(@PathVariable Long userId) {
        return workExperienceService.findWorkExperiencesByUser_Id(userId);
    }


}
