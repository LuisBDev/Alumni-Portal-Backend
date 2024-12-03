package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.ResponseDTO.WorkExperienceResponseDTO;
import com.alumniportal.unmsm.model.WorkExperience;
import com.alumniportal.unmsm.service.interfaces.IWorkExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/work-experience")
public class WorkExperienceController {

    private final IWorkExperienceService workExperienceService;


    @GetMapping("/all")
    public List<WorkExperienceResponseDTO> findAll() {
        return workExperienceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        WorkExperienceResponseDTO workExperienceResponseDTO = workExperienceService.findById(id);
        if (workExperienceResponseDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(workExperienceResponseDTO);
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
    public List<WorkExperienceResponseDTO> findWorkExperiencesByUserId(@PathVariable Long userId) {
        return workExperienceService.findWorkExperiencesByUserId(userId);
    }


}
