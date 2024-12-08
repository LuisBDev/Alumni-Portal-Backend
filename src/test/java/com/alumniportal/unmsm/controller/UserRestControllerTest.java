package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.config.security.JwtService;
import com.alumniportal.unmsm.config.security.SecurityTestConfig;
import com.alumniportal.unmsm.dto.request.PasswordChangeRequestDTO;
import com.alumniportal.unmsm.dto.response.*;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.UserMapper;
import com.alumniportal.unmsm.persistence.interfaces.*;
import com.alumniportal.unmsm.service.interfaces.IActivityService;
import com.alumniportal.unmsm.service.interfaces.IUserService;
import com.alumniportal.unmsm.util.CVGenerator;
import com.alumniportal.unmsm.util.ImageManagement;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(UserRestController.class)
@Import(SecurityTestConfig.class)
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IUserService userService;

    @MockBean
    private CVGenerator CVGenerator;


    @Test
    void findAllReturnsListOfUsers() throws Exception {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        when(userService.findAll()).thenReturn(List.of(userResponseDTO));

        mockMvc.perform(get("/api/user/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(userService, times(1)).findAll();
    }

    @Test
    void findAllReturnsEmptyListWhenNoUsers() throws Exception {
        when(userService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/user/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(userService, times(1)).findAll();
    }

    @Test
    void findByIdReturnsUser() throws Exception {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        when(userService.findById(1L)).thenReturn(userResponseDTO);

        mockMvc.perform(get("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userResponseDTO)));

        verify(userService, times(1)).findById(1L);
    }

    @Test
    void findByIdReturnsNotFoundWhenUserDoesNotExist() throws Exception {
        doThrow(new AppException("User not found", "NOT_FOUND")).when(userService).findById(1L);

        mockMvc.perform(get("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).findById(1L);
    }

    @Test
    void deleteByIdReturnsNoContent() throws Exception {
        doNothing().when(userService).deleteById(1L);

        mockMvc.perform(delete("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdReturnsNotFoundWhenUserDoesNotExist() throws Exception {
        doThrow(new AppException("User not found", "NOT_FOUND")).when(userService).deleteById(1L);

        mockMvc.perform(delete("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).deleteById(1L);
    }

    @Test
    void updateReturnsUpdatedUser() throws Exception {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        Map<String, Object> updates = Map.of("field", "value");
        when(userService.updateUser(eq(1L), anyMap())).thenReturn(userResponseDTO);

        mockMvc.perform(patch("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updates)))
                .andExpect(status().isOk());

        verify(userService, times(1)).updateUser(eq(1L), anyMap());
    }

    @Test
    void downloadUserCVReturnsPdf() throws Exception {
        UserCVResponseDTO cv = new UserCVResponseDTO();
        cv.setAbout("About");
        cv.setEmail("email");
        CertificationResponseDTO certificationResponseDTO = new CertificationResponseDTO();
        certificationResponseDTO.setName("Certification");
        cv.setCertifications(List.of(certificationResponseDTO));
        WorkExperienceResponseDTO workExperienceResponseDTO = new WorkExperienceResponseDTO();
        workExperienceResponseDTO.setCompany("Company");
        workExperienceResponseDTO.setDescription("Description");
        cv.setWorkExperience(List.of(workExperienceResponseDTO));

        SkillResponseDTO skillResponseDTO = new SkillResponseDTO();
        skillResponseDTO.setName("Skill");
        skillResponseDTO.setLevel("Level");
        cv.setSkills(List.of(skillResponseDTO));

        ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO();
        projectResponseDTO.setName("Project");
        projectResponseDTO.setDescription("Description");

        cv.setProjects(List.of(projectResponseDTO));

        EducationResponseDTO educationResponseDTO = new EducationResponseDTO();
        educationResponseDTO.setDegree("Degree");
        educationResponseDTO.setDescription("Description");
        educationResponseDTO.setInstitution("Institution");
        cv.setEducation(List.of(educationResponseDTO));


        byte[] pdfContent = new byte[]{1, 2, 3};
        when(userService.getUserCV(1L)).thenReturn(cv);

        when(CVGenerator.generateCV(cv)).thenReturn(pdfContent);

        mockMvc.perform(get("/api/user/cv/download/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(content().bytes(pdfContent));

        verify(userService, times(1)).getUserCV(1L);
    }

    @Test
    void updatePasswordReturnsNoContent() throws Exception {
        PasswordChangeRequestDTO passwordChangeRequestDTO = new PasswordChangeRequestDTO();
        passwordChangeRequestDTO.setPassword("password");
        passwordChangeRequestDTO.setNewPassword("newPassword");
        doNothing().when(userService).updatePassword(1L, passwordChangeRequestDTO);

        mockMvc.perform(post("/api/user/updatePassword/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordChangeRequestDTO)))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).updatePassword(1L, passwordChangeRequestDTO);
    }


}