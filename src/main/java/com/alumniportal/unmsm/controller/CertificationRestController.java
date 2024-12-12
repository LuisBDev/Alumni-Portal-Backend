package com.alumniportal.unmsm.controller;


import com.alumniportal.unmsm.dto.request.CertificationRequestDTO;
import com.alumniportal.unmsm.dto.response.CertificationResponseDTO;
import com.alumniportal.unmsm.service.interfaces.ICertificationService;
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
@RequestMapping("/api/certification")
public class CertificationRestController {

    private final ICertificationService certificationService;
    

    @GetMapping("/all")
    @Operation(summary = "Get all certifications")
    @ApiResponse(responseCode = "200", description = "List of certifications")
    public ResponseEntity<List<CertificationResponseDTO>> findAll() {
        List<CertificationResponseDTO> certificationResponseDTOList = certificationService.findAll();
        return ResponseEntity.ok(certificationResponseDTOList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get certification by id")
    @ApiResponse(responseCode = "200", description = "Return certification found")
    public ResponseEntity<CertificationResponseDTO> findById(@PathVariable Long id) {
        CertificationResponseDTO certificationResponseDTO = certificationService.findById(id);
        return ResponseEntity.ok(certificationResponseDTO);
    }

    @PostMapping("/save/{userId}")
    @Operation(summary = "Save certification")
    @ApiResponse(responseCode = "201", description = "Return certification saved")
    public ResponseEntity<CertificationResponseDTO> save(@RequestBody CertificationRequestDTO certificationRequestDTO, @PathVariable Long userId) {

        CertificationResponseDTO certificationResponseDTO = certificationService.saveCertification(certificationRequestDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(certificationResponseDTO);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete certification by id")
    @ApiResponse(responseCode = "204", description = "Certification deleted")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        certificationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update certification by id")
    @ApiResponse(responseCode = "200", description = "Return certification updated")
    public ResponseEntity<CertificationResponseDTO> updateCertification(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        CertificationResponseDTO certificationResponseDTO = certificationService.updateCertification(id, updates);
        return ResponseEntity.ok(certificationResponseDTO);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get certifications by user id")
    @ApiResponse(responseCode = "200", description = "List of certifications")
    public ResponseEntity<List<CertificationResponseDTO>> findCertificationsByUserId(@PathVariable Long userId) {
        List<CertificationResponseDTO> certificationsByUserId = certificationService.findCertificationsByUserId(userId);
        return ResponseEntity.ok(certificationsByUserId);
    }

}
