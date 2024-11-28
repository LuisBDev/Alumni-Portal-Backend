package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.Activity;
import com.alumniportal.unmsm.repository.IActivityRepository;
import com.alumniportal.unmsm.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityDAOImplTest {

    @Mock
    private IActivityRepository activityRepository;

    @InjectMocks
    private ActivityDAOImpl activityDAO;

    @Test
    void findAll_ReturnsListOfActivities_WhenActivitiesExist() {

        List<Activity> activities = List.of(new Activity(), new Activity());
        when(activityRepository.findAll()).thenReturn(activities);

        List<Activity> result = activityDAO.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(activityRepository, times(1)).findAll();
    }

    @Test
    void findAll_ReturnsEmptyList_WhenNoActivitiesExist() {
        when(activityRepository.findAll()).thenReturn(List.of());

        List<Activity> result = activityDAO.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(activityRepository, times(1)).findAll();
    }

    @Test
    void findById_ReturnsActivity_WhenActivityExists() {
        Activity activity = new Activity();
        activity.setId(1L);

        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));

        Activity result = activityDAO.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(activityRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ReturnsNull_WhenActivityDoesNotExist() {
        when(activityRepository.findById(1L)).thenReturn(Optional.empty());

        Activity result = activityDAO.findById(1L);

        assertNull(result);
        verify(activityRepository, times(1)).findById(1L);
    }

    @Test
    void save_SavesActivitySuccessfully_WhenValidActivityProvided() {
        Activity activity = new Activity();
        activity.setId(1L);
        activity.setTitle("Test Activity");

        activityDAO.save(activity);

        verify(activityRepository, times(1)).save(activity);
    }

    @Test
    void deleteById_DeletesActivitySuccessfully_WhenActivityExists() {
        doNothing().when(activityRepository).deleteById(1L);

        activityDAO.deleteById(1L);

        verify(activityRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_ThrowsException_WhenActivityDoesNotExist() {
        doThrow(new IllegalArgumentException()).when(activityRepository).deleteById(anyLong());

        assertThrows(IllegalArgumentException.class, () -> activityDAO.deleteById(500L));
        verify(activityRepository, times(1)).deleteById(500L);
    }

    @Test
    void findActivitiesByUserId_ReturnsListOfActivities_WhenActivitiesExistForUser() {
        List<Activity> activities = List.of(new Activity(), new Activity());
        when(activityRepository.findActivitiesByUserId(anyLong())).thenReturn(activities);

        List<Activity> result = activityDAO.findActivitiesByUserId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(activityRepository, times(1)).findActivitiesByUserId(1L);
    }

    @Test
    void findActivitiesByUserId_ReturnsEmptyList_WhenNoActivitiesExistForUser() {
        when(activityRepository.findActivitiesByUserId(anyLong())).thenReturn(List.of());

        List<Activity> result = activityDAO.findActivitiesByUserId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(activityRepository, times(1)).findActivitiesByUserId(1L);
    }

    @Test
    void findActivitiesByCompanyId_ReturnsListOfActivities_WhenActivitiesExistForCompany() {
        List<Activity> activities = List.of(new Activity(), new Activity());
        when(activityRepository.findActivitiesByCompanyId(1L)).thenReturn(activities);

        List<Activity> result = activityDAO.findActivitiesByCompanyId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(activityRepository, times(1)).findActivitiesByCompanyId(1L);
    }

    @Test
    void findActivitiesByCompanyId_ReturnsEmptyList_WhenNoActivitiesExistForCompany() {
        when(activityRepository.findActivitiesByCompanyId(1L)).thenReturn(List.of());

        List<Activity> result = activityDAO.findActivitiesByCompanyId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(activityRepository, times(1)).findActivitiesByCompanyId(1L);
    }

}