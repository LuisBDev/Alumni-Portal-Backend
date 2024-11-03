package com.alumniportal.unmsm.controller;


import com.alumniportal.unmsm.dto.ActivityDTO;
import com.alumniportal.unmsm.model.Activity;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.IActivityDAO;
import com.alumniportal.unmsm.service.IActivityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/activity")
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class ActivityController {

    private final IActivityService activityService;

    @Autowired
    private IActivityDAO activityDAO;

    // Inyección por constructor
    @Autowired
    public ActivityController(IActivityService activityService) {
        this.activityService = activityService;
    }


    @GetMapping("/all")
    public List<ActivityDTO> findAll() {
        return activityService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        ActivityDTO activityDTO = activityService.findById(id);
        if (activityDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(activityDTO);
    }

    @PostMapping("/save-user/{userId}")
    public ResponseEntity<?> saveActivityByUserId(
            @RequestPart(value = "activity") Activity activity,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @PathVariable Long userId) {
        try {
            activityService.saveActivityWithImageByUserId(activity, userId, image);
            return ResponseEntity.ok("Actividad creada por el usuario correctamente: " + userId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar la actividad");
        }
    }


    @PostMapping("/save-company/{companyId}")
    public ResponseEntity<?> saveActivityByCompanyId(
            @RequestPart(value = "activity") Activity activity,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @PathVariable Long companyId) {
        try {
            activityService.saveActivityWithImageByCompanyId(activity, companyId, image);
            return ResponseEntity.ok()
                    .body(Map.of(
                            "message", "Actividad creada exitosamente",
                            "companyId", companyId,
                            "activityTitle", activity.getTitle()
                    ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al guardar la actividad: " + e.getMessage()));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws Exception {
        try {
            activityService.deleteById(id);
            return ResponseEntity.ok("Activity deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public List<ActivityDTO> findActivitiesByUserId(@PathVariable Long userId) {
        return activityService.findActivitiesByUserId(userId);
    }

    @GetMapping("/company/{companyId}")
    public List<ActivityDTO> findActivitiesByCompanyId(@PathVariable Long companyId) {
        return activityService.findActivitiesByCompanyId(companyId);
    }


    @PostMapping("/activity-image/{activityId}")
    public ResponseEntity<?> uploadActivityImage(@PathVariable Long activityId, @RequestParam("image") MultipartFile file) throws IOException {

        try {
            activityService.uploadActivityImage(activityId, file);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/activity-image/{activityId}")
    public ResponseEntity<byte[]> downloadActivityImage(@PathVariable Long activityId) throws Exception {


        byte[] fileData = activityService.downloadActivityImage(activityId);
        String contentType = activityService.getFileContentType(activityId); // Método para obtener el tipo de contenido
        String fileName = activityService.getFileName(activityId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDispositionFormData("attachment", fileName); // Para descargar el archivo

        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }

    @DeleteMapping("/activity-image/{activityId}")
    public ResponseEntity<?> deleteActivityImage(@PathVariable Long activityId) {
        try {
            activityService.deleteActivityImage(activityId);
            return ResponseEntity.ok("File deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
