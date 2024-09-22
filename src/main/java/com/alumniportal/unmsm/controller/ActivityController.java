package com.alumniportal.unmsm.controller;


import com.alumniportal.unmsm.model.Activity;
import com.alumniportal.unmsm.service.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ActivityController {

    @Autowired
    private IActivityService activityService;

    @GetMapping("/all")
    public List<Activity> findAll() {
        return activityService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Activity activity = activityService.findById(id);
        if (activity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(activity);
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
    public List<Activity> findActivitysByUser_Id(@PathVariable Long userId) {
        return activityService.findActivitiesByUser_Id(userId);
    }


}
