package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.request.WorkExperienceRequestDTO;
import com.alumniportal.unmsm.dto.response.WorkExperienceResponseDTO;
import com.alumniportal.unmsm.model.WorkExperience;
import com.alumniportal.unmsm.service.interfaces.IWorkExperienceService;
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
    public ResponseEntity<List<WorkExperienceResponseDTO>> findAll() {
        List<WorkExperienceResponseDTO> workExperienceResponseDTOList = workExperienceService.findAll();
        return ResponseEntity.ok(workExperienceResponseDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkExperienceResponseDTO> findById(@PathVariable Long id) {
        WorkExperienceResponseDTO workExperienceResponseDTO = workExperienceService.findById(id);
        return ResponseEntity.ok(workExperienceResponseDTO);
    }

    @PostMapping("/save/{userId}")
    public ResponseEntity<WorkExperienceResponseDTO> save(@RequestBody WorkExperienceRequestDTO workExperienceRequestDTO, @PathVariable Long userId) {
        WorkExperienceResponseDTO workExperienceResponseDTO = workExperienceService.saveWorkExperience(workExperienceRequestDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(workExperienceResponseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<WorkExperienceResponseDTO> update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        WorkExperienceResponseDTO workExperienceResponseDTO = workExperienceService.updateWorkExperience(id, updates);
        return ResponseEntity.ok(workExperienceResponseDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        workExperienceService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WorkExperienceResponseDTO>> findWorkExperiencesByUserId(@PathVariable Long userId) {
        List<WorkExperienceResponseDTO> workExperiencesByUserId = workExperienceService.findWorkExperiencesByUserId(userId);
        return ResponseEntity.ok(workExperiencesByUserId);
    }


}
