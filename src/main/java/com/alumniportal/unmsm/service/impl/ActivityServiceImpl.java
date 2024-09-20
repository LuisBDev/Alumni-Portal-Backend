package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.model.Activity;
import com.alumniportal.unmsm.persistence.IActivityDAO;
import com.alumniportal.unmsm.service.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements IActivityService {

    @Autowired
    private IActivityDAO activityDAO;

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
}
