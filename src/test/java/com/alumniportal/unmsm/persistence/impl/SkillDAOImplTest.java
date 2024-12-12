package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.Skill;
import com.alumniportal.unmsm.repository.ISkillRepository;
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
class SkillDAOImplTest {

    @Mock
    private ISkillRepository skillRepository;

    @InjectMocks
    private SkillDAOImpl skillDAO;

    @Test
    void findAllReturnsListOfSkillsWhenSkillsExist() {
        List<Skill> skills = List.of(new Skill(), new Skill());
        when(skillRepository.findAll()).thenReturn(skills);

        List<Skill> result = skillDAO.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(skillRepository, times(1)).findAll();
    }

    @Test
    void findAllReturnsEmptyListWhenNoSkillsExist() {
        when(skillRepository.findAll()).thenReturn(List.of());

        List<Skill> result = skillDAO.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(skillRepository, times(1)).findAll();
    }

    @Test
    void findByIdReturnsSkillWhenSkillExists() {
        Skill skill = new Skill();
        skill.setId(1L);

        when(skillRepository.findById(1L)).thenReturn(Optional.of(skill));

        Skill result = skillDAO.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(skillRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdReturnsNullWhenSkillDoesNotExist() {
        when(skillRepository.findById(1L)).thenReturn(Optional.empty());

        Skill result = skillDAO.findById(1L);

        assertNull(result);
        verify(skillRepository, times(1)).findById(1L);
    }

    @Test
    void saveSavesSkillSuccessfullyWhenValidSkillProvided() {
        Skill skill = new Skill();
        skill.setId(1L);
        skill.setName("Test Skill");

        skillDAO.save(skill);

        verify(skillRepository, times(1)).save(skill);
    }


    @Test
    void deleteByIdDeletesSkillSuccessfullyWhenSkillExists() {
        doNothing().when(skillRepository).deleteById(1L);

        skillDAO.deleteById(1L);

        verify(skillRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdThrowsExceptionWhenSkillDoesNotExist() {
        doThrow(new RuntimeException("Skill not found")).when(skillRepository).deleteById(1L);

        assertThrows(RuntimeException.class, () -> skillDAO.deleteById(1L));
        verify(skillRepository, times(1)).deleteById(1L);
    }

    @Test
    void findSkillsByUserIdReturnsListOfSkillsWhenSkillsExistForUser() {
        List<Skill> skills = List.of(new Skill(), new Skill());
        when(skillRepository.findSkillsByUserId(1L)).thenReturn(skills);

        List<Skill> result = skillDAO.findSkillsByUserId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(skillRepository, times(1)).findSkillsByUserId(1L);
    }

    @Test
    void findSkillsByUserIdReturnsEmptyListWhenNoSkillsExistForUser() {
        when(skillRepository.findSkillsByUserId(1L)).thenReturn(List.of());

        List<Skill> result = skillDAO.findSkillsByUserId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(skillRepository, times(1)).findSkillsByUserId(1L);
    }


}