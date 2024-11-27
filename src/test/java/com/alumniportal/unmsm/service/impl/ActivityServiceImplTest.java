package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.Data.ActivityProvider;
import com.alumniportal.unmsm.dto.ActivityDTO;
import com.alumniportal.unmsm.model.Activity;
import com.alumniportal.unmsm.persistence.IActivityDAO;
import com.alumniportal.unmsm.persistence.ICompanyDAO;
import com.alumniportal.unmsm.persistence.IUserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceImplTest {

    @Mock
    private IActivityDAO activityDAO;

    @Mock
    private IUserDAO userDAO;

    @Mock
    private ICompanyDAO companyDAO;

    @Mock
    private S3Client s3Client;

    @Mock
    private LambdaClient lambdaClient;

    // ModelMapper real
    private ModelMapper modelMapper;

    @InjectMocks
    private ActivityServiceImpl activityService;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        activityService = new ActivityServiceImpl(activityDAO, userDAO, companyDAO, modelMapper, s3Client, lambdaClient);
    }

    @Test
    void testFindAll() {
        when(activityDAO.findAll()).thenReturn(ActivityProvider.activityList());

        List<ActivityDTO> activityDTOList = activityService.findAll();
        assertEquals("Taller de Java", activityDTOList.get(0).getTitle());
        assertEquals("Conferencia de Java", activityDTOList.get(1).getTitle());
        assertEquals("Seminario de Java", activityDTOList.get(2).getTitle());
    }


    @Test
    void testFindActivitiesByUserId_ReturnsActivityDTOList_WhenActivitiesExist() {
        Long userId = 1L;
        when(activityDAO.findActivitiesByUserId(userId)).thenReturn(ActivityProvider.activityList());

        List<ActivityDTO> result = activityService.findActivitiesByUserId(userId);

        assertNotNull(result);
        assertEquals(5, result.size());
    }

    @Test
    void testFindActivitiesByUserId_ReturnsEmptyList_WhenNoActivitiesExist() {
        Long userId = 1L;
        when(activityDAO.findActivitiesByUserId(userId)).thenReturn(List.of());

        List<ActivityDTO> result = activityService.findActivitiesByUserId(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindActivitiesByCompanyId_ReturnsActivityDTOList_WhenActivitiesExist() {
        Long companyId = 1L;
        when(activityDAO.findActivitiesByCompanyId(companyId)).thenReturn(ActivityProvider.activityList());

        List<ActivityDTO> result = activityService.findActivitiesByCompanyId(companyId);

        assertNotNull(result);
        assertEquals(5, result.size());
    }

    @Test
    void testFindActivitiesByCompanyId_ReturnsEmptyList_WhenNoActivitiesExist() {
        Long companyId = 1L;
        when(activityDAO.findActivitiesByCompanyId(companyId)).thenReturn(List.of());

        List<ActivityDTO> result = activityService.findActivitiesByCompanyId(companyId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindActivityById_ReturnsActivityDTO_WhenActivityExists() {
        Long activityId = 1L;
        when(activityDAO.findById(activityId)).thenReturn(ActivityProvider.activityOne());

        ActivityDTO result = activityService.findById(activityId);

        assertNotNull(result);
        assertEquals("Charla magistral de Java", result.getTitle());
    }

    @Test
    void testFindActivityById_ReturnsNull() {
        Long activityId = 1L;
        when(activityDAO.findById(activityId)).thenReturn(null);

        ActivityDTO result = activityService.findById(activityId);

        assertNull(result);
    }

    @Test
    void testSaveActivitySuccessfully() {
        // Arrange
        Activity activity = ActivityProvider.activityOne();

        // Act
        activityService.save(activity); // Llamamos al método void.

        // Assert
        // Verificamos que el método fue llamado exactamente una vez con el argumento correcto.
        verify(activityDAO, times(1)).save(any(Activity.class));
    }

    @Test
    void deleteById_DeletesActivity_WhenActivityHasNoImage() throws Exception {
        Long activityId = 1L;
        Activity activity = new Activity();
        activity.setId(activityId);
        when(activityDAO.findById(activityId)).thenReturn(activity);

        activityService.deleteById(activityId);

        verify(activityDAO, times(1)).deleteById(activityId);
    }

    @Test
    void deleteById_ThrowsException_WhenActivityNotFound() {
        Long activityId = 1L;
        when(activityDAO.findById(activityId)).thenReturn(null);

        assertThrows(Exception.class, () -> activityService.deleteById(activityId));
    }

    @Test
    void deleteById_DeletesActivityAndImage_WhenActivityHasImage() throws Exception {
        Long activityId = 1L;
        Activity activity = new Activity();
        activity.setId(activityId);
        activity.setUrl("some/image/url");
        when(activityDAO.findById(activityId)).thenReturn(activity);

        activityService.deleteById(activityId);

        verify(activityDAO, times(1)).deleteById(activityId);

    }


}
