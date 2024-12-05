package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.RequestDTO.ActivityRequestDTO;
import com.alumniportal.unmsm.dto.RequestDTO.EnrollmentRequestDTO;
import com.alumniportal.unmsm.dto.RequestDTO.UserRequestDTO;
import com.alumniportal.unmsm.dto.ResponseDTO.EnrollmentResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.EnrollmentMapper;
import com.alumniportal.unmsm.model.Activity;
import com.alumniportal.unmsm.model.Enrollment;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.interfaces.IActivityDAO;
import com.alumniportal.unmsm.persistence.interfaces.IEnrollmentDAO;
import com.alumniportal.unmsm.persistence.interfaces.IUserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import software.amazon.awssdk.services.lambda.LambdaClient;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EnrollmentServiceImplTest {

    @Mock
    private IEnrollmentDAO enrollmentDAO;

    @Mock
    private IUserDAO userDAO;

    @Mock
    private IActivityDAO activityDAO;


    private EnrollmentMapper enrollmentMapper;

    @Mock
    private LambdaClient lambdaClient;

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;


    @BeforeEach
    void setUp() {
        enrollmentMapper = new EnrollmentMapper(new ModelMapper());
        enrollmentService = new EnrollmentServiceImpl(enrollmentDAO, userDAO, activityDAO, enrollmentMapper, lambdaClient);
    }

    @Test
    void findAllThrowsExceptionWhenNoEnrollmentsFound() {
        when(enrollmentDAO.findAll()).thenReturn(Collections.emptyList());
        assertThrows(AppException.class, () -> enrollmentService.findAll());
        verify(enrollmentDAO, times(1)).findAll();
    }

    @Test
    void findAllReturnsListOfEnrollmentResponseDTOs() {
        Enrollment enrollment = new Enrollment();
        when(enrollmentDAO.findAll()).thenReturn(List.of(enrollment));
        List<EnrollmentResponseDTO> result = enrollmentService.findAll();
        assertEquals(1, result.size());
        verify(enrollmentDAO, times(1)).findAll();
    }

    @Test
    void findByIdThrowsExceptionWhenEnrollmentNotFound() {
        when(enrollmentDAO.findById(1L)).thenReturn(null);
        assertThrows(AppException.class, () -> enrollmentService.findById(1L));
        verify(enrollmentDAO, times(1)).findById(1L);
    }

    @Test
    void findByIdReturnsEnrollmentResponseDTO() {
        Enrollment enrollment = new Enrollment();
        when(enrollmentDAO.findById(1L)).thenReturn(enrollment);
        EnrollmentResponseDTO result = enrollmentService.findById(1L);
        assertNotNull(result);
        verify(enrollmentDAO, times(1)).findById(1L);
    }

    @Test
    void saveEnrollment() {
        Enrollment enrollment = new Enrollment();
        enrollmentService.save(enrollment);
        verify(enrollmentDAO, times(1)).save(enrollment);
    }

    @Test
    void deleteByIdDeletesEnrollmentWhenFound() {
        Enrollment enrollment = new Enrollment();
        when(enrollmentDAO.findById(1L)).thenReturn(enrollment);
        enrollmentService.deleteById(1L);
        verify(enrollmentDAO, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdThrowsExceptionWhenEnrollmentNotFound() {
        when(enrollmentDAO.findById(1L)).thenReturn(null);
        assertThrows(AppException.class, () -> enrollmentService.deleteById(1L));
        verify(enrollmentDAO, never()).deleteById(1L);
    }

    @Test
    void findEnrollmentsByUserIdReturnsListOfEnrollmentResponseDTOs() {
        Enrollment enrollment = new Enrollment();
        when(enrollmentDAO.findEnrollmentsByUserId(1L)).thenReturn(List.of(enrollment));
        List<EnrollmentResponseDTO> result = enrollmentService.findEnrollmentsByUserId(1L);
        assertEquals(1, result.size());
        verify(enrollmentDAO, times(1)).findEnrollmentsByUserId(1L);
    }

    @Test
    void findEnrollmentsByUserIdThrowsExceptionWhenNoEnrollmentsFound() {
        when(enrollmentDAO.findEnrollmentsByUserId(1L)).thenReturn(Collections.emptyList());
        assertThrows(AppException.class, () -> enrollmentService.findEnrollmentsByUserId(1L));
        verify(enrollmentDAO, times(1)).findEnrollmentsByUserId(1L);
    }

    @Test
    void findEnrollmentsByActivityIdReturnsListOfEnrollmentResponseDTOs() {
        Enrollment enrollment = new Enrollment();
        when(enrollmentDAO.findEnrollmentsByActivityId(1L)).thenReturn(List.of(enrollment));
        List<EnrollmentResponseDTO> result = enrollmentService.findEnrollmentsByActivityId(1L);
        assertEquals(1, result.size());
        verify(enrollmentDAO, times(1)).findEnrollmentsByActivityId(1L);
    }

    @Test
    void findEnrollmentsByActivityIdThrowsExceptionWhenNoEnrollmentsFound() {
        when(enrollmentDAO.findEnrollmentsByActivityId(1L)).thenReturn(Collections.emptyList());
        assertThrows(AppException.class, () -> enrollmentService.findEnrollmentsByActivityId(1L));
        verify(enrollmentDAO, times(1)).findEnrollmentsByActivityId(1L);
    }

    @Test
    void findEnrollmentByUserIdAndActivityIdReturnsEnrollmentResponseDTO() {
        Enrollment enrollment = new Enrollment();
        when(enrollmentDAO.findEnrollmentByUserIdAndActivityId(1L, 1L)).thenReturn(enrollment);
        EnrollmentResponseDTO result = enrollmentService.findEnrollmentByUserIdAndActivityId(1L, 1L);
        assertNotNull(result);
        verify(enrollmentDAO, times(1)).findEnrollmentByUserIdAndActivityId(1L, 1L);
    }

    @Test
    void findEnrollmentByUserIdAndActivityIdThrowsExceptionWhenEnrollmentNotFound() {
        when(enrollmentDAO.findEnrollmentByUserIdAndActivityId(1L, 1L)).thenReturn(null);
        AppException exception = assertThrows(AppException.class, () -> enrollmentService.findEnrollmentByUserIdAndActivityId(1L, 1L));
        assertEquals("No enrollment exists by userId: 1 and activityId: 1", exception.getMessage());
        verify(enrollmentDAO, times(1)).findEnrollmentByUserIdAndActivityId(1L, 1L);
    }

//    @Test
//    void saveEnrollmentSavesEnrollmentWhenValid() {
//        EnrollmentRequestDTO enrollmentRequestDTO = new EnrollmentRequestDTO();
//
//        User user = new User();
//        user.setId(1L);
//        Activity activity = new Activity();
//        activity.setId(1L);
//        activity.setEnrollable(true);
//        activity.setStartDate(LocalDate.parse("2021-10-10"));
//        activity.setEndDate(LocalDate.parse("2021-10-20"));
//        when(userDAO.findById(anyLong())).thenReturn(user);
//        when(activityDAO.findById(anyLong())).thenReturn(activity);
//        when(enrollmentDAO.findEnrollmentByUserIdAndActivityId(1L, 1L)).thenReturn(null);
//
//        UserRequestDTO userRequestDTO = new UserRequestDTO();
//        userRequestDTO.setId(1L);
//        enrollmentRequestDTO.setUser(userRequestDTO);
//
//        ActivityRequestDTO activityRequestDTO = new ActivityRequestDTO();
//        activityRequestDTO.setId(1L);
//        activityRequestDTO.setStartDate(LocalDate.parse("2021-10-10"));
//        activityRequestDTO.setEndDate(LocalDate.parse("2021-10-20"));
//        enrollmentRequestDTO.setActivity(activityRequestDTO);
//
//        doNothing().when(enrollmentService).invokeLambdaWhenEnrollmentIsCreated(anyString(), any(Enrollment.class));
//
//
//        enrollmentService.saveEnrollment(enrollmentRequestDTO);
//
//        verify(enrollmentDAO, times(1)).save(any(Enrollment.class));
//    }

    @Test
    void saveEnrollmentThrowsExceptionWhenUserNotFound() {

        EnrollmentRequestDTO enrollmentRequestDTO = new EnrollmentRequestDTO();
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setId(1L);
        enrollmentRequestDTO.setUser(userRequestDTO);

        when(userDAO.findById(anyLong())).thenReturn(null);
        assertThrows(AppException.class, () -> enrollmentService.saveEnrollment(enrollmentRequestDTO));
    }

    @Test
    void saveEnrollmentThrowsExceptionWhenActivityNotFound() {
        EnrollmentRequestDTO enrollmentRequestDTO = new EnrollmentRequestDTO();
        User user = new User();
        when(userDAO.findById(anyLong())).thenReturn(user);
        when(activityDAO.findById(anyLong())).thenReturn(null);

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setId(1L);
        enrollmentRequestDTO.setUser(userRequestDTO);

        ActivityRequestDTO activityRequestDTO = new ActivityRequestDTO();
        activityRequestDTO.setId(1L);
        enrollmentRequestDTO.setActivity(activityRequestDTO);

        assertThrows(AppException.class, () -> enrollmentService.saveEnrollment(enrollmentRequestDTO));
    }

    @Test
    void saveEnrollmentThrowsExceptionWhenActivityNotEnrollable() {
        EnrollmentRequestDTO enrollmentRequestDTO = new EnrollmentRequestDTO();
        User user = new User();
        Activity activity = new Activity();
        activity.setEnrollable(false);
        when(userDAO.findById(anyLong())).thenReturn(user);
        when(activityDAO.findById(anyLong())).thenReturn(activity);

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setId(1L);
        enrollmentRequestDTO.setUser(userRequestDTO);

        ActivityRequestDTO activityRequestDTO = new ActivityRequestDTO();
        activityRequestDTO.setId(1L);
        enrollmentRequestDTO.setActivity(activityRequestDTO);

        assertThrows(AppException.class, () -> enrollmentService.saveEnrollment(enrollmentRequestDTO));
    }

    @Test
    void saveEnrollmentThrowsExceptionWhenUserAlreadyEnrolled() {
        // Crear DTOs de entrada
        EnrollmentRequestDTO enrollmentRequestDTO = new EnrollmentRequestDTO();


        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setId(1L);
        enrollmentRequestDTO.setUser(userRequestDTO);

        ActivityRequestDTO activityRequestDTO = new ActivityRequestDTO();
        activityRequestDTO.setId(1L);
        enrollmentRequestDTO.setActivity(activityRequestDTO);

        // Crear objetos de dominio simulados
        User user = new User();
        user.setId(1L);

        Activity activity = new Activity();
        activity.setId(1L);
        activity.setEnrollable(true);

        Enrollment existingEnrollment = new Enrollment();
        existingEnrollment.setId(1L);
        existingEnrollment.setUser(user);
        existingEnrollment.setActivity(activity);

        // Configurar los mocks
        when(userDAO.findById(1L)).thenReturn(user);
        when(activityDAO.findById(1L)).thenReturn(activity);
        when(enrollmentDAO.findEnrollmentByUserIdAndActivityId(1L, 1L)).thenReturn(existingEnrollment);

        // Verificar que se lanza la excepción esperada
        AppException exception = assertThrows(AppException.class, () -> enrollmentService.saveEnrollment(enrollmentRequestDTO));

        // Validar el mensaje de excepción
        assertEquals("Error: User is already enrolled in this activity!", exception.getMessage());

        // Verificar que no se intentó guardar un nuevo registro
        verify(enrollmentDAO, never()).save(any(Enrollment.class));
    }


}