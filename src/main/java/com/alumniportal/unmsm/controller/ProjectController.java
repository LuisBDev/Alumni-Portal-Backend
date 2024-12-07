package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.request.ProjectRequestDTO;
import com.alumniportal.unmsm.dto.response.ProjectResponseDTO;
import com.alumniportal.unmsm.model.Project;
import com.alumniportal.unmsm.service.interfaces.IProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Get all projects")
    @ApiResponse(responseCode = "200", description = "List of projects")
    public ResponseEntity<List<ProjectResponseDTO>> findAll() {
        List<ProjectResponseDTO> projectResponseDTOList = projectService.findAll();
        return ResponseEntity.ok(projectResponseDTOList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get project by id")
    @ApiResponse(responseCode = "200", description = "Return project")
    public ResponseEntity<ProjectResponseDTO> findById(@PathVariable Long id) {
        ProjectResponseDTO projectResponseDTO = projectService.findById(id);
        return ResponseEntity.ok(projectResponseDTO);
    }

    @PostMapping("/save/{userId}")
    @Operation(summary = "Save project by user id")
    @ApiResponse(responseCode = "201", description = "Return saved project")
    public ResponseEntity<ProjectResponseDTO> save(@RequestBody ProjectRequestDTO projectRequestDTO, @PathVariable Long userId) {
        ProjectResponseDTO projectResponseDTO = projectService.saveProject(projectRequestDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(projectResponseDTO);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update project by project id")
    @ApiResponse(responseCode = "200", description = "Return updated project")
    public ResponseEntity<ProjectResponseDTO> update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        ProjectResponseDTO projectResponseDTO = projectService.updateProject(id, updates);
        return ResponseEntity.ok(projectResponseDTO);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete project by project id")
    @ApiResponse(responseCode = "204", description = "Project deleted")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        projectService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get projects by user id")
    @ApiResponse(responseCode = "200", description = "List of projects")
    public ResponseEntity<List<ProjectResponseDTO>> findProjectsByUserId(@PathVariable Long userId) {
        List<ProjectResponseDTO> projectsByUserId = projectService.findProjectsByUserId(userId);
        return ResponseEntity.ok(projectsByUserId);
    }


}
