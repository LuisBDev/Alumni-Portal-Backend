package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.model.Activity;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.IActivityDAO;
import com.alumniportal.unmsm.service.IActivityService;
import com.alumniportal.unmsm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ActivityServiceImpl implements IActivityService {

    @Autowired
    private IActivityDAO activityDAO;

    @Autowired
    private IUserService userService;

    @Override
    public List<Activity> findAll() {
        return activityDAO.findAll();
    }

    @Override
    public Activity findById(Long id) {
        return activityDAO.findById(id);
    }

    @Override
    public void save(Activity activity) {
        activityDAO.save(activity);
    }

    @Override
    public void deleteById(Long id) {
        activityDAO.deleteById(id);
    }

    @Override
    public List<Activity> findActivitiesByUser_Id(Long userId) {
        return activityDAO.findActivitiesByUser_Id(userId);
    }

    public void saveActivity(Activity activity, Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
//        Setteando el usuario y fecha de creacion en la actividad
        activity.setUser(user);
        activity.setCreatedAt(LocalDate.now());
        activityDAO.save(activity);

//        Agregando la actividad a la lista de actividades del usuario
        user.getActivityList().add(activity);
        userService.save(user);

    }

}
