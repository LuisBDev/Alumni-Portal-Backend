package com.alumniportal.unmsm.controller;


import com.alumniportal.unmsm.dto.ActivityDTO;
import com.alumniportal.unmsm.model.Activity;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.IActivityDAO;
import com.alumniportal.unmsm.service.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @PostMapping("/save/{userId}")
    public ResponseEntity<?> save(@RequestBody Activity activity, @PathVariable Long userId) {

        try {
            activityService.saveActivity(activity, userId);
            return ResponseEntity.ok(activity);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar la actividad");
        }
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        activityService.deleteById(id);
    }

    @GetMapping("/user/{userId}")
    public List<ActivityDTO> findActivitiesByUserId(@PathVariable Long userId) {
        return activityService.findActivitiesByUserId(userId);
    }

    @PostMapping("/activity-image/{activityId}")
    public ResponseEntity<?> uploadActivityImage(@PathVariable Long activityId, @RequestParam("image") MultipartFile file) throws IOException {

        try {
            return ResponseEntity.ok(activityService.uploadActivityImage(activityId, file));
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
