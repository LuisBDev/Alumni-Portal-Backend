package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.ResponseDTO.ProjectResponseDTO;
import com.alumniportal.unmsm.model.Project;
import com.alumniportal.unmsm.service.interfaces.IProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class ProjectController {

    private final IProjectService projectService;

    @GetMapping("/all")
    public List<ProjectResponseDTO> findAll() {
        return projectService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        ProjectResponseDTO projectResponseDTO = projectService.findById(id);
        if (projectResponseDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projectResponseDTO);
    }

    @PostMapping("/save/{userId}")
    public ResponseEntity<?> save(@RequestBody Project workExperience, @PathVariable Long userId) {

        try {
            projectService.saveProject(workExperience, userId);
            return ResponseEntity.ok("Project saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            projectService.updateProject(id, updates);
            return ResponseEntity.ok("Project updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        projectService.deleteById(id);
    }

    @GetMapping("/user/{userId}")
    public List<ProjectResponseDTO> findProjectsByUserId(@PathVariable Long userId) {
        return projectService.findProjectsByUserId(userId);
    }


}
