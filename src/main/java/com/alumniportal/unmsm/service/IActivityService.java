package com.alumniportal.unmsm.service;

import com.alumniportal.unmsm.dto.ActivityDTO;
import com.alumniportal.unmsm.model.Activity;

import java.util.List;

public interface IActivityService {
    List<ActivityDTO> findAll();

    ActivityDTO findById(Long id);

    void save(Activity activity);

    void deleteById(Long id);

    List<ActivityDTO> findActivitiesByUserId(Long userId);

    void saveActivity(Activity activity, Long userId);
}
