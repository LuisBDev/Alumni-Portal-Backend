package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.request.WorkExperienceRequestDTO;
import com.alumniportal.unmsm.dto.response.WorkExperienceResponseDTO;
import com.alumniportal.unmsm.model.WorkExperience;
import com.alumniportal.unmsm.service.interfaces.IWorkExperienceService;
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
@RequestMapping("/api/work-experience")
public class WorkExperienceController {

    private final IWorkExperienceService workExperienceService;


    @GetMapping("/all")
    @Operation(summary = "Get all work experiences")
    @ApiResponse(responseCode = "200", description = "Return all work experiences")
    public ResponseEntity<List<WorkExperienceResponseDTO>> findAll() {
        List<WorkExperienceResponseDTO> workExperienceResponseDTOList = workExperienceService.findAll();
        return ResponseEntity.ok(workExperienceResponseDTOList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get work experience by id")
    @ApiResponse(responseCode = "200", description = "Return work experience by id")
    public ResponseEntity<WorkExperienceResponseDTO> findById(@PathVariable Long id) {
        WorkExperienceResponseDTO workExperienceResponseDTO = workExperienceService.findById(id);
        return ResponseEntity.ok(workExperienceResponseDTO);
    }

    @PostMapping("/save/{userId}")
    @Operation(summary = "Save work experience by user id")
    @ApiResponse(responseCode = "201", description = "Return work experience saved")
    public ResponseEntity<WorkExperienceResponseDTO> save(@RequestBody WorkExperienceRequestDTO workExperienceRequestDTO, @PathVariable Long userId) {
        WorkExperienceResponseDTO workExperienceResponseDTO = workExperienceService.saveWorkExperience(workExperienceRequestDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(workExperienceResponseDTO);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update work experience by id")
    @ApiResponse(responseCode = "200", description = "Return work experience updated")
    public ResponseEntity<WorkExperienceResponseDTO> update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        WorkExperienceResponseDTO workExperienceResponseDTO = workExperienceService.updateWorkExperience(id, updates);
        return ResponseEntity.ok(workExperienceResponseDTO);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete work experience by id")
    @ApiResponse(responseCode = "204", description = "Work experience deleted")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        workExperienceService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get work experiences by user id")
    @ApiResponse(responseCode = "200", description = "Return work experiences by user id")
    public ResponseEntity<List<WorkExperienceResponseDTO>> findWorkExperiencesByUserId(@PathVariable Long userId) {
        List<WorkExperienceResponseDTO> workExperiencesByUserId = workExperienceService.findWorkExperiencesByUserId(userId);
        return ResponseEntity.ok(workExperiencesByUserId);
    }


}
