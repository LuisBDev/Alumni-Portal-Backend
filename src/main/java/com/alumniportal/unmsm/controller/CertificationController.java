package com.alumniportal.unmsm.controller;


import com.alumniportal.unmsm.model.Certification;
import com.alumniportal.unmsm.model.User;
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
    public Certification findById(@PathVariable Long id) {
        return certificationService.findById(id);
    }

    @PostMapping("/save/{userId}")
    public ResponseEntity<?> save(@RequestBody Certification certification, @PathVariable Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body("Error: User not found!");
        }
        certification.setUser(user);
        certificationService.save(certification);

        user.getCertificationList().add(certification);
        userService.save(user);

        return ResponseEntity.ok("Certification saved successfully!");

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
