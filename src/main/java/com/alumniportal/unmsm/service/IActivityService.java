package com.alumniportal.unmsm.service;

import com.alumniportal.unmsm.model.Activity;

import java.util.List;

public interface IActivityService {
    List<Activity> findAll();

    Activity findById(Long id);

    void save(Activity activity);

    void deleteById(Long id);

    List<Activity> findActivitiesByUser_Id(Long userId);

    void saveActivity(Activity activity, Long userId);
}
