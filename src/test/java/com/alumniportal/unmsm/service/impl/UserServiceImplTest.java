package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.request.PasswordChangeRequestDTO;
import com.alumniportal.unmsm.dto.response.ActivityResponseDTO;
import com.alumniportal.unmsm.dto.response.UserCVResponseDTO;
import com.alumniportal.unmsm.dto.response.UserResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.UserMapper;
import com.alumniportal.unmsm.model.*;
import com.alumniportal.unmsm.persistence.interfaces.*;
import com.alumniportal.unmsm.service.interfaces.IActivityService;
import com.alumniportal.unmsm.util.ImageManagement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private IUserDAO userDAO;

    @Mock
    private ICertificationDAO certificationDAO;

    @Mock
    private IEducationDAO educationDAO;

    @Mock
    private IProjectDAO projectDAO;

    @Mock
    private IWorkExperienceDAO workExperienceDAO;

    @Mock
    private ISkillDAO skillDAO;


    private UserMapper userMapper;

    @Mock
    private ImageManagement imageManagement;

    @Mock
    private IActivityService activityService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper(new ModelMapper());
        userService = new UserServiceImpl(userDAO, certificationDAO, educationDAO, projectDAO, workExperienceDAO, skillDAO, userMapper, imageManagement, activityService, passwordEncoder);
    }

    @Test
    void findAllReturnsListOfUserResponseDTOs() {
        User user = new User();
        when(userDAO.findAll()).thenReturn(List.of(user));
        List<UserResponseDTO> result = userService.findAll();
        assertEquals(1, result.size());
        verify(userDAO, times(1)).findAll();
    }

    @Test
    void findAllThrowsExceptionWhenNoUsersFound() {
        when(userDAO.findAll()).thenReturn(Collections.emptyList());
        AppException exception = assertThrows(AppException.class, () -> userService.findAll());
        assertEquals("No users found!", exception.getMessage());
        verify(userDAO, times(1)).findAll();
    }

    @Test
    void findByIdReturnsUserResponseDTO() {
        User user = new User();
        when(userDAO.findById(1L)).thenReturn(user);
        UserResponseDTO result = userService.findById(1L);
        assertNotNull(result);
        verify(userDAO, times(1)).findById(1L);
    }

    @Test
    void findByIdThrowsExceptionWhenUserNotFound() {
        when(userDAO.findById(1L)).thenReturn(null);
        AppException exception = assertThrows(AppException.class, () -> userService.findById(1L));
        assertEquals("User not found!", exception.getMessage());
        verify(userDAO, times(1)).findById(1L);
    }

    @Test
    void saveCallsUserDAOSave() {
        User user = new User();
        userService.save(user);
        verify(userDAO, times(1)).save(user);
    }

    @Test
    void deleteByIdDeletesUserWhenFound() {
        User user = new User();
        when(userDAO.findById(1L)).thenReturn(user);
        userService.deleteById(1L);
        verify(userDAO, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdThrowsExceptionWhenUserNotFound() {
        when(userDAO.findById(1L)).thenReturn(null);
        AppException exception = assertThrows(AppException.class, () -> userService.deleteById(1L));
        assertEquals("User not found!", exception.getMessage());
        verify(userDAO, never()).deleteById(1L);
    }

    @Test
    void deleteByIdDeletesUserWhenHasPhotoUrl() {
        User user = new User();
        user.setPhotoUrl("test");
        when(userDAO.findById(1L)).thenReturn(user);
        userService.deleteById(1L);
        verify(userDAO, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdDeletesUserWhenActivityServiceFindsActivities() {
        User user = new User();
        Activity activity = new Activity();
        activity.setUrl(null);
        user.setActivityList(List.of(activity));
        when(userDAO.findById(1L)).thenReturn(user);
        userService.deleteById(1L);
        verify(userDAO, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdDeletesUserWhenActivityServiceFindsActivitiesWithUrl() throws Exception {
        User user = new User();
        Activity activity = new Activity();
        activity.setUrl("test");
        user.setActivityList(List.of(activity));
        when(userDAO.findById(1L)).thenReturn(user);
        userService.deleteById(1L);
        verify(activityService, times(1)).deleteActivityImage(activity.getId());
        verify(userDAO, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdDeleteUserWhenActivityServiceFindsActivitiesWithUrlAndThrowsException() throws Exception {
        User user = new User();
        when(userDAO.findById(1L)).thenReturn(user);
        Activity activity = new Activity();
        activity.setUrl("test");
        user.setActivityList(List.of(activity));
        doThrow(new AppException("Error deleting activity image", "INTERNAL_SERVER_ERROR")).when(activityService).deleteActivityImage(anyLong());
        AppException exception = assertThrows(AppException.class, () -> userService.deleteById(1L));
        assertEquals("Error deleting activity image", exception.getMessage());
        verify(userDAO, never()).deleteById(1L);
    }

    @Test
    void existsByEmailReturnsTrueWhenEmailExists() {
        when(userDAO.existsByEmail("test@example.com")).thenReturn(true);
        boolean result = userService.existsByEmail("test@example.com");
        assertTrue(result);
        verify(userDAO, times(1)).existsByEmail("test@example.com");
    }

    @Test
    void existsByEmailReturnsFalseWhenEmailDoesNotExist() {
        when(userDAO.existsByEmail("test@example.com")).thenReturn(false);
        boolean result = userService.existsByEmail("test@example.com");
        assertFalse(result);
        verify(userDAO, times(1)).existsByEmail("test@example.com");
    }

    @Test
    void findByEmailReturnsUserResponseDTO() {
        User user = new User();
        when(userDAO.findByEmail("test@example.com")).thenReturn(user);
        UserResponseDTO result = userService.findByEmail("test@example.com");
        assertNotNull(result);
        verify(userDAO, times(1)).findByEmail("test@example.com");
    }

    @Test
    void findByEmailThrowsExceptionWhenUserNotFound() {
        when(userDAO.findByEmail("test@example.com")).thenReturn(null);
        AppException exception = assertThrows(AppException.class, () -> userService.findByEmail("test@example.com"));
        assertEquals("User not found!", exception.getMessage());
        verify(userDAO, times(1)).findByEmail("test@example.com");
    }

    @Test
    void saveUserThrowsExceptionWhenEmailExists() {
        User user = new User();
        when(userDAO.existsByEmail(user.getEmail())).thenReturn(true);
        AppException exception = assertThrows(AppException.class, () -> userService.saveUser(user));
        assertEquals("Error: Email is already in use!", exception.getMessage());
        verify(userDAO, never()).save(user);
    }

    @Test
    void saveUserSavesUserWhenEmailDoesNotExist() {
        User user = new User();
        when(userDAO.existsByEmail(user.getEmail())).thenReturn(false);
        userService.saveUser(user);
        verify(userDAO, times(1)).save(user);
    }

    @Test
    void updateUserUpdatesFieldsWhenUserExists() {
        User user = new User();
        when(userDAO.findById(1L)).thenReturn(user);
        Map<String, Object> fields = Map.of("name", "Updated Name");
        userService.updateUser(1L, fields);
        assertEquals("Updated Name", user.getName());
        verify(userDAO, times(1)).save(user);
    }

    @Test
    void updateUserThrowsExceptionWhenUserNotFound() {
        when(userDAO.findById(1L)).thenReturn(null);
        Map<String, Object> fields = Map.of("name", "Updated Name");
        AppException exception = assertThrows(AppException.class, () -> userService.updateUser(1L, fields));
        assertEquals("User not found!", exception.getMessage());
        verify(userDAO, never()).save(any(User.class));
    }

    @Test
    void getUserCVReturnsUserCVResponseDTOWhenUserExists() {
        User user = new User();
        when(userDAO.findById(1L)).thenReturn(user);
        when(certificationDAO.findCertificationsByUserId(1L)).thenReturn(Collections.emptyList());
        when(educationDAO.findEducationsByUserId(1L)).thenReturn(Collections.emptyList());
        when(projectDAO.findProjectsByUserId(1L)).thenReturn(Collections.emptyList());
        when(skillDAO.findSkillsByUserId(1L)).thenReturn(Collections.emptyList());
        when(workExperienceDAO.findWorkExperiencesByUserId(1L)).thenReturn(Collections.emptyList());

        UserCVResponseDTO result = userService.getUserCV(1L);
        assertNotNull(result);
        verify(userDAO, times(1)).findById(1L);
    }

    @Test
    void getUserCVReturnsUserCVWhenHasAllElementsOfCV() {
        User user = new User();
        when(userDAO.findById(1L)).thenReturn(user);
        Certification certification = new Certification();
        Education education = new Education();
        Project project = new Project();
        Skill skill = new Skill();
        WorkExperience workExperience = new WorkExperience();
        when(certificationDAO.findCertificationsByUserId(1L)).thenReturn(List.of(certification));
        when(educationDAO.findEducationsByUserId(1L)).thenReturn(List.of(education));
        when(projectDAO.findProjectsByUserId(1L)).thenReturn(List.of(project));
        when(skillDAO.findSkillsByUserId(1L)).thenReturn(List.of(skill));
        when(workExperienceDAO.findWorkExperiencesByUserId(1L)).thenReturn(List.of(workExperience));

        UserCVResponseDTO result = userService.getUserCV(1L);
        assertNotNull(result);
        verify(userDAO, times(1)).findById(1L);
    }

    @Test
    void getUserCVThrowsExceptionWhenUserNotFound() {
        when(userDAO.findById(1L)).thenReturn(null);
        AppException exception = assertThrows(AppException.class, () -> userService.getUserCV(1L));
        assertEquals("Error: User not found!", exception.getMessage());
        verify(userDAO, times(1)).findById(1L);
    }

    @Test
    void updatePasswordUpdatesPasswordWhenUserExistsAndCredentialsAreValid() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        when(userDAO.findById(1L)).thenReturn(user);
        when(passwordEncoder.matches(any(), anyString())).thenReturn(true);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        PasswordChangeRequestDTO passwordChangeRequestDTO = new PasswordChangeRequestDTO("test@example.com", "currentPassword", "newPassword");

        userService.updatePassword(1L, passwordChangeRequestDTO);

        verify(userDAO, times(1)).save(user);
        assertTrue(passwordEncoder.matches("encodedPassword", user.getPassword()));
    }

    @Test
    void updatePasswordThrowsExceptionWhenUserNotFound() {
        when(userDAO.findById(1L)).thenReturn(null);
        PasswordChangeRequestDTO passwordChangeRequestDTO = new PasswordChangeRequestDTO("test@example.com", "currentPassword", "newPassword");

        AppException exception = assertThrows(AppException.class, () -> userService.updatePassword(1L, passwordChangeRequestDTO));

        assertEquals("Error: User not found!", exception.getMessage());
        verify(userDAO, never()).save(any(User.class));
    }

    @Test
    void updatePasswordThrowsExceptionWhenEmailDoesNotMatch() {
        User user = new User();
        user.setEmail("different@example.com");
        when(userDAO.findById(1L)).thenReturn(user);
        PasswordChangeRequestDTO passwordChangeRequestDTO = new PasswordChangeRequestDTO("test@example.com", "currentPassword", "newPassword");

        AppException exception = assertThrows(AppException.class, () -> userService.updatePassword(1L, passwordChangeRequestDTO));

        assertEquals("Error: Email does not match!", exception.getMessage());
        verify(userDAO, never()).save(any(User.class));
    }

    @Test
    void updatePasswordThrowsExceptionWhenPasswordIsInvalid() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        when(userDAO.findById(1L)).thenReturn(user);
        when(passwordEncoder.matches("currentPassword", "encodedPassword")).thenReturn(false);
        PasswordChangeRequestDTO passwordChangeRequestDTO = new PasswordChangeRequestDTO("test@example.com", "currentPassword", "newPassword");

        AppException exception = assertThrows(AppException.class, () -> userService.updatePassword(1L, passwordChangeRequestDTO));

        assertEquals("Error: Invalid password!", exception.getMessage());
        verify(userDAO, never()).save(any(User.class));
    }

}