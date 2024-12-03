package com.alumniportal.unmsm.controller;


import com.alumniportal.unmsm.dto.ResponseDTO.CertificationResponseDTO;
import com.alumniportal.unmsm.model.Certification;
import com.alumniportal.unmsm.service.interfaces.ICertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/certification")
public class CertificationController {

    private final ICertificationService certificationService;


    @GetMapping("/all")
    public List<CertificationResponseDTO> findAll() {
        return certificationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        CertificationResponseDTO certificationResponseDTO = certificationService.findById(id);
        if (certificationResponseDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(certificationResponseDTO);
    }

    @PostMapping("/save/{userId}")
    public ResponseEntity<?> save(@RequestBody Certification certification, @PathVariable Long userId) {
        try {
            certificationService.saveCertification(certification, userId);
            return ResponseEntity.ok(certification);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        certificationService.deleteById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCertification(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            certificationService.updateCertification(id, updates);
            return ResponseEntity.ok("Certification updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public List<CertificationResponseDTO> findCertificationsByUserId(@PathVariable Long userId) {
        return certificationService.findCertificationsByUserId(userId);
    }


}
