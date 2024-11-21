package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.WorkExperienceDTO;
import com.alumniportal.unmsm.model.WorkExperience;
import com.alumniportal.unmsm.service.IWorkExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/work-experience")
public class WorkExperienceController {

    @Autowired
    private IWorkExperienceService workExperienceService;


    @GetMapping("/all")
    public List<WorkExperienceDTO> findAll() {
        return workExperienceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        WorkExperienceDTO workExperienceDTO = workExperienceService.findById(id);
        if (workExperienceDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(workExperienceDTO);
    }

    @PostMapping("/save/{userId}")
    public ResponseEntity<?> save(@RequestBody WorkExperience workExperience, @PathVariable Long userId) {

        try {
            workExperienceService.saveWorkExperience(workExperience, userId);
            return ResponseEntity.ok(workExperience);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            workExperienceService.updateWorkExperience(id, updates);
            return ResponseEntity.ok("Work Experience updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        workExperienceService.deleteById(id);
    }

    @GetMapping("/user/{userId}")
    public List<WorkExperienceDTO> findWorkExperiencesByUserId(@PathVariable Long userId) {
        return workExperienceService.findWorkExperiencesByUserId(userId);
    }


}
