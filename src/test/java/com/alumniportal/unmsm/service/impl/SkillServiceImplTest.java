package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.request.SkillRequestDTO;
import com.alumniportal.unmsm.dto.response.SkillResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.SkillMapper;
import com.alumniportal.unmsm.model.Skill;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.interfaces.ISkillDAO;
import com.alumniportal.unmsm.persistence.interfaces.IUserDAO;
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
class SkillServiceImplTest {

    @Mock
    private ISkillDAO skillDAO;

    @Mock
    private IUserDAO userDAO;

    private SkillMapper skillMapper;

    @InjectMocks
    private SkillServiceImpl skillService;

    @BeforeEach
    void setUp() {
        skillMapper = new SkillMapper(new ModelMapper());
        skillService = new SkillServiceImpl(skillDAO, userDAO, skillMapper);
    }

    @Test
    void findAllReturnsListOfSkillResponseDTOs() {
        Skill skill = new Skill();
        when(skillDAO.findAll()).thenReturn(List.of(skill));
        List<SkillResponseDTO> result = skillService.findAll();
        assertEquals(1, result.size());
        verify(skillDAO, times(1)).findAll();
    }

    @Test
    void findAllThrowsExceptionWhenNoSkillsFound() {
        when(skillDAO.findAll()).thenReturn(Collections.emptyList());
        AppException exception = assertThrows(AppException.class, () -> skillService.findAll());
        assertEquals("Skills not found", exception.getMessage());
        verify(skillDAO, times(1)).findAll();
    }

    @Test
    void findByIdReturnsSkillResponseDTO() {
        Skill skill = new Skill();
        when(skillDAO.findById(1L)).thenReturn(skill);
        SkillResponseDTO result = skillService.findById(1L);
        assertNotNull(result);
        verify(skillDAO, times(1)).findById(1L);
    }

    @Test
    void findByIdThrowsExceptionWhenSkillNotFound() {
        when(skillDAO.findById(1L)).thenReturn(null);
        AppException exception = assertThrows(AppException.class, () -> skillService.findById(1L));
        assertEquals("Skill not found", exception.getMessage());
        verify(skillDAO, times(1)).findById(1L);
    }

    @Test
    void saveSavesSkillSuccessfully() {
        Skill skill = new Skill();
        skillService.save(skill);
        verify(skillDAO, times(1)).save(skill);
    }

    @Test
    void deleteByIdDeletesSkillWhenFound() {
        Skill skill = new Skill();
        when(skillDAO.findById(1L)).thenReturn(skill);
        skillService.deleteById(1L);
        verify(skillDAO, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdThrowsExceptionWhenSkillNotFound() {
        when(skillDAO.findById(1L)).thenReturn(null);
        AppException exception = assertThrows(AppException.class, () -> skillService.deleteById(1L));
        assertEquals("Skill not found", exception.getMessage());
        verify(skillDAO, never()).deleteById(1L);
    }

    @Test
    void findSkillsByUserIdReturnsListOfSkillResponseDTOs() {
        Skill skill = new Skill();
        when(skillDAO.findSkillsByUserId(1L)).thenReturn(List.of(skill));
        List<SkillResponseDTO> result = skillService.findSkillsByUserId(1L);
        assertEquals(1, result.size());
        verify(skillDAO, times(1)).findSkillsByUserId(1L);
    }

    @Test
    void findSkillsByUserIdThrowsExceptionWhenNoSkillsFound() {
        when(skillDAO.findSkillsByUserId(1L)).thenReturn(Collections.emptyList());
        AppException exception = assertThrows(AppException.class, () -> skillService.findSkillsByUserId(1L));
        assertEquals("Skills not found", exception.getMessage());
        verify(skillDAO, times(1)).findSkillsByUserId(1L);
    }

    @Test
    void saveSkillSavesSkillWhenUserExists() {
        SkillRequestDTO skillRequestDTO = new SkillRequestDTO();
        User user = new User();
        when(userDAO.findById(1L)).thenReturn(user);
        skillService.saveSkill(skillRequestDTO, 1L);
        verify(skillDAO, times(1)).save(any(Skill.class));
    }

    @Test
    void saveSkillThrowsExceptionWhenUserNotFound() {
        SkillRequestDTO skillRequestDTO = new SkillRequestDTO();
        when(userDAO.findById(1L)).thenReturn(null);
        AppException exception = assertThrows(AppException.class, () -> skillService.saveSkill(skillRequestDTO, 1L));
        assertEquals("User not found", exception.getMessage());
        verify(skillDAO, never()).save(any(Skill.class));
    }

    @Test
    void updateSkillUpdatesFieldsWhenSkillExists() {
        Skill skill = new Skill();
        when(skillDAO.findById(1L)).thenReturn(skill);
        Map<String, Object> fields = Map.of("name", "Updated Name");
        skillService.updateSkill(1L, fields);
        assertEquals("Updated Name", skill.getName());
        verify(skillDAO, times(1)).save(skill);
    }

    @Test
    void updateSkillThrowsExceptionWhenSkillNotFound() {
        when(skillDAO.findById(1L)).thenReturn(null);
        Map<String, Object> fields = Map.of("name", "Updated Name");
        AppException exception = assertThrows(AppException.class, () -> skillService.updateSkill(1L, fields));
        assertEquals("Skill not found", exception.getMessage());
        verify(skillDAO, never()).save(any(Skill.class));
    }


}