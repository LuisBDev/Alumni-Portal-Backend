package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.interfaces.ICompanyDAO;
import com.alumniportal.unmsm.persistence.interfaces.IUserDAO;
import com.alumniportal.unmsm.util.ImageManagement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageController {


    private final ImageManagement<User> userImageManagement;

    private final ImageManagement<Company> companyImageManagement;

    private final ICompanyDAO companyDAO;

    private final IUserDAO userDAO;


    @PostMapping("/upload-user/{userId}")
    @Operation(summary = "Upload user image by user id")
    @ApiResponse(responseCode = "200", description = "File uploaded successfully")
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
    @Operation(summary = "Upload company image by company id")
    @ApiResponse(responseCode = "200", description = "File uploaded successfully")
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
    @Operation(summary = "Download user image by user id")
    @ApiResponse(responseCode = "200", description = "Image found")
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
    @Operation(summary = "Download company image by company id")
    @ApiResponse(responseCode = "200", description = "Image found")
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
    @Operation(summary = "Delete user image by user id")
    @ApiResponse(responseCode = "200", description = "Image deleted successfully")
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
    @Operation(summary = "Delete company image by company id")
    @ApiResponse(responseCode = "200", description = "Image deleted successfully")
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
