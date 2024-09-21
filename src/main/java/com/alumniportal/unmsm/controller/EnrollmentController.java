package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.model.Activity;
import com.alumniportal.unmsm.model.Enrollment;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.service.IActivityService;
import com.alumniportal.unmsm.service.IEnrollmentService;
import com.alumniportal.unmsm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/enrollment")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class EnrollmentController {

    @Autowired
    private IEnrollmentService enrollmentService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IActivityService activityService;

    @GetMapping("/all")
    public List<Enrollment> findAll() {
        return enrollmentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Enrollment enrollment = enrollmentService.findById(id);
        if (enrollment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(enrollment);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Enrollment enrollment) {
        User user = userService.findById(enrollment.getUser().getId());
        if (user == null) {
            return ResponseEntity.badRequest().body("Error: User not found!");
        }
        Activity activity = activityService.findById(enrollment.getActivity().getId());
        if (activity == null) {
            return ResponseEntity.badRequest().body("Error: Activity not found!");
        }

//        Persistir enrollment
        enrollment.setUser(user);
        enrollment.setActivity(activity);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setStatus("ACTIVE");
        enrollmentService.save(enrollment);

//        Actualizar user
        user.getEnrollmentList().add(enrollment);
        System.out.println(user.getEnrollmentList());
        userService.save(user);

//        Actualizar activity
        activity.getEnrollmentList().add(enrollment);
        System.out.println(activity.getEnrollmentList());
        activityService.save(activity);

        return ResponseEntity.ok("Enrollment saved successfully!");

    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        enrollmentService.deleteById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Enrollment> findEnrollmentsByUser_Id(@PathVariable Long userId) {
        return enrollmentService.findEnrollmentsByUser_Id(userId);
    }

    @GetMapping("/activity/{activityId}")
    public List<Enrollment> findEnrollmentsByActivity_Id(@PathVariable Long activityId) {
        return enrollmentService.findEnrollmentsByActivity_Id(activityId);
    }


}
