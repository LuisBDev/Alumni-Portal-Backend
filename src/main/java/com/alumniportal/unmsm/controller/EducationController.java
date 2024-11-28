package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.EducationDTO;
import com.alumniportal.unmsm.model.Education;
import com.alumniportal.unmsm.service.interfaces.IEducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/education")
public class EducationController {

    private final IEducationService educationService;


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

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            educationService.updateEducation(id, updates);
            return ResponseEntity.ok("Education updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        educationService.deleteById(id);
    }

    @GetMapping("/user/{userId}")
    public List<EducationDTO> findEducationsByUserId(@PathVariable Long userId) {
        return educationService.findEducationsByUserId(userId);
    }


}
