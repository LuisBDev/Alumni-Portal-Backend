package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.request.EducationRequestDTO;
import com.alumniportal.unmsm.dto.response.EducationResponseDTO;
import com.alumniportal.unmsm.model.Education;
import com.alumniportal.unmsm.service.interfaces.IEducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<EducationResponseDTO>> findAll() {
        List<EducationResponseDTO> educationResponseDTOList = educationService.findAll();
        return ResponseEntity.ok(educationResponseDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EducationResponseDTO> findById(@PathVariable Long id) {
        EducationResponseDTO educationResponseDTO = educationService.findById(id);
        return ResponseEntity.ok(educationResponseDTO);
    }

    @PostMapping("/save/{userId}")
    public ResponseEntity<Void> save(@RequestBody EducationRequestDTO educationRequestDTO, @PathVariable Long userId) {
        educationService.saveEducation(educationRequestDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        educationService.updateEducation(id, updates);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        educationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EducationResponseDTO>> findEducationsByUserId(@PathVariable Long userId) {
        List<EducationResponseDTO> educationsByUserId = educationService.findEducationsByUserId(userId);
        return ResponseEntity.ok(educationsByUserId);
    }
    
}
