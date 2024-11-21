package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.Activity;
import com.alumniportal.unmsm.persistence.IActivityDAO;
import com.alumniportal.unmsm.repository.IActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ActivityDAOImpl implements IActivityDAO {


    private final IActivityRepository activityRepository;


    @Override
    public List<Activity> findAll() {
        return activityRepository.findAll();
    }

    @Override
    public Activity findById(Long id) {
        return activityRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Activity activity) {
        activityRepository.save(activity);
    }

    @Override
    public void deleteById(Long id) {
        activityRepository.deleteById(id);
    }

    @Override
    public List<Activity> findActivitiesByUserId(Long userId) {
        return activityRepository.findActivitiesByUserId(userId);
    }

    @Override
    public List<Activity> findActivitiesByCompanyId(Long companyId) {
        return activityRepository.findActivitiesByCompanyId(companyId);
    }
}
