package com.alumniportal.unmsm.controller;


import com.alumniportal.unmsm.dto.response.ActivityResponseDTO;
import com.alumniportal.unmsm.dto.request.ActivityRequestDTO;
import com.alumniportal.unmsm.service.interfaces.IActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Get all activities")
    @ApiResponse(responseCode = "200", description = "List of activities")
    public ResponseEntity<List<ActivityResponseDTO>> findAll() {
        List<ActivityResponseDTO> activityResponseDTOList = activityService.findAll();
        return ResponseEntity.ok(activityResponseDTOList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find activity by ID")
    @ApiResponse(responseCode = "200", description = "Return activity")
    public ResponseEntity<ActivityResponseDTO> findById(@PathVariable Long id) {
        ActivityResponseDTO activityResponseDTO = activityService.findById(id);
        return ResponseEntity.ok(activityResponseDTO);
    }

    @PostMapping("/save-user/{userId}")
    @Operation(summary = "Save activity by associating user ID")
    @ApiResponse(responseCode = "201", description = "Return activity saved by user")
    public ResponseEntity<ActivityResponseDTO> saveActivityByUserId(@RequestBody ActivityRequestDTO activityRequestDTO, @PathVariable Long userId) {
        ActivityResponseDTO activityResponseDTO = activityService.saveActivityByUserId(activityRequestDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(activityResponseDTO);

    }

    @PostMapping("/save-company/{companyId}")
    @Operation(summary = "Save activity by associating company ID")
    @ApiResponse(responseCode = "201", description = "Return activity saved by company")
    public ResponseEntity<ActivityResponseDTO> saveActivityByCompanyId(@RequestBody ActivityRequestDTO activityRequestDTO, @PathVariable Long companyId) {
        ActivityResponseDTO activityResponseDTO = activityService.saveActivityByCompanyId(activityRequestDTO, companyId);
        return ResponseEntity.status(HttpStatus.CREATED).body(activityResponseDTO);
    }

    @PatchMapping("/update-activity/{id}")
    @Operation(summary = "Update activity by ID")
    @ApiResponse(responseCode = "200", description = "Return activity updated")
    public ResponseEntity<ActivityResponseDTO> updateActivity(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        ActivityResponseDTO activityResponseDTO = activityService.updateActivity(id, fields);
        return ResponseEntity.ok(activityResponseDTO);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete activity by ID")
    @ApiResponse(responseCode = "204", description = "Activity deleted")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) throws Exception {
        activityService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "List activities published by user ID")
    @ApiResponse(responseCode = "200", description = "List of activities")
    public ResponseEntity<List<ActivityResponseDTO>> findActivitiesByUserId(@PathVariable Long userId) {
        List<ActivityResponseDTO> activities = activityService.findActivitiesByUserId(userId);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "List activities published by company ID")
    @ApiResponse(responseCode = "200", description = "List of activities")
    public ResponseEntity<List<ActivityResponseDTO>> findActivitiesByCompanyId(@PathVariable Long companyId) {
        List<ActivityResponseDTO> activitiesByCompanyId = activityService.findActivitiesByCompanyId(companyId);
        return ResponseEntity.ok(activitiesByCompanyId);
    }


    @PostMapping("/activity-image/{activityId}")
    @Operation(summary = "Upload activity image")
    @ApiResponse(responseCode = "200", description = "Image uploaded")
    public ResponseEntity<?> uploadActivityImage(@PathVariable Long activityId, @RequestParam("image") MultipartFile file) throws IOException {
        activityService.uploadActivityImage(activityId, file);
        return ResponseEntity.ok("File uploaded successfully");
    }

    @GetMapping("/activity-image/{activityId}")
    @Operation(summary = "Download activity image")
    @ApiResponse(responseCode = "200", description = "Image downloaded")
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
    @Operation(summary = "Delete activity image")
    @ApiResponse(responseCode = "200", description = "Image deleted")
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
