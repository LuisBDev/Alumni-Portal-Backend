package com.alumniportal.unmsm.persistence;

import com.alumniportal.unmsm.model.Activity;

import java.util.List;

public interface IActivityDAO {
    List<Activity> findAll();

    Activity findById(Long id);

    void save(Activity activity);

    void deleteById(Long id);

    List<Activity> findActivitiesByUserId(Long userId);

}
