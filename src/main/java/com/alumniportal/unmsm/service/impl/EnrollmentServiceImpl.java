package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.model.Activity;
import com.alumniportal.unmsm.model.Enrollment;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.IEnrollmentDAO;
import com.alumniportal.unmsm.service.IActivityService;
import com.alumniportal.unmsm.service.IEnrollmentService;
import com.alumniportal.unmsm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements IEnrollmentService {

    @Autowired
    private IEnrollmentDAO enrollmentDAO;

    @Autowired
    private IUserService userService;

    @Autowired
    private IActivityService activityService;

    @Override
    public List<Enrollment> findAll() {
        return enrollmentDAO.findAll();
    }

    @Override
    public Enrollment findById(Long id) {
        return enrollmentDAO.findById(id);
    }

    @Override
    public void save(Enrollment enrollment) {
        enrollmentDAO.save(enrollment);
    }

    @Override
    public void deleteById(Long id) {
        enrollmentDAO.deleteById(id);
    }

    @Override
    public List<Enrollment> findEnrollmentsByUser_Id(Long userId) {
        return enrollmentDAO.findEnrollmentsByUser_Id(userId);
    }

    @Override
    public List<Enrollment> findEnrollmentsByActivity_Id(Long activityId) {
        return enrollmentDAO.findEnrollmentsByActivity_Id(activityId);
    }

    public void saveEnrollment(Enrollment enrollment) {
        User user = userService.findById(enrollment.getUser().getId());
        if (user == null) {
            throw new IllegalArgumentException("Error: User not found!");
        }
        Activity activity = activityService.findById(enrollment.getActivity().getId());
        if (activity == null) {
            throw new IllegalArgumentException("Error: Activity not found!");
        }

//        Persistir enrollment
        enrollment.setUser(user);
        enrollment.setActivity(activity);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setStatus("ACTIVE");
        enrollmentDAO.save(enrollment);

//        Actualizar user
        user.getEnrollmentList().add(enrollment);
        userService.save(user);

//        Actualizar activity
        activity.getEnrollmentList().add(enrollment);
        activityService.save(activity);

    }


}
