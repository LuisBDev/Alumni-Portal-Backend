package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.Enrollment;
import com.alumniportal.unmsm.repository.IEnrollmentRepository;
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
class EnrollmentDAOImplTest {

    @Mock
    private IEnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentDAOImpl enrollmentDAO;

    @Test
    void findAllReturnsListOfEnrollmentsWhenEnrollmentsExist() {
        List<Enrollment> enrollments = List.of(new Enrollment(), new Enrollment());
        when(enrollmentRepository.findAll()).thenReturn(enrollments);

        List<Enrollment> result = enrollmentDAO.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(enrollmentRepository, times(1)).findAll();
    }

    @Test
    void findAllReturnsEmptyListWhenNoEnrollmentsExist() {
        when(enrollmentRepository.findAll()).thenReturn(List.of());

        List<Enrollment> result = enrollmentDAO.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(enrollmentRepository, times(1)).findAll();
    }

    @Test
    void findByIdReturnsEnrollmentWhenEnrollmentExists() {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);

        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));

        Enrollment result = enrollmentDAO.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(enrollmentRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdReturnsNullWhenEnrollmentDoesNotExist() {
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.empty());

        Enrollment result = enrollmentDAO.findById(1L);

        assertNull(result);
        verify(enrollmentRepository, times(1)).findById(1L);
    }

    @Test
    void saveSavesEnrollmentSuccessfullyWhenValidEnrollmentProvided() {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);

        enrollmentDAO.save(enrollment);

        verify(enrollmentRepository, times(1)).save(enrollment);
    }


    @Test
    void deleteByIdDeletesEnrollmentSuccessfullyWhenEnrollmentExists() {
        doNothing().when(enrollmentRepository).deleteById(1L);

        enrollmentDAO.deleteById(1L);

        verify(enrollmentRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdThrowsExceptionWhenEnrollmentDoesNotExist() {
        doThrow(new RuntimeException("Enrollment not found")).when(enrollmentRepository).deleteById(1L);

        assertThrows(RuntimeException.class, () -> enrollmentDAO.deleteById(1L));
        verify(enrollmentRepository, times(1)).deleteById(1L);
    }

    @Test
    void findEnrollmentsByUserIdReturnsListOfEnrollmentsWhenEnrollmentsExistForUser() {
        List<Enrollment> enrollments = List.of(new Enrollment(), new Enrollment());
        when(enrollmentRepository.findEnrollmentsByUserId(1L)).thenReturn(enrollments);

        List<Enrollment> result = enrollmentDAO.findEnrollmentsByUserId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(enrollmentRepository, times(1)).findEnrollmentsByUserId(1L);
    }

    @Test
    void findEnrollmentsByUserIdReturnsEmptyListWhenNoEnrollmentsExistForUser() {
        when(enrollmentRepository.findEnrollmentsByUserId(1L)).thenReturn(List.of());

        List<Enrollment> result = enrollmentDAO.findEnrollmentsByUserId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(enrollmentRepository, times(1)).findEnrollmentsByUserId(1L);
    }

    @Test
    void findEnrollmentsByActivityIdReturnsListOfEnrollmentsWhenEnrollmentsExistForActivity() {
        List<Enrollment> enrollments = List.of(new Enrollment(), new Enrollment());
        when(enrollmentRepository.findEnrollmentsByActivityId(1L)).thenReturn(enrollments);

        List<Enrollment> result = enrollmentDAO.findEnrollmentsByActivityId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(enrollmentRepository, times(1)).findEnrollmentsByActivityId(1L);
    }

    @Test
    void findEnrollmentsByActivityIdReturnsEmptyListWhenNoEnrollmentsExistForActivity() {
        when(enrollmentRepository.findEnrollmentsByActivityId(1L)).thenReturn(List.of());

        List<Enrollment> result = enrollmentDAO.findEnrollmentsByActivityId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(enrollmentRepository, times(1)).findEnrollmentsByActivityId(1L);
    }

    @Test
    void findEnrollmentByUserIdAndActivityIdReturnsEnrollmentWhenEnrollmentExistsForUserAndActivity() {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);

        when(enrollmentRepository.findEnrollmentByUserIdAndActivityId(1L, 1L)).thenReturn(enrollment);

        Enrollment result = enrollmentDAO.findEnrollmentByUserIdAndActivityId(1L, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(enrollmentRepository, times(1)).findEnrollmentByUserIdAndActivityId(1L, 1L);
    }

    @Test
    void findEnrollmentByUserIdAndActivityIdReturnsNullWhenNoEnrollmentExistsForUserAndActivity() {
        when(enrollmentRepository.findEnrollmentByUserIdAndActivityId(1L, 1L)).thenReturn(null);

        Enrollment result = enrollmentDAO.findEnrollmentByUserIdAndActivityId(1L, 1L);

        assertNull(result);
        verify(enrollmentRepository, times(1)).findEnrollmentByUserIdAndActivityId(1L, 1L);
    }


}