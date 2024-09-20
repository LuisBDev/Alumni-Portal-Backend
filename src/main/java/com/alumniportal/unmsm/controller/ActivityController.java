package com.alumniportal.unmsm.controller;


import com.alumniportal.unmsm.model.Activity;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.service.IActivityService;
import com.alumniportal.unmsm.service.IUserService;
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

    @Autowired
    private IUserService userService;

    @GetMapping("/all")
    public List<Activity> findAll() {
        return activityService.findAll();
    }

    @GetMapping("/{id}")
    public Activity findById(@PathVariable Long id) {
        return activityService.findById(id);
    }

    @PostMapping("/save/{userId}")
    public ResponseEntity<?> save(@RequestBody Activity activity, @PathVariable Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body("Error: User not found!");
        }
        activity.setUser(user);
        activityService.save(activity);

        user.getActivityList().add(activity);
        userService.save(user);

        return ResponseEntity.ok("Activity saved successfully!");

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
