package com.alumniportal.unmsm.controller;


import com.alumniportal.unmsm.dto.RequestDTO.CertificationRequestDTO;
import com.alumniportal.unmsm.dto.ResponseDTO.CertificationResponseDTO;
import com.alumniportal.unmsm.model.Certification;
import com.alumniportal.unmsm.service.interfaces.ICertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<CertificationResponseDTO>> findAll() {
        List<CertificationResponseDTO> certificationResponseDTOList = certificationService.findAll();
        return ResponseEntity.ok(certificationResponseDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificationResponseDTO> findById(@PathVariable Long id) {
        CertificationResponseDTO certificationResponseDTO = certificationService.findById(id);
        return ResponseEntity.ok(certificationResponseDTO);
    }

    @PostMapping("/save/{userId}")
    public ResponseEntity<Void> save(@RequestBody CertificationRequestDTO certificationRequestDTO, @PathVariable Long userId) {

        certificationService.saveCertification(certificationRequestDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        certificationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCertification(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        certificationService.updateCertification(id, updates);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CertificationResponseDTO>> findCertificationsByUserId(@PathVariable Long userId) {
        List<CertificationResponseDTO> certificationsByUserId = certificationService.findCertificationsByUserId(userId);
        return ResponseEntity.ok(certificationsByUserId);
    }

}
