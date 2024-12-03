package com.alumniportal.unmsm.controller;


import com.alumniportal.unmsm.dto.ResponseDTO.ActivityResponseDTO;
import com.alumniportal.unmsm.dto.RequestDTO.ActivityRequestDTO;
import com.alumniportal.unmsm.service.interfaces.IActivityService;
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
public class ActivityController {

    private final IActivityService activityService;

    // Inyección por constructor
    public ActivityController(IActivityService activityService) {
        this.activityService = activityService;
    }


    @GetMapping("/all")
    public List<ActivityResponseDTO> findAll() {
        return activityService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponseDTO> findById(@PathVariable Long id) {
        ActivityResponseDTO activityResponseDTO = activityService.findById(id);
        return ResponseEntity.ok(activityResponseDTO);
    }

    @PostMapping("/save-user/{userId}")
    public ResponseEntity<?> saveActivityByUserId(@RequestBody ActivityRequestDTO activityRequestDTO, @PathVariable Long userId) {

        activityService.saveActivityByUserId(activityRequestDTO, userId);
        return ResponseEntity.ok("Actividad creada por el usuario correctamente: " + userId);
    }

    @PostMapping("/save-company/{companyId}")
    public ResponseEntity<?> saveActivityByCompanyId(@RequestBody ActivityRequestDTO activityRequestDTO, @PathVariable Long companyId) {
        activityService.saveActivityByCompanyId(activityRequestDTO, companyId);
        return ResponseEntity.ok("Actividad creada por la empresa correctamente: " + companyId);
    }

    @PatchMapping("/update-activity/{id}")
    public ResponseEntity<?> updateActivity(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        activityService.updateActivity(id, fields);
        return ResponseEntity.ok("Activity id: " + id + " updated successfully");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws Exception {
        activityService.deleteById(id);
        return ResponseEntity.ok("Activity id: " + id + " deleted successfully");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ActivityResponseDTO>> findActivitiesByUserId(@PathVariable Long userId) {
        List<ActivityResponseDTO> activities = activityService.findActivitiesByUserId(userId);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/company/{companyId}")
    public List<ActivityResponseDTO> findActivitiesByCompanyId(@PathVariable Long companyId) {
        return activityService.findActivitiesByCompanyId(companyId);
    }


    @PostMapping("/activity-image/{activityId}")
    public ResponseEntity<?> uploadActivityImage(@PathVariable Long activityId, @RequestParam("image") MultipartFile file) throws IOException {
        activityService.uploadActivityImage(activityId, file);
        return ResponseEntity.ok("File uploaded successfully");
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
    public ResponseEntity<?> deleteActivityImage(@PathVariable Long activityId) throws Exception {
        activityService.deleteActivityImage(activityId);
        return ResponseEntity.ok("File deleted successfully");
    }

    //Subir actividad con MultiPartFile

//    @PostMapping("/save-user/{userId}")
//    public ResponseEntity<?> saveActivityByUserId(
//            @RequestPart(value = "activity") Activity activity,
//            @RequestPart(value = "image", required = false) MultipartFile image,
//            @PathVariable Long userId) {
//        try {
//            activityService.saveActivityWithImageByUserId(activity, userId, image);
//            return ResponseEntity.ok("Actividad creada por el usuario correctamente: " + userId);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error al guardar la actividad");
//        }
//    }
//
//    @PostMapping("/save-company/{companyId}")
//    public ResponseEntity<?> saveActivityByCompanyId(
//            @RequestPart(value = "activity") Activity activity,
//            @RequestPart(value = "image", required = false) MultipartFile image,
//            @PathVariable Long companyId) {
//        try {
//            activityService.saveActivityWithImageByCompanyId(activity, companyId, image);
//            return ResponseEntity.ok()
//                    .body(Map.of(
//                            "message", "Actividad creada exitosamente",
//                            "companyId", companyId,
//                            "activityTitle", activity.getTitle()
//                    ));
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest()
//                    .body(Map.of("error", e.getMessage()));
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError()
//                    .body(Map.of("error", "Error al guardar la actividad: " + e.getMessage()));
//        }
//    }


}
