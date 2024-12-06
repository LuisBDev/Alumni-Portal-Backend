package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.request.ProjectRequestDTO;
import com.alumniportal.unmsm.dto.response.ProjectResponseDTO;
import com.alumniportal.unmsm.model.Project;
import com.alumniportal.unmsm.service.interfaces.IProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<ProjectResponseDTO>> findAll() {
        List<ProjectResponseDTO> projectResponseDTOList = projectService.findAll();
        return ResponseEntity.ok(projectResponseDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> findById(@PathVariable Long id) {
        ProjectResponseDTO projectResponseDTO = projectService.findById(id);
        return ResponseEntity.ok(projectResponseDTO);
    }

    @PostMapping("/save/{userId}")
    public ResponseEntity<Void> save(@RequestBody ProjectRequestDTO projectRequestDTO, @PathVariable Long userId) {
        projectService.saveProject(projectRequestDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        projectService.updateProject(id, updates);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        projectService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectResponseDTO>> findProjectsByUserId(@PathVariable Long userId) {
        List<ProjectResponseDTO> projectsByUserId = projectService.findProjectsByUserId(userId);
        return ResponseEntity.ok(projectsByUserId);
    }


}
