package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.request.WorkExperienceRequestDTO;
import com.alumniportal.unmsm.dto.response.WorkExperienceResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.WorkExperienceMapper;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.model.WorkExperience;
import com.alumniportal.unmsm.persistence.interfaces.IUserDAO;
import com.alumniportal.unmsm.persistence.interfaces.IWorkExperienceDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkExperienceServiceImplTest {

    @Mock
    private IWorkExperienceDAO workExperienceDAO;

    @Mock
    private IUserDAO userDAO;

    private WorkExperienceMapper workExperienceMapper;

    @InjectMocks
    private WorkExperienceServiceImpl workExperienceService;

    @BeforeEach
    void setUp() {
        workExperienceMapper = new WorkExperienceMapper(new ModelMapper());
        workExperienceService = new WorkExperienceServiceImpl(workExperienceDAO, userDAO, workExperienceMapper);
    }

    @Test
    void findAllReturnsListOfWorkExperienceResponseDTOs() {
        WorkExperience workExperience = new WorkExperience();
        when(workExperienceDAO.findAll()).thenReturn(List.of(workExperience));
        List<WorkExperienceResponseDTO> result = workExperienceService.findAll();
        assertEquals(1, result.size());
        verify(workExperienceDAO, times(1)).findAll();
    }

    @Test
    void findAllThrowsExceptionWhenNoWorkExperiencesFound() {
        when(workExperienceDAO.findAll()).thenReturn(Collections.emptyList());
        AppException exception = assertThrows(AppException.class, () -> workExperienceService.findAll());
        assertEquals("No work experiences found", exception.getMessage());
        verify(workExperienceDAO, times(1)).findAll();
    }

    @Test
    void findByIdReturnsWorkExperienceResponseDTO() {
        WorkExperience workExperience = new WorkExperience();
        when(workExperienceDAO.findById(1L)).thenReturn(workExperience);
        WorkExperienceResponseDTO result = workExperienceService.findById(1L);
        assertNotNull(result);
        verify(workExperienceDAO, times(1)).findById(1L);
    }

    @Test
    void findByIdThrowsExceptionWhenWorkExperienceNotFound() {
        when(workExperienceDAO.findById(1L)).thenReturn(null);
        AppException exception = assertThrows(AppException.class, () -> workExperienceService.findById(1L));
        assertEquals("Work experience not found", exception.getMessage());
        verify(workExperienceDAO, times(1)).findById(1L);
    }

    @Test
    void saveSavesWorkExperienceSuccessfully() {
        WorkExperience workExperience = new WorkExperience();
        workExperienceService.save(workExperience);
        verify(workExperienceDAO, times(1)).save(workExperience);
    }

    @Test
    void deleteByIdDeletesWorkExperienceWhenFound() {
        WorkExperience workExperience = new WorkExperience();
        when(workExperienceDAO.findById(1L)).thenReturn(workExperience);
        workExperienceService.deleteById(1L);
        verify(workExperienceDAO, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdThrowsExceptionWhenWorkExperienceNotFound() {
        when(workExperienceDAO.findById(1L)).thenReturn(null);
        AppException exception = assertThrows(AppException.class, () -> workExperienceService.deleteById(1L));
        assertEquals("Work experience not found", exception.getMessage());
        verify(workExperienceDAO, never()).deleteById(1L);
    }

    @Test
    void findWorkExperiencesByUserIdReturnsListOfWorkExperienceResponseDTOs() {
        WorkExperience workExperience = new WorkExperience();
        when(workExperienceDAO.findWorkExperiencesByUserId(1L)).thenReturn(List.of(workExperience));
        List<WorkExperienceResponseDTO> result = workExperienceService.findWorkExperiencesByUserId(1L);
        assertEquals(1, result.size());
        verify(workExperienceDAO, times(1)).findWorkExperiencesByUserId(1L);
    }

    @Test
    void findWorkExperiencesByUserIdThrowsExceptionWhenNoWorkExperiencesFound() {
        when(workExperienceDAO.findWorkExperiencesByUserId(1L)).thenReturn(Collections.emptyList());
        AppException exception = assertThrows(AppException.class, () -> workExperienceService.findWorkExperiencesByUserId(1L));
        assertEquals("No work experiences found for user", exception.getMessage());
        verify(workExperienceDAO, times(1)).findWorkExperiencesByUserId(1L);
    }

    @Test
    void saveWorkExperienceSavesWorkExperienceWhenUserExists() {
        WorkExperienceRequestDTO workExperienceRequestDTO = new WorkExperienceRequestDTO();
        User user = new User();
        when(userDAO.findById(1L)).thenReturn(user);
        workExperienceService.saveWorkExperience(workExperienceRequestDTO, 1L);
        verify(workExperienceDAO, times(1)).save(any(WorkExperience.class));
    }

    @Test
    void saveWorkExperienceThrowsExceptionWhenUserNotFound() {
        WorkExperienceRequestDTO workExperienceRequestDTO = new WorkExperienceRequestDTO();
        when(userDAO.findById(1L)).thenReturn(null);
        AppException exception = assertThrows(AppException.class, () -> workExperienceService.saveWorkExperience(workExperienceRequestDTO, 1L));
        assertEquals("User not found", exception.getMessage());
        verify(workExperienceDAO, never()).save(any(WorkExperience.class));
    }

    @Test
    void updateWorkExperienceUpdatesFieldsWhenWorkExperienceExists() {
        WorkExperience workExperience = new WorkExperience();
        when(workExperienceDAO.findById(1L)).thenReturn(workExperience);
        Map<String, Object> fields = Map.of("jobTitle", "Updated JobTitle");
        workExperienceService.updateWorkExperience(1L, fields);
        assertEquals("Updated JobTitle", workExperience.getJobTitle());
        verify(workExperienceDAO, times(1)).save(workExperience);
    }

    @Test
    void updateWorkExperienceThrowsExceptionWhenWorkExperienceNotFound() {
        when(workExperienceDAO.findById(1L)).thenReturn(null);
        Map<String, Object> fields = Map.of("jobTitle", "Updated JobTitle");
        AppException exception = assertThrows(AppException.class, () -> workExperienceService.updateWorkExperience(1L, fields));
        assertEquals("Work experience not found", exception.getMessage());
        verify(workExperienceDAO, never()).save(any(WorkExperience.class));
    }

    @Test
    void updateWorkExperienceWhenFieldIsStartDateAndEndDate() {
        WorkExperience workExperience = new WorkExperience();
        when(workExperienceDAO.findById(1L)).thenReturn(workExperience);
        Map<String, Object> fields = Map.of(
                "startDate", "2021-01-01",
                "endDate", "2021-01-01");
        workExperienceService.updateWorkExperience(1L, fields);
        assertEquals("2021-01-01", workExperience.getStartDate().toString());
        assertEquals("2021-01-01", workExperience.getEndDate().toString());
        verify(workExperienceDAO, times(1)).save(workExperience);
    }

}