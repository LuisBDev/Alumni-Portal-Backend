package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.config.security.JwtService;
import com.alumniportal.unmsm.config.security.SecurityTestConfig;
import com.alumniportal.unmsm.dto.request.ActivityRequestDTO;
import com.alumniportal.unmsm.dto.response.ActivityResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.service.interfaces.IActivityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;


import java.util.List;
import java.util.Map;


@WebMvcTest(ActivityRestController.class)
@Import(SecurityTestConfig.class)
class ActivityRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IActivityService activityService;


    @Test
    void findAllReturnsListOfActivities() throws Exception {
        // Arrange: Configurar datos y comportamiento de los mocks
        ActivityResponseDTO activityResponseDTO = new ActivityResponseDTO();
        when(activityService.findAll()).thenReturn(List.of(activityResponseDTO));

        // Act & Assert: Simular la petición HTTP y verificar la respuesta
        mockMvc.perform(get("/api/activity/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        // Verificar interacciones con los mocks
        verify(activityService, times(1)).findAll();
    }

    @Test
    void findByIdReturnsActivity() throws Exception {
        // Arrange: Configurar datos y comportamiento de los mocks
        ActivityResponseDTO activityResponseDTO = new ActivityResponseDTO();
        when(activityService.findById(1L)).thenReturn(activityResponseDTO);

        // Act & Assert: Simular la petición HTTP y verificar la respuesta
        mockMvc.perform(get("/api/activity/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verificar interacciones con los mocks
        verify(activityService, times(1)).findById(1L);
    }

    @Test
    void saveActivityByUserIdReturnsCreatedActivity() throws Exception {
        ActivityRequestDTO activityRequestDTO = new ActivityRequestDTO();
        ActivityResponseDTO activityResponseDTO = new ActivityResponseDTO();
        when(activityService.saveActivityByUserId(any(ActivityRequestDTO.class), eq(1L))).thenReturn(activityResponseDTO);

        mockMvc.perform(post("/api/activity/save-user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(activityRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(activityResponseDTO)));
        verify(activityService, times(1)).saveActivityByUserId(any(ActivityRequestDTO.class), eq(1L));
    }

    @Test
    void saveActivityByCompanyIdReturnsCreatedActivity() throws Exception {
        ActivityRequestDTO activityRequestDTO = new ActivityRequestDTO();
        ActivityResponseDTO activityResponseDTO = new ActivityResponseDTO();
        when(activityService.saveActivityByCompanyId(any(ActivityRequestDTO.class), eq(1L))).thenReturn(activityResponseDTO);

        mockMvc.perform(post("/api/activity/save-company/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(activityRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(activityResponseDTO)));
        verify(activityService, times(1)).saveActivityByCompanyId(any(ActivityRequestDTO.class), eq(1L));
    }

    @Test
    void updateActivityReturnsUpdatedActivity() throws Exception {
        Map<String, Object> fields = Map.of("title", "Updated Title");
        ActivityResponseDTO activityResponseDTO = new ActivityResponseDTO();
        when(activityService.updateActivity(eq(1L), anyMap())).thenReturn(activityResponseDTO);

        mockMvc.perform(patch("/api/activity/update-activity/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fields)))
                .andExpect(status().isOk());
        verify(activityService, times(1)).updateActivity(eq(1L), anyMap());
    }

    @Test
    void deleteByIdReturnsNoContent() throws Exception {
        doNothing().when(activityService).deleteById(1L);

        mockMvc.perform(delete("/api/activity/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(activityService, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdThrowsExceptionWhenActivityNotFound() throws Exception {
        doThrow(new AppException("Activity not found", "NOT_FOUND"))
                .when(activityService).deleteById(1L);

        mockMvc.perform(delete("/api/activity/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(activityService, times(1)).deleteById(1L);
    }

    @Test
    void findActivitiesByUserIdReturnsListOfActivities() throws Exception {
        ActivityResponseDTO activityResponseDTO = new ActivityResponseDTO();
        when(activityService.findActivitiesByUserId(1L)).thenReturn(List.of(activityResponseDTO));

        mockMvc.perform(get("/api/activity/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(activityService, times(1)).findActivitiesByUserId(1L);
    }


    @Test
    void findActivitiesByCompanyIdReturnsListOfActivities() throws Exception {
        ActivityResponseDTO activityResponseDTO = new ActivityResponseDTO();
        when(activityService.findActivitiesByCompanyId(1L)).thenReturn(List.of(activityResponseDTO));

        mockMvc.perform(get("/api/activity/company/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(activityService, times(1)).findActivitiesByCompanyId(1L);
    }

    @Test
    void uploadActivityImageReturnsSuccess() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "image content".getBytes());
        doNothing().when(activityService).uploadActivityImage(eq(1L), any(MultipartFile.class));

        mockMvc.perform(multipart("/api/activity/activity-image/1")
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("File uploaded successfully"));

        verify(activityService, times(1)).uploadActivityImage(eq(1L), any(MultipartFile.class));
    }


    @Test
    void downloadActivityImageReturnsImage() throws Exception {
        byte[] fileData = "image content".getBytes();
        when(activityService.downloadActivityImage(1L)).thenReturn(fileData);
        when(activityService.getFileContentType(1L)).thenReturn(MediaType.IMAGE_JPEG_VALUE);
        when(activityService.getFileName(1L)).thenReturn("image.jpg");

        mockMvc.perform(get("/api/activity/activity-image/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "form-data; name=\"attachment\"; filename=\"image.jpg\""))
                .andExpect(content().bytes(fileData));

        verify(activityService, times(1)).downloadActivityImage(1L);
        verify(activityService, times(1)).getFileContentType(1L);
        verify(activityService, times(1)).getFileName(1L);
    }


    @Test
    void deleteActivityImageReturnsSuccess() throws Exception {
        doNothing().when(activityService).deleteActivityImage(1L);

        mockMvc.perform(delete("/api/activity/activity-image/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("File deleted successfully"));

        verify(activityService, times(1)).deleteActivityImage(1L);
    }


}