package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.config.security.JwtService;
import com.alumniportal.unmsm.config.security.SecurityTestConfig;
import com.alumniportal.unmsm.dto.request.SkillRequestDTO;
import com.alumniportal.unmsm.dto.response.SkillResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.service.interfaces.ISkillService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(SkillRestController.class)
@Import(SecurityTestConfig.class)
class SkillRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ISkillService skillService;

    @Test
    void findAllReturnsListOfSkills() throws Exception {
        SkillResponseDTO skillResponseDTO = new SkillResponseDTO();
        when(skillService.findAll()).thenReturn(List.of(skillResponseDTO));

        mockMvc.perform(get("/api/skill/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(skillService, times(1)).findAll();
    }

    @Test
    void findAllReturnsNotFoundWhenNoSkills() throws Exception {
        doThrow(new AppException("No skills found", "NOT_FOUND")).when(skillService).findAll();

        mockMvc.perform(get("/api/skill/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(skillService, times(1)).findAll();
    }

    @Test
    void findByIdReturnsSkill() throws Exception {
        SkillResponseDTO skillResponseDTO = new SkillResponseDTO();
        when(skillService.findById(1L)).thenReturn(skillResponseDTO);

        mockMvc.perform(get("/api/skill/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(skillResponseDTO)));

        verify(skillService, times(1)).findById(1L);
    }

    @Test
    void findByIdReturnsNotFoundWhenSkillDoesNotExist() throws Exception {
        doThrow(new AppException("Skill not found", "NOT_FOUND")).when(skillService).findById(1L);

        mockMvc.perform(get("/api/skill/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(skillService, times(1)).findById(1L);
    }

    @Test
    void saveReturnsCreatedSkill() throws Exception {
        SkillRequestDTO skillRequestDTO = new SkillRequestDTO();
        SkillResponseDTO skillResponseDTO = new SkillResponseDTO();
        when(skillService.saveSkill(any(SkillRequestDTO.class), eq(1L))).thenReturn(skillResponseDTO);

        mockMvc.perform(post("/api/skill/save/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(skillRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(skillResponseDTO)));

        verify(skillService, times(1)).saveSkill(any(SkillRequestDTO.class), eq(1L));
    }

    @Test
    void updateReturnsUpdatedSkill() throws Exception {
        SkillResponseDTO skillResponseDTO = new SkillResponseDTO();
        Map<String, Object> updates = Map.of("field", "value");
        when(skillService.updateSkill(eq(1L), anyMap())).thenReturn(skillResponseDTO);

        mockMvc.perform(patch("/api/skill/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updates)))
                .andExpect(status().isOk());

        verify(skillService, times(1)).updateSkill(eq(1L), anyMap());
    }

    @Test
    void deleteByIdReturnsNoContent() throws Exception {
        doNothing().when(skillService).deleteById(1L);

        mockMvc.perform(delete("/api/skill/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(skillService, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdReturnsNotFoundWhenSkillDoesNotExist() throws Exception {
        doThrow(new AppException("No skills found", "NOT_FOUND")).when(skillService).deleteById(1L);

        mockMvc.perform(delete("/api/skill/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(skillService, times(1)).deleteById(1L);
    }

    @Test
    void findSkillsByUserIdReturnsListOfSkills() throws Exception {
        SkillResponseDTO skillResponseDTO = new SkillResponseDTO();
        when(skillService.findSkillsByUserId(1L)).thenReturn(List.of(skillResponseDTO));

        mockMvc.perform(get("/api/skill/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(skillService, times(1)).findSkillsByUserId(1L);
    }

    @Test
    void findSkillsByUserIdReturnsNotFoundWhenNoSkills() throws Exception {
        doThrow(new AppException("No skills found", "NOT_FOUND")).when(skillService).findSkillsByUserId(1L);

        mockMvc.perform(get("/api/skill/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(skillService, times(1)).findSkillsByUserId(1L);
    }


}