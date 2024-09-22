package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.model.Project;
import com.alumniportal.unmsm.service.IUserService;
import com.alumniportal.unmsm.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IUserService userService;

    @GetMapping("/all")
    public List<Project> findAll() {
        return projectService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Project project = projectService.findById(id);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(project);
    }

    @PostMapping("/save/{userId}")
    public ResponseEntity<?> save(@RequestBody Project workExperience, @PathVariable Long userId) {

        try {
            projectService.saveProject(workExperience, userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        projectService.deleteById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Project> findProjectsByUser_Id(@PathVariable Long userId) {
        return projectService.findProjectsByUser_Id(userId);
    }


}
