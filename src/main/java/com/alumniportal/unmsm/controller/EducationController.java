package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.model.Education;
import com.alumniportal.unmsm.service.IEducationService;
import com.alumniportal.unmsm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/education")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

public class EducationController {

    @Autowired
    private IEducationService educationService;

    @Autowired
    private IUserService userService;

    @GetMapping("/all")
    public List<Education> findAll() {
        return educationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Education education = educationService.findById(id);
        if (education == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(education);
    }

    @PostMapping("/save/{userId}")
    public ResponseEntity<?> save(@RequestBody Education education, @PathVariable Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body("Error: User not found!");
        }
        education.setUser(user);
        educationService.save(education);

        user.getEducationList().add(education);
        userService.save(user);

        return ResponseEntity.ok("Education saved successfully!");

    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        educationService.deleteById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Education> findEducationsByUser_Id(@PathVariable Long userId) {
        return educationService.findEducationsByUser_Id(userId);
    }


}
