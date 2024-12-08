package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.request.EducationRequestDTO;
import com.alumniportal.unmsm.dto.response.EducationResponseDTO;
import com.alumniportal.unmsm.service.interfaces.IEducationService;
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
@RequestMapping("/api/education")
public class EducationRestController {

    private final IEducationService educationService;


    @GetMapping("/all")
    @Operation(summary = "Get all educations")
    @ApiResponse(responseCode = "200", description = "List of educations")
    public ResponseEntity<List<EducationResponseDTO>> findAll() {
        List<EducationResponseDTO> educationResponseDTOList = educationService.findAll();
        return ResponseEntity.ok(educationResponseDTOList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get education by id")
    @ApiResponse(responseCode = "200", description = "Return education found")
    public ResponseEntity<EducationResponseDTO> findById(@PathVariable Long id) {
        EducationResponseDTO educationResponseDTO = educationService.findById(id);
        return ResponseEntity.ok(educationResponseDTO);
    }

    @PostMapping("/save/{userId}")
    @Operation(summary = "Save education")
    @ApiResponse(responseCode = "201", description = "Return saved education")
    public ResponseEntity<EducationResponseDTO> save(@RequestBody EducationRequestDTO educationRequestDTO, @PathVariable Long userId) {
        EducationResponseDTO educationResponseDTO = educationService.saveEducation(educationRequestDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(educationResponseDTO);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update education")
    @ApiResponse(responseCode = "200", description = "Return updated education")
    public ResponseEntity<EducationResponseDTO> update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        EducationResponseDTO educationResponseDTO = educationService.updateEducation(id, updates);
        return ResponseEntity.ok(educationResponseDTO);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete education by id")
    @ApiResponse(responseCode = "204", description = "Education deleted")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        educationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get educations by user id")
    @ApiResponse(responseCode = "200", description = "List of educations")
    public ResponseEntity<List<EducationResponseDTO>> findEducationsByUserId(@PathVariable Long userId) {
        List<EducationResponseDTO> educationsByUserId = educationService.findEducationsByUserId(userId);
        return ResponseEntity.ok(educationsByUserId);
    }

}
