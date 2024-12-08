package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.config.security.JwtService;
import com.alumniportal.unmsm.config.security.SecurityTestConfig;
import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.interfaces.ICompanyDAO;
import com.alumniportal.unmsm.persistence.interfaces.IUserDAO;
import com.alumniportal.unmsm.util.ImageManagement;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(ImageRestController.class)
@Import(SecurityTestConfig.class)
class ImageRestControllerTest {

    @MockBean
    private ImageManagement<User> userImageManagement;

    @MockBean
    private ImageManagement<Company> companyImageManagement;

    @MockBean
    private ICompanyDAO companyDAO;

    @MockBean
    private IUserDAO userDAO;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void uploadUserImageReturnsOkWhenFileUploadedSuccessfully() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        User user = new User();
        when(userDAO.findById(1L)).thenReturn(user);
        when(userImageManagement.uploadOrUpdateImage(any(User.class), any(MultipartFile.class))).thenReturn("path/to/image");

        mockMvc.perform(multipart("/api/image/upload-user/1")
                        .file("image", file.getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().string("File uploaded successfully: path/to/image"));

        verify(userDAO, times(1)).findById(1L);
        verify(userImageManagement, times(1)).uploadOrUpdateImage(any(User.class), any(MultipartFile.class));
    }

    @Test
    void uploadUserImageReturnsBadRequestWhenIOExceptionOccurs() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        User user = new User();
        when(userDAO.findById(1L)).thenReturn(user);
        when(userImageManagement.uploadOrUpdateImage(any(User.class), any(MultipartFile.class))).thenThrow(new IOException("Error uploading file"));

        mockMvc.perform(multipart("/api/image/upload-user/1")
                        .file("image", file.getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error uploading file"));

        verify(userDAO, times(1)).findById(1L);
        verify(userImageManagement, times(1)).uploadOrUpdateImage(any(User.class), any(MultipartFile.class));
    }

    @Test
    void uploadCompanyImageReturnsOkWhenFileUploadedSuccessfully() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        Company company = new Company();
        when(companyDAO.findById(1L)).thenReturn(company);
        when(companyImageManagement.uploadOrUpdateImage(any(Company.class), any(MultipartFile.class))).thenReturn("path/to/image");

        mockMvc.perform(multipart("/api/image/upload-company/1")
                        .file("image", file.getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().string("File uploaded successfully: path/to/image"));

        verify(companyDAO, times(1)).findById(1L);
        verify(companyImageManagement, times(1)).uploadOrUpdateImage(any(Company.class), any(MultipartFile.class));
    }

    @Test
    void uploadCompanyImageReturnsBadRequestWhenIOExceptionOccurs() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        Company company = new Company();
        when(companyDAO.findById(1L)).thenReturn(company);
        when(companyImageManagement.uploadOrUpdateImage(any(Company.class), any(MultipartFile.class))).thenThrow(new IOException("Error uploading file"));

        mockMvc.perform(multipart("/api/image/upload-company/1")
                        .file("image", file.getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error uploading file"));

        verify(companyDAO, times(1)).findById(1L);
        verify(companyImageManagement, times(1)).uploadOrUpdateImage(any(Company.class), any(MultipartFile.class));
    }


    @Test
    void downloadUserImageReturnsImageWhenUserExists() throws Exception {
        User user = new User();
        byte[] imageData = new byte[]{1, 2, 3};
        when(userDAO.findById(1L)).thenReturn(user);
        when(userImageManagement.downloadImageFromFileSystem(user)).thenReturn(imageData);

        mockMvc.perform(get("/api/image/download-user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("image/png"))
                .andExpect(content().bytes(imageData));

        verify(userDAO, times(1)).findById(1L);
        verify(userImageManagement, times(1)).downloadImageFromFileSystem(user);
    }

    @Test
    void downloadUserImageReturnsBadRequestWhenRuntimeExceptionOccurs() throws Exception {
        User user = new User();
        when(userDAO.findById(1L)).thenReturn(user);
        when(userImageManagement.downloadImageFromFileSystem(user)).thenThrow(new RuntimeException("Error downloading image"));

        mockMvc.perform(get("/api/image/download-user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error downloading image"));

        verify(userDAO, times(1)).findById(1L);
        verify(userImageManagement, times(1)).downloadImageFromFileSystem(user);
    }


    @Test
    void downloadCompanyImageReturnsImageWhenCompanyExists() throws Exception {
        Company company = new Company();
        byte[] imageData = new byte[]{1, 2, 3};
        when(companyDAO.findById(1L)).thenReturn(company);
        when(companyImageManagement.downloadImageFromFileSystem(company)).thenReturn(imageData);

        mockMvc.perform(get("/api/image/download-company/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("image/png"))
                .andExpect(content().bytes(imageData));

        verify(companyDAO, times(1)).findById(1L);
        verify(companyImageManagement, times(1)).downloadImageFromFileSystem(company);
    }

    @Test
    void downloadCompanyImageReturnsBadRequestWhenRuntimeExceptionOccurs() throws Exception {
        Company company = new Company();
        when(companyDAO.findById(1L)).thenReturn(company);
        when(companyImageManagement.downloadImageFromFileSystem(company)).thenThrow(new RuntimeException("Error downloading image"));

        mockMvc.perform(get("/api/image/download-company/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error downloading image"));

        verify(companyDAO, times(1)).findById(1L);
        verify(companyImageManagement, times(1)).downloadImageFromFileSystem(company);
    }

    @Test
    void deleteUserImageReturnsOkWhenImageDeletedSuccessfully() throws Exception {
        User user = new User();
        when(userDAO.findById(1L)).thenReturn(user);
        doNothing().when(userImageManagement).deleteImage(user);

        mockMvc.perform(delete("/api/image/delete-image-user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Image deleted successfully!"));

        verify(userDAO, times(1)).findById(1L);
        verify(userImageManagement, times(1)).deleteImage(user);
    }


    @Test
    void deleteUserImageReturnsBadRequestWhenRuntimeExceptionOccurs() throws Exception {
        User user = new User();
        when(userDAO.findById(1L)).thenReturn(user);
        doThrow(new RuntimeException("Error deleting image")).when(userImageManagement).deleteImage(user);

        mockMvc.perform(delete("/api/image/delete-image-user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error deleting image"));

        verify(userDAO, times(1)).findById(1L);
        verify(userImageManagement, times(1)).deleteImage(user);
    }

    @Test
    void deleteCompanyImageReturnsOkWhenImageDeletedSuccessfully() throws Exception {
        Company company = new Company();
        when(companyDAO.findById(1L)).thenReturn(company);
        doNothing().when(companyImageManagement).deleteImage(company);

        mockMvc.perform(delete("/api/image/delete-image-company/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Image deleted successfully!"));

        verify(companyDAO, times(1)).findById(1L);
        verify(companyImageManagement, times(1)).deleteImage(company);
    }

    @Test
    void deleteCompanyImageReturnsBadRequestWhenRuntimeExceptionOccurs() throws Exception {
        Company company = new Company();
        when(companyDAO.findById(1L)).thenReturn(company);
        doThrow(new RuntimeException("Error deleting image")).when(companyImageManagement).deleteImage(company);

        mockMvc.perform(delete("/api/image/delete-image-company/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error deleting image"));

        verify(companyDAO, times(1)).findById(1L);
        verify(companyImageManagement, times(1)).deleteImage(company);
    }


}