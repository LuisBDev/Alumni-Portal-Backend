package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.EducationDTO;
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
    public List<EducationDTO> findAll() {
        return educationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        EducationDTO educationDTO = educationService.findById(id);
        if (educationDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(educationDTO);
    }

    @PostMapping("/save/{userId}")
    public ResponseEntity<?> save(@RequestBody Education education, @PathVariable Long userId) {

        try {
            educationService.saveEducation(education, userId);
            return ResponseEntity.ok("Education saved successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        educationService.deleteById(id);
    }

    @GetMapping("/user/{userId}")
    public List<EducationDTO> findEducationsByUser_Id(@PathVariable Long userId) {
        return educationService.findEducationsByUser_Id(userId);
    }


}
