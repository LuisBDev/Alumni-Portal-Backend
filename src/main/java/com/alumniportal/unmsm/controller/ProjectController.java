package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.ProjectDTO;
import com.alumniportal.unmsm.model.Project;
import com.alumniportal.unmsm.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/project")
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @GetMapping("/all")
    public List<ProjectDTO> findAll() {
        return projectService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        ProjectDTO projectDTO = projectService.findById(id);
        if (projectDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projectDTO);
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
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Map<String, Object> updates)
    {
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
    public List<ProjectDTO> findProjectsByUser_Id(@PathVariable Long userId) {
        return projectService.findProjectsByUser_Id(userId);
    }


}
