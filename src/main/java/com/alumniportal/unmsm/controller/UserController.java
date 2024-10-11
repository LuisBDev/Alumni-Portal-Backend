package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.LoginRequestDTO;
import com.alumniportal.unmsm.dto.UserCVDTO;
import com.alumniportal.unmsm.dto.UserDTO;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.service.IUserService;
import com.alumniportal.unmsm.util.CVGenerator;
import com.alumniportal.unmsm.util.ImageManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class UserController {

    @Autowired
    private IUserService userService;



    @GetMapping("/all")
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> findById(@PathVariable Long id) {
        UserDTO userDTO = userService.findById(id);
        if (userDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        UserDTO userDTO = userService.findById(id);
        if (userDTO == null) {
            return ResponseEntity.status(404).body("Error: User not found!");
        }
        userService.deleteById(id);
        return ResponseEntity.ok("User deleted successfully!");
    }

    @PostMapping("/loginAcademic")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        UserDTO userDTO = userService.validateLogin(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        if (userDTO == null) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
        return ResponseEntity.ok(userDTO);

    }

    @PostMapping("/registerAcademic")
    public ResponseEntity<?> save(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            userService.updateUser(id, updates);
            return ResponseEntity.ok("User updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/cv/download/{userId}")
    public ResponseEntity<?> downloadUserCV(@PathVariable Long userId) {
        try {
            UserCVDTO userCV = userService.getUserCV(userId);

            // Call the CV generation method as a static method
            File pdfFile = CVGenerator.generateCV(userCV);

            Path downloadsPath = Paths.get(System.getProperty("user.home"), "Downloads", pdfFile.getName());
            Files.move(pdfFile.toPath(), downloadsPath, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok("CV successfully downloaded to: " + downloadsPath.toString());

        } catch (RuntimeException | IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
