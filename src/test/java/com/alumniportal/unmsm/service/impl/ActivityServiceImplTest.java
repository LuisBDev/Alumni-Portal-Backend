package com.alumniportal.unmsm.service.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceImplTest {

//    @Mock
//    private IActivityDAO activityDAO;
//
//    @Mock
//    private IUserDAO userDAO;
//
//    @Mock
//    private ICompanyDAO companyDAO;
//
//    @Mock
//    private S3Client s3Client;
//
//    @Mock
//    private LambdaClient lambdaClient;
//
//    // activityMapper real
//    private ModelMapper modelMapper;
//
//    //    @InjectMocks
//    private ActivityServiceImpl activityService;
//
//    @BeforeEach
//    void setUp() {
//        modelMapper = new ModelMapper();
//        activityService = spy(new ActivityServiceImpl(activityDAO, userDAO, companyDAO, modelMapper, s3Client, lambdaClient));
//    }
//
//    @Test
//    void testFindAll() {
//        when(activityDAO.findAll()).thenReturn(ActivityProvider.activityList());
//
//        List<ActivityResponseDTO> activityDTOList = activityService.findAll();
//        assertEquals("Taller de Java", activityDTOList.get(0).getTitle());
//        assertEquals("Conferencia de Java", activityDTOList.get(1).getTitle());
//        assertEquals("Seminario de Java", activityDTOList.get(2).getTitle());
//    }
//
//
//    @Test
//    void testFindActivitiesByUserId_ReturnsActivityDTOList_WhenActivitiesExist() {
//        Long userId = 1L;
//        when(activityDAO.findActivitiesByUserId(userId)).thenReturn(ActivityProvider.activityList());
//
//        List<ActivityResponseDTO> result = activityService.findActivitiesByUserId(userId);
//
//        assertNotNull(result);
//        assertEquals(5, result.size());
//    }
//
//    @Test
//    void testFindActivitiesByUserId_ReturnsEmptyList_WhenNoActivitiesExist() {
//        Long userId = 1L;
//        when(activityDAO.findActivitiesByUserId(userId)).thenReturn(List.of());
//
//        List<ActivityResponseDTO> result = activityService.findActivitiesByUserId(userId);
//
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    void testFindActivitiesByCompanyId_ReturnsActivityDTOList_WhenActivitiesExist() {
//        Long companyId = 1L;
//        when(activityDAO.findActivitiesByCompanyId(companyId)).thenReturn(ActivityProvider.activityList());
//
//        List<ActivityResponseDTO> result = activityService.findActivitiesByCompanyId(companyId);
//
//        assertNotNull(result);
//        assertEquals(5, result.size());
//    }
//
//    @Test
//    void testFindActivitiesByCompanyId_ReturnsEmptyList_WhenNoActivitiesExist() {
//        Long companyId = 1L;
//        when(activityDAO.findActivitiesByCompanyId(companyId)).thenReturn(List.of());
//
//        List<ActivityResponseDTO> result = activityService.findActivitiesByCompanyId(companyId);
//
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    void testFindActivityById_ReturnsActivityDTO_WhenActivityExists() {
//        Long activityId = 1L;
//        when(activityDAO.findById(activityId)).thenReturn(ActivityProvider.activityOne());
//
//        ActivityResponseDTO result = activityService.findById(activityId);
//
//        assertNotNull(result);
//        assertEquals("Charla magistral de Java", result.getTitle());
//    }
//
//    @Test
//    void testFindActivityById_ReturnsNull() {
//        Long activityId = 1L;
//        when(activityDAO.findById(activityId)).thenReturn(null);
//
//        ActivityResponseDTO result = activityService.findById(activityId);
//
//        assertNull(result);
//    }
//
//    @Test
//    void testSaveActivitySuccessfully() {
//        // Arrange
//        Activity activity = ActivityProvider.activityOne();
//
//        // Act
//        activityService.save(activity); // Llamamos al método void.
//
//        // Assert
//        // Verificamos que el método fue llamado exactamente una vez con el argumento correcto.
//        verify(activityDAO, times(1)).save(any(Activity.class));
//    }
//
//    @Test
//    void deleteById_DeletesActivity_WhenActivityHasNoImage() throws Exception {
//        Long activityId = 1L;
//        Activity activity = new Activity();
//        activity.setId(activityId);
//        when(activityDAO.findById(activityId)).thenReturn(activity);
//
//        activityService.deleteById(activityId);
//
//        verify(activityDAO, times(1)).deleteById(activityId);
//    }
//
//    @Test
//    void deleteById_ThrowsException_WhenActivityNotFound() {
//        Long activityId = 1L;
//        when(activityDAO.findById(activityId)).thenReturn(null);
//
//        assertThrows(Exception.class, () -> activityService.deleteById(activityId));
//    }
//
//    @Test
//    void deleteById_DeletesActivityAndImage_WhenActivityHasImage() throws Exception {
//        Long activityId = 1L;
//        Activity activity = new Activity();
//        activity.setId(activityId);
//        activity.setUrl("some/image/url");
//        when(activityDAO.findById(activityId)).thenReturn(activity);
//
//        activityService.deleteById(activityId);
//
//        verify(activityDAO, times(1)).deleteById(activityId);
//
//    }
//
//    @Test
//    void testSaveActivityByUserId() {
//        User user = User.builder().id(1L).name("Luis").activityList(new ArrayList<>()).build();
//
//        Activity activity = ActivityProvider.activityOne();
//
//        when(userDAO.findById(anyLong())).thenReturn(user);
//
//        doNothing().when(activityService).invokeLambda(anyString(), anyString(), any(Activity.class));
//
//        activityService.saveActivityByUserId(activity, user.getId());
//        verify(activityDAO, times(1)).save(any(Activity.class));
//    }
//
//    @Test
//    void testSaveActivityByUserId_WhenUserIsNull() {
//        Activity activity = ActivityProvider.activityOne();
//
//        when(userDAO.findById(anyLong())).thenReturn(null);
//
//        assertThrows(IllegalArgumentException.class, () -> activityService.saveActivityByUserId(activity, 1L));
//    }
//
//    @Test
//    void testSaveActivityByCompanyId() {
//
//        Company company = Company.builder().id(1L).name("UNMSM").activityList(new ArrayList<>()).build();
//
//        Activity activity = ActivityProvider.activityOne();
//
//        when(companyDAO.findById(anyLong())).thenReturn(company);
//
//        doNothing().when(activityService).invokeLambda(anyString(), anyString(), any(Activity.class));
//
//        activityService.saveActivityByCompanyId(activity, company.getId());
//        verify(activityDAO, times(1)).save(any(Activity.class));
//
//    }
//
//    @Test
//    void testSaveActivityByCompanyId_WhenCompanyIsNull() {
//        Activity activity = ActivityProvider.activityOne();
//
//        when(companyDAO.findById(anyLong())).thenReturn(null);
//
//        assertThrows(IllegalArgumentException.class, () -> activityService.saveActivityByCompanyId(activity, 1L));
//
////        No se necesita generar el doNothing para invokeLambda porque no se llama a ese método en el caso de que la compañía sea nula.
//    }
//
//    @Test
//    void testSaveActivityWithImageByUserId() throws IOException {
//
//        User user = User.builder().id(1L).name("Luis").activityList(new ArrayList<>()).build();
//
//        MultipartFile image = mock(MultipartFile.class);
//
//        Activity activity = ActivityProvider.activityOne();
//
//        when(userDAO.findById(anyLong())).thenReturn(user);
//
//        doNothing().when(activityService).invokeLambda(anyString(), anyString(), any(Activity.class));
//
//        doNothing().when(activityService).uploadActivityImage(anyLong(), any(MultipartFile.class));
//
//        activityService.saveActivityWithImageByUserId(activity, user.getId(), image);
//
//        verify(activityDAO, times(1)).save(any(Activity.class));
//    }
//
//    @Test
//    void testSaveActivityWithImageByUserId_WhenUserIsNull() {
//
//        MultipartFile image = mock(MultipartFile.class);
//
//        Activity activity = ActivityProvider.activityOne();
//
//        when(userDAO.findById(anyLong())).thenReturn(null);
//
//        assertThrows(IllegalArgumentException.class, () -> activityService.saveActivityWithImageByUserId(activity, 1L, image));
//
//    }
//
//    @Test
//    void testSaveActivityWithImageByUserId_WhenImageIsNull() throws IOException {
//
//        User user = User.builder().id(1L).name("Luis").activityList(new ArrayList<>()).build();
//
//        Activity activity = ActivityProvider.activityOne();
//
//        when(userDAO.findById(anyLong())).thenReturn(user);
//
//        doNothing().when(activityService).invokeLambda(anyString(), anyString(), any(Activity.class));
//
//        activityService.saveActivityWithImageByUserId(activity, user.getId(), null);
//
//        verify(activityDAO, times(1)).save(any(Activity.class));
//    }
//
//    @Test
//    void testSaveActivityWithImageByUserId_WhenImageIsEmpty() throws IOException {
//
//        User user = User.builder().id(1L).name("Luis").activityList(new ArrayList<>()).build();
//
//        MultipartFile imageEmpty = mock(MultipartFile.class);
//        when(imageEmpty.isEmpty()).thenReturn(true);
//
//        Activity activity = ActivityProvider.activityOne();
//
//        when(userDAO.findById(anyLong())).thenReturn(user);
//
//        doNothing().when(activityService).invokeLambda(anyString(), anyString(), any(Activity.class));
//
//        activityService.saveActivityWithImageByUserId(activity, user.getId(), imageEmpty);
//
//        verify(activityDAO, times(1)).save(any(Activity.class));
//
//    }
//
//    @Test
//    void testSaveActivityWithImageByCompanyId() throws IOException {
//
//        Company company = Company.builder().id(1L).name("TechCorp").activityList(new ArrayList<>()).build();
//
//        MultipartFile image = mock(MultipartFile.class);
//
//        Activity activity = ActivityProvider.activityOne();
//
//        when(companyDAO.findById(anyLong())).thenReturn(company);
//
//        doNothing().when(activityService).invokeLambda(anyString(), anyString(), any(Activity.class));
//
//        doNothing().when(activityService).uploadActivityImage(anyLong(), any(MultipartFile.class));
//
//        activityService.saveActivityWithImageByCompanyId(activity, company.getId(), image);
//
//        verify(activityDAO, times(1)).save(any(Activity.class));
//    }
//
//    @Test
//    void testSaveActivityWithImageByCompanyId_WhenCompanyIsNull() {
//
//        MultipartFile image = mock(MultipartFile.class);
//
//        Activity activity = ActivityProvider.activityOne();
//
//        when(companyDAO.findById(anyLong())).thenReturn(null);
//
//        assertThrows(IllegalArgumentException.class, () -> activityService.saveActivityWithImageByCompanyId(activity, 1L, image));
//    }
//
//    @Test
//    void testSaveActivityWithImageByCompanyId_WhenImageIsNull() throws IOException {
//
//        Company company = Company.builder().id(1L).name("TechCorp").activityList(new ArrayList<>()).build();
//
//        Activity activity = ActivityProvider.activityOne();
//
//        when(companyDAO.findById(anyLong())).thenReturn(company);
//
//        doNothing().when(activityService).invokeLambda(anyString(), anyString(), any(Activity.class));
//
//        activityService.saveActivityWithImageByCompanyId(activity, company.getId(), null);
//
//        verify(activityDAO, times(1)).save(any(Activity.class));
//    }
//
//    @Test
//    void testSaveActivityWithImageByCompanyId_WhenImageIsEmpty() throws IOException {
//
//        Company company = Company.builder().id(1L).name("TechCorp").activityList(new ArrayList<>()).build();
//
//        MultipartFile imageEmpty = mock(MultipartFile.class);
//        when(imageEmpty.isEmpty()).thenReturn(true);
//
//        Activity activity = ActivityProvider.activityOne();
//
//        when(companyDAO.findById(anyLong())).thenReturn(company);
//
//        doNothing().when(activityService).invokeLambda(anyString(), anyString(), any(Activity.class));
//
//        activityService.saveActivityWithImageByCompanyId(activity, company.getId(), imageEmpty);
//
//        verify(activityDAO, times(1)).save(any(Activity.class));
//    }
//
//
//    @Test
//    void updateActivity_UpdatesFieldsSuccessfully() {
//        Long activityId = 1L;
//        Activity activity = Activity.builder().
//                id(activityId).
//                title("Original Title").
//                description("Original Description").
//                build();
//
//        when(activityDAO.findById(activityId)).thenReturn(activity);
//
//
//        Map<String, Object> fields = Map.of(
//                "title", "Updated Title",
//                "description", "Updated Description"
//        );
//
//        activityService.updateActivity(activityId, fields);
//
//        assertEquals("Updated Title", activity.getTitle());
//        assertEquals("Updated Description", activity.getDescription());
//        verify(activityDAO, times(1)).save(activity);
//    }
//
//    @Test
//    void updateActivity_ThrowsException_WhenActivityNotFound() {
//
//        when(activityDAO.findById(anyLong())).thenReturn(null);
//
//        Map<String, Object> fields = Map.of("title", "Updated Title");
//
//        assertThrows(RuntimeException.class, () -> activityService.updateActivity(1L, fields));
//    }
//
//    @Test
//    void updateActivity_SetsFieldToNull_WhenEmptyStringProvided() {
//        Long activityId = 1L;
//        Activity activity = new Activity();
//        activity.setId(activityId);
//        activity.setTitle("Original Title");
//        when(activityDAO.findById(activityId)).thenReturn(activity);
//
//        Map<String, Object> fields = Map.of("title", "");
//
//        activityService.updateActivity(activityId, fields);
//
//        assertNull(activity.getTitle());
//        verify(activityDAO, times(1)).save(activity);
//    }
//
//    @Test
//    void updateActivity_ConvertsStringToLocalDate_ForDateFields() {
//        Long activityId = 1L;
//        Activity activity = new Activity();
//        activity.setId(activityId);
//        when(activityDAO.findById(activityId)).thenReturn(activity);
//
//        Map<String, Object> fields = Map.of(
//                "startDate", "2023-01-01",
//                "endDate", "2023-12-31"
//        );
//
//        activityService.updateActivity(activityId, fields);
//
//        assertEquals(LocalDate.of(2023, 1, 1), activity.getStartDate());
//        assertEquals(LocalDate.of(2023, 12, 31), activity.getEndDate());
//        verify(activityDAO, times(1)).save(activity);
//    }
//
//    @Test
//    void getFileName_ReturnsFileName_WhenActivityHasImage() {
//        Long activityId = 1L;
//        Activity activity = new Activity();
//        activity.setId(activityId);
//        activity.setUrl("activityimages/1_image.jpg");
//        when(activityDAO.findById(activityId)).thenReturn(activity);
//
//        String fileName = activityService.getFileName(activityId);
//
//        assertEquals("1_image.jpg", fileName);
//    }
//
//    @Test
//    void getFileName_ThrowsException_WhenActivityHasNoImage() {
//        Long activityId = 1L;
//        Activity activity = new Activity();
//        activity.setId(activityId);
//        activity.setUrl(null);
//        when(activityDAO.findById(activityId)).thenReturn(activity);
//
//        assertThrows(RuntimeException.class, () -> activityService.getFileName(activityId));
//    }
//
//
//

}
