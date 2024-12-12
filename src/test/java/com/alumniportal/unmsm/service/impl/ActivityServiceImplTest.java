package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.data.ActivityProvider;
import com.alumniportal.unmsm.dto.request.ActivityRequestDTO;
import com.alumniportal.unmsm.dto.response.ActivityResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.ActivityMapper;
import com.alumniportal.unmsm.model.Activity;
import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.interfaces.IActivityDAO;
import com.alumniportal.unmsm.persistence.interfaces.ICompanyDAO;
import com.alumniportal.unmsm.persistence.interfaces.IUserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    // activityMapper real
    private ActivityMapper activityMapper;

    //    @InjectMocks
    private ActivityServiceImpl activityService;

    @BeforeEach
    void setUp() {
        activityMapper = new ActivityMapper(new ModelMapper());
        activityService = spy(new ActivityServiceImpl(activityDAO, userDAO, companyDAO, activityMapper, s3Client, lambdaClient));
    }

    @Test
    void findAllReturnsActivityDTOList() {
        when(activityDAO.findAll()).thenReturn(ActivityProvider.activityList());

        List<ActivityResponseDTO> activityDTOList = activityService.findAll();
        assertEquals("Taller de Java", activityDTOList.get(0).getTitle());
        assertEquals("Conferencia de Java", activityDTOList.get(1).getTitle());
        assertEquals("Seminario de Java", activityDTOList.get(2).getTitle());
    }


    @Test
    void findActivitiesByUserIdReturnsActivityDTOListWhenActivitiesExist() {
        Long userId = 1L;
        when(activityDAO.findActivitiesByUserId(userId)).thenReturn(ActivityProvider.activityList());

        List<ActivityResponseDTO> result = activityService.findActivitiesByUserId(userId);

        assertNotNull(result);
        assertEquals(5, result.size());
    }

    @Test
    void findActivitiesByUserIdThrowsExceptionWhenNoActivitiesExist() {
        Long userId = 1L;
        when(activityDAO.findActivitiesByUserId(userId)).thenReturn(List.of());

        assertThrows(AppException.class, () -> activityService.findActivitiesByUserId(userId));

    }

    @Test
    void findActivitiesByCompanyIdReturnsActivityDTOListWhenActivitiesExist() {
        Long companyId = 1L;
        when(activityDAO.findActivitiesByCompanyId(companyId)).thenReturn(ActivityProvider.activityList());

        List<ActivityResponseDTO> result = activityService.findActivitiesByCompanyId(companyId);

        assertNotNull(result);
        assertEquals(5, result.size());
    }

    @Test
    void findActivitiesByCompanyIdThrowsExceptionWhenNoActivitiesExist() {
        Long companyId = 1L;
        when(activityDAO.findActivitiesByCompanyId(companyId)).thenReturn(List.of());

        assertThrows(AppException.class, () -> activityService.findActivitiesByCompanyId(companyId));
        verify(activityDAO, times(1)).findActivitiesByCompanyId(companyId);
    }

    @Test
    void findActivityByIdReturnsActivityDTOWhenActivityExists() {
        Long activityId = 1L;
        when(activityDAO.findById(activityId)).thenReturn(ActivityProvider.activityOne());

        ActivityResponseDTO result = activityService.findById(activityId);

        assertNotNull(result);
        assertEquals("Charla magistral de Java", result.getTitle());
    }

    @Test
    void findActivityByIdReturnsNull() {
        Long activityId = 1L;
        when(activityDAO.findById(activityId)).thenReturn(null);

        assertThrows(AppException.class, () -> activityService.findById(activityId));

        verify(activityDAO, times(1)).findById(activityId);
    }

    @Test
    void SaveActivitySuccessfully() {
        // Arrange
        Activity activity = ActivityProvider.activityOne();

        // Act
        activityService.save(activity); // Llamamos al método void.

        // Assert
        // Verificamos que el método fue llamado exactamente una vez con el argumento correcto.
        verify(activityDAO, times(1)).save(any(Activity.class));
    }

    @Test
    void deleteByIdDeletesActivityWhenActivityHasNoImage() throws Exception {
        Long activityId = 1L;
        Activity activity = new Activity();
        activity.setId(activityId);
        when(activityDAO.findById(activityId)).thenReturn(activity);

        activityService.deleteById(activityId);

        verify(activityDAO, times(1)).deleteById(activityId);
    }

    @Test
    void deleteByIdThrowsExceptionWhenActivityNotFound() {
        Long activityId = 1L;
        when(activityDAO.findById(activityId)).thenReturn(null);

        assertThrows(Exception.class, () -> activityService.deleteById(activityId));
    }

    @Test
    void deleteByIdDeletesActivityAndImageWhenActivityHasImage() throws Exception {
        Long activityId = 1L;
        Activity activity = new Activity();
        activity.setId(activityId);
        activity.setUrl("some/image/url");
        when(activityDAO.findById(activityId)).thenReturn(activity);

        activityService.deleteById(activityId);

        verify(activityDAO, times(1)).deleteById(activityId);

    }

    @Test
    void saveActivityByUserIdSaveActivitySuccessfully() {
        User user = User.builder().id(1L).name("Luis").activityList(new ArrayList<>()).build();

        ActivityRequestDTO activity = new ActivityRequestDTO();

        when(userDAO.findById(anyLong())).thenReturn(user);

        doNothing().when(activityService).invokeLambda(anyString(), anyString(), any(Activity.class));

        activityService.saveActivityByUserId(activity, user.getId());
        verify(activityDAO, times(1)).save(any(Activity.class));
    }

    @Test
    void saveActivityByUserIdThrowsExceptionWhenUserIsNull() {
        ActivityRequestDTO activity = new ActivityRequestDTO();

        when(userDAO.findById(anyLong())).thenReturn(null);

        assertThrows(AppException.class, () -> activityService.saveActivityByUserId(activity, 1L));
    }

    @Test
    void saveActivityByCompanyIdSaveActivitySuccessfully() {

        Company company = Company.builder().id(1L).name("UNMSM").activityList(new ArrayList<>()).build();

        ActivityRequestDTO activity = new ActivityRequestDTO();

        when(companyDAO.findById(anyLong())).thenReturn(company);

        doNothing().when(activityService).invokeLambda(anyString(), anyString(), any(Activity.class));

        activityService.saveActivityByCompanyId(activity, company.getId());
        verify(activityDAO, times(1)).save(any(Activity.class));

    }

    @Test
    void saveActivityByCompanyIdThrowsExceptionWhenCompanyIsNull() {
        ActivityRequestDTO activity = new ActivityRequestDTO();

        when(companyDAO.findById(anyLong())).thenReturn(null);

        assertThrows(AppException.class, () -> activityService.saveActivityByCompanyId(activity, 1L));

//        No se necesita generar el doNothing para invokeLambda porque no se llama a ese método en el caso de que la compañía sea nula.
    }


    @Test
    void saveActivityWithImageByUserIdThrowsExceptionWhenUserIsNull() {

        MultipartFile image = mock(MultipartFile.class);

        ActivityRequestDTO activity = new ActivityRequestDTO();

        when(userDAO.findById(anyLong())).thenReturn(null);

        assertThrows(AppException.class, () -> activityService.saveActivityWithImageByUserId(activity, 1L, image));

    }

    @Test
    void saveActivityWithImageByUserIdSaveActivitySuccessfullyWhenImageIsNull() throws IOException {

        User user = User.builder().id(1L).name("Luis").activityList(new ArrayList<>()).build();

        ActivityRequestDTO activity = new ActivityRequestDTO();

        when(userDAO.findById(anyLong())).thenReturn(user);

        doNothing().when(activityService).invokeLambda(anyString(), anyString(), any(Activity.class));

        activityService.saveActivityWithImageByUserId(activity, user.getId(), null);

        verify(activityDAO, times(1)).save(any(Activity.class));
    }

    @Test
    void saveActivityWithImageByUserIdSaveActivitySuccessfullyWhenImageIsEmpty() throws IOException {

        User user = User.builder().id(1L).name("Luis").activityList(new ArrayList<>()).build();

        MultipartFile imageEmpty = mock(MultipartFile.class);
        when(imageEmpty.isEmpty()).thenReturn(true);

        ActivityRequestDTO activity = new ActivityRequestDTO();

        when(userDAO.findById(anyLong())).thenReturn(user);

        doNothing().when(activityService).invokeLambda(anyString(), anyString(), any(Activity.class));

        activityService.saveActivityWithImageByUserId(activity, user.getId(), imageEmpty);

        verify(activityDAO, times(1)).save(any(Activity.class));

    }

    @Test
    void saveActivityWithImageByCompanyIdSaveActivitySuccessfully() throws IOException {

        Company company = Company.builder().id(1L).name("TechCorp").activityList(new ArrayList<>()).build();

        MultipartFile image = mock(MultipartFile.class);

        Activity activity = ActivityProvider.activityOne();

        when(companyDAO.findById(anyLong())).thenReturn(company);

        doNothing().when(activityService).invokeLambda(anyString(), anyString(), any(Activity.class));

        doNothing().when(activityService).uploadActivityImage(anyLong(), any(MultipartFile.class));

        activityService.saveActivityWithImageByCompanyId(activity, company.getId(), image);

        verify(activityDAO, times(1)).save(any(Activity.class));
    }

    @Test
    void saveActivityWithImageByCompanyIdThrowsExceptionWhenCompanyIsNull() {

        MultipartFile image = mock(MultipartFile.class);

        Activity activity = ActivityProvider.activityOne();

        when(companyDAO.findById(anyLong())).thenReturn(null);

        assertThrows(AppException.class, () -> activityService.saveActivityWithImageByCompanyId(activity, 1L, image));
    }

    @Test
    void saveActivityWithImageByCompanyIdSaveActivitySuccessfullyWhenImageIsNull() throws IOException {

        Company company = Company.builder().id(1L).name("TechCorp").activityList(new ArrayList<>()).build();

        Activity activity = ActivityProvider.activityOne();

        when(companyDAO.findById(anyLong())).thenReturn(company);

        doNothing().when(activityService).invokeLambda(anyString(), anyString(), any(Activity.class));

        activityService.saveActivityWithImageByCompanyId(activity, company.getId(), null);

        verify(activityDAO, times(1)).save(any(Activity.class));
    }

    @Test
    void SaveActivityWithImageByCompanyIdSaveActivitySuccessfullyWhenImageIsEmpty() throws IOException {

        Company company = Company.builder().id(1L).name("TechCorp").activityList(new ArrayList<>()).build();

        MultipartFile imageEmpty = mock(MultipartFile.class);
        when(imageEmpty.isEmpty()).thenReturn(true);

        Activity activity = ActivityProvider.activityOne();

        when(companyDAO.findById(anyLong())).thenReturn(company);

        doNothing().when(activityService).invokeLambda(anyString(), anyString(), any(Activity.class));

        activityService.saveActivityWithImageByCompanyId(activity, company.getId(), imageEmpty);

        verify(activityDAO, times(1)).save(any(Activity.class));
    }


    @Test
    void updateActivityUpdatesFieldsSuccessfully() {
        Long activityId = 1L;
        Activity activity = Activity.builder().
                id(activityId).
                title("Original Title").
                description("Original Description").
                build();

        when(activityDAO.findById(activityId)).thenReturn(activity);


        Map<String, Object> fields = Map.of(
                "title", "Updated Title",
                "description", "Updated Description"
        );

        activityService.updateActivity(activityId, fields);

        assertEquals("Updated Title", activity.getTitle());
        assertEquals("Updated Description", activity.getDescription());
        verify(activityDAO, times(1)).save(activity);
    }

    @Test
    void updateActivityThrowsExceptionWhenActivityNotFound() {

        when(activityDAO.findById(anyLong())).thenReturn(null);

        Map<String, Object> fields = Map.of("title", "Updated Title");

        assertThrows(AppException.class, () -> activityService.updateActivity(1L, fields));
    }

    @Test
    void updateActivitySetsFieldToNullWhenEmptyStringProvided() {
        Long activityId = 1L;
        Activity activity = new Activity();
        activity.setId(activityId);
        activity.setTitle("Original Title");
        when(activityDAO.findById(activityId)).thenReturn(activity);

        Map<String, Object> fields = Map.of("title", "");

        activityService.updateActivity(activityId, fields);

        assertNull(activity.getTitle());
        verify(activityDAO, times(1)).save(activity);
    }

    @Test
    void updateActivityConvertsStringToLocalDateForDateFields() {
        Long activityId = 1L;
        Activity activity = new Activity();
        activity.setId(activityId);
        when(activityDAO.findById(activityId)).thenReturn(activity);

        Map<String, Object> fields = Map.of(
                "startDate", "2023-01-01",
                "endDate", "2023-12-31"
        );

        activityService.updateActivity(activityId, fields);

        assertEquals(LocalDate.of(2023, 1, 1), activity.getStartDate());
        assertEquals(LocalDate.of(2023, 12, 31), activity.getEndDate());
        verify(activityDAO, times(1)).save(activity);
    }

    @Test
    void getFileNameReturnsFileNameWhenActivityHasImage() {
        Long activityId = 1L;
        Activity activity = new Activity();
        activity.setId(activityId);
        activity.setUrl("activityimages/1image.jpg");
        when(activityDAO.findById(activityId)).thenReturn(activity);

        String fileName = activityService.getFileName(activityId);

        assertEquals("1image.jpg", fileName);
    }

    @Test
    void getFileNameThrowsExceptionWhenActivityHasNoImage() {
        Long activityId = 1L;
        Activity activity = new Activity();
        activity.setId(activityId);
        activity.setUrl(null);
        when(activityDAO.findById(activityId)).thenReturn(activity);

        assertThrows(RuntimeException.class, () -> activityService.getFileName(activityId));
    }


}
