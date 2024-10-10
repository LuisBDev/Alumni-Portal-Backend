package com.alumniportal.unmsm.controller;


import com.alumniportal.unmsm.dto.CertificationDTO;
import com.alumniportal.unmsm.model.Certification;
import com.alumniportal.unmsm.service.ICertificationService;
import com.alumniportal.unmsm.service.IUserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/certification")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CertificationController {

    @Autowired
    private ICertificationService certificationService;

    @Autowired
    private IUserService userService;

    @GetMapping("/all")
    public List<CertificationDTO> findAll() {
        return certificationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        CertificationDTO certificationDTO = certificationService.findById(id);
        if (certificationDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(certificationDTO);
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
    public List<CertificationDTO> findCertificationsByUser_Id(@PathVariable Long userId) {
        return certificationService.findCertificationsByUser_Id(userId);
    }


}
