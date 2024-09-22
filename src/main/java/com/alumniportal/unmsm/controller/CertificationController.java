package com.alumniportal.unmsm.controller;


import com.alumniportal.unmsm.model.Certification;
import com.alumniportal.unmsm.service.ICertificationService;
import com.alumniportal.unmsm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certification")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CertificationController {

    @Autowired
    private ICertificationService certificationService;

    @Autowired
    private IUserService userService;

    @GetMapping("/all")
    public List<Certification> findAll() {
        return certificationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Certification certification = certificationService.findById(id);
        if (certification == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(certification);
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

    @GetMapping("/user/{userId}")
    public List<Certification> findCertificationsByUser_Id(@PathVariable Long userId) {
        return certificationService.findCertificationsByUser_Id(userId);
    }


}
