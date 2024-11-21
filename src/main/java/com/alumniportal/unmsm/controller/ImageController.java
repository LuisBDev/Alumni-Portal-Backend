package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.ICompanyDAO;
import com.alumniportal.unmsm.persistence.IUserDAO;
import com.alumniportal.unmsm.util.ImageManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/image")
public class ImageController {


    @Autowired
    private ImageManagement<User> userImageManagement;

    @Autowired
    private ImageManagement<Company> companyImageManagement;

    @Autowired
    private ICompanyDAO companyDAO;

    @Autowired
    private IUserDAO userDAO;


    @PostMapping("/upload-user/{userId}")
    public ResponseEntity<?> uploadUserImage(@PathVariable Long userId, @RequestParam("image") MultipartFile file) {
        try {
            User user = userDAO.findById(userId);
            String filePath = userImageManagement.uploadOrUpdateImage(user, file);
            return ResponseEntity.ok("File uploaded successfully: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/upload-company/{companyId}")
    public ResponseEntity<?> uploadCompanyImage(@PathVariable Long companyId, @RequestParam("image") MultipartFile file) {
        try {
            Company company = companyDAO.findById(companyId);
            String filePath = companyImageManagement.uploadOrUpdateImage(company, file);
            return ResponseEntity.ok("File uploaded successfully: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/download-user/{userId}")
    public ResponseEntity<?> downloadUserImage(@PathVariable Long userId) throws IOException {

        try {
            User user = userDAO.findById(userId);
            byte[] imageData = userImageManagement.downloadImageFromFileSystem(user);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/png"))
                    .body(imageData);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @GetMapping("/download-company/{companyId}")
    public ResponseEntity<?> downloadCompanyImage(@PathVariable Long companyId) throws IOException {

        try {
            Company company = companyDAO.findById(companyId);
            byte[] imageData = companyImageManagement.downloadImageFromFileSystem(company);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/png"))
                    .body(imageData);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @DeleteMapping("/delete-image-user/{userId}")
    public ResponseEntity<?> deleteUserImage(@PathVariable Long userId) {
        try {
            User user = userDAO.findById(userId);
            userImageManagement.deleteImage(user);
            return ResponseEntity.ok("Image deleted successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-image-company/{companyId}")
    public ResponseEntity<?> deleteCompanyImage(@PathVariable Long companyId) {
        try {
            Company company = companyDAO.findById(companyId);
            companyImageManagement.deleteImage(company);
            return ResponseEntity.ok("Image deleted successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
