package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.Data.CompanyProvider;
import com.alumniportal.unmsm.dto.ResponseDTO.CompanyResponseDTO;
import com.alumniportal.unmsm.dto.RequestDTO.PasswordChangeRequestDTO;
import com.alumniportal.unmsm.model.Activity;
import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.persistence.interfaces.ICompanyDAO;
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

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock
    private ICompanyDAO companyDAO;

    @Mock
    private ImageManagement imageManagement;

    @Mock
    private IActivityService activityService;

    @Mock
    private PasswordEncoder passwordEncoder;

    //   Real modelMapper
    private ModelMapper modelMapper;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @BeforeEach
    void setUp() {

        modelMapper = new ModelMapper();
        companyService = new CompanyServiceImpl(companyDAO, modelMapper, imageManagement, activityService, passwordEncoder);
    }

    @Test
    void findAll_ReturnsListOfCompanyDTOs_WhenCompaniesExist() {
        Company company1 = new Company();
        Company company2 = new Company();
        when(companyDAO.findAll()).thenReturn(List.of(company1, company2));

        List<CompanyResponseDTO> result = companyService.findAll();

        assertEquals(2, result.size());
        verify(companyDAO, times(1)).findAll();
    }

    @Test
    void findAll_ReturnsEmptyList_WhenNoCompaniesExist() {
        when(companyDAO.findAll()).thenReturn(List.of());

        List<CompanyResponseDTO> result = companyService.findAll();

        assertTrue(result.isEmpty());
        verify(companyDAO, times(1)).findAll();
    }

    @Test
    void findById_ReturnsCompanyDTO_WhenCompanyExists() {
        Company company = new Company();
        company.setId(1L);
        when(companyDAO.findById(1L)).thenReturn(company);

        CompanyResponseDTO result = companyService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(companyDAO, times(1)).findById(1L);
    }

    @Test
    void findById_ReturnsNull_WhenCompanyDoesNotExist() {
        when(companyDAO.findById(1L)).thenReturn(null);

        CompanyResponseDTO result = companyService.findById(1L);

        assertNull(result);
        verify(companyDAO, times(1)).findById(1L);
    }

    @Test
    void save_SavesCompanySuccessfully() {
        Company company = new Company();
        company.setName("Tech Company");

        companyService.save(company);

        verify(companyDAO, times(1)).save(company);
    }

    @Test
    void existsByEmail_ReturnsTrue_WhenEmailExists() {
        when(companyDAO.existsByEmail("test@example.com")).thenReturn(true);

        boolean result = companyService.existsByEmail("test@example.com");

        assertTrue(result);
        verify(companyDAO, times(1)).existsByEmail("test@example.com");
    }

    @Test
    void existsByEmail_ReturnsFalse_WhenEmailDoesNotExist() {
        when(companyDAO.existsByEmail("test@example.com")).thenReturn(false);

        boolean result = companyService.existsByEmail("test@example.com");

        assertFalse(result);
        verify(companyDAO, times(1)).existsByEmail("test@example.com");
    }

    @Test
    void findByEmail_ReturnsCompanyDTO_WhenEmailExists() {
        Company company = new Company();
        company.setEmail("test@example.com");
        when(companyDAO.findByEmail("test@example.com")).thenReturn(company);

        CompanyResponseDTO result = companyService.findByEmail("test@example.com");

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(companyDAO, times(1)).findByEmail("test@example.com");
    }

    @Test
    void findByEmail_ReturnsNull_WhenEmailDoesNotExist() {
        when(companyDAO.findByEmail("test@example.com")).thenReturn(null);

        CompanyResponseDTO result = companyService.findByEmail("test@example.com");

        assertNull(result);
        verify(companyDAO, times(1)).findByEmail("test@example.com");
    }

    @Test
    void saveCompany_SavesCompanySuccessfully_WhenEmailIsNotRegistered() {
        Company company = new Company();
        company.setEmail("new@example.com");

        when(companyDAO.existsByEmail("new@example.com")).thenReturn(false);

        doNothing().when(companyDAO).save(company);

        companyService.saveCompany(company);

        assertNotNull(company.getCreatedAt());
        verify(companyDAO, times(1)).save(company);
    }

    @Test
    void saveCompany_ThrowsException_WhenEmailAlreadyExists() {
        Company company = new Company();
        company.setEmail("existing@example.com");

        when(companyDAO.existsByEmail("existing@example.com")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> companyService.saveCompany(company));
        verify(companyDAO, never()).save(any(Company.class));
    }

    @Test
    void updateCompany_UpdatesFieldsSuccessfully_WhenCompanyExists() {
        Company company = CompanyProvider.companyOne();
        Map<String, Object> fields = Map.of("name", "Updated Company", "email", "updated@example.com");

        when(companyDAO.findById(anyLong())).thenReturn(company);

        companyService.updateCompany(1L, fields);

        assertEquals("Updated Company", company.getName());
        assertEquals("updated@example.com", company.getEmail());
        assertNotNull(company.getUpdatedAt());
        verify(companyDAO, times(1)).save(any(Company.class));
    }

    @Test
    void updateCompany_ThrowsException_WhenCompanyDoesNotExist() {
        Map<String, Object> fields = Map.of("name", "Updated Company");

        when(companyDAO.findById(anyLong())).thenReturn(null);

        assertThrows(RuntimeException.class, () -> companyService.updateCompany(1L, fields));
        verify(companyDAO, never()).save(any(Company.class));
    }

    @Test
    void updateCompany_SetsFieldToNull_WhenEmptyStringProvided() {
        Company company = new Company();
        company.setId(1L);
        Map<String, Object> fields = Map.of("sector", "");

        when(companyDAO.findById(anyLong())).thenReturn(company);

        companyService.updateCompany(1L, fields);

        assertNull(company.getSector());
        assertNotNull(company.getUpdatedAt());
        verify(companyDAO, times(1)).save(company);
    }

    @Test
    void updatePassword_UpdatesPasswordSuccessfully_WhenValidDataProvided() {
        Company company = Company.builder()
                .id(1L)
                .email("test@example.com")
                .password("encodedOldPassword")
                .build();


        PasswordChangeRequestDTO passwordChangeRequestDTO = PasswordChangeRequestDTO.builder()
                .email("test@example.com")
                .password("oldPassword")
                .newPassword("newPassword")
                .build();


        when(companyDAO.findById(anyLong())).thenReturn(company);
        when(passwordEncoder.matches("oldPassword", "encodedOldPassword")).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");

        companyService.updatePassword(1L, passwordChangeRequestDTO);

        assertEquals("encodedNewPassword", company.getPassword());
        assertNotNull(company.getUpdatedAt());
        verify(companyDAO, times(1)).save(company);
    }

    @Test
    void updatePassword_ThrowsException_WhenCompanyDoesNotExist() {
        PasswordChangeRequestDTO passwordChangeRequestDTO = PasswordChangeRequestDTO.builder()
                .email("test@example.com")
                .password("oldPassword")
                .newPassword("newPassword")
                .build();

        when(companyDAO.findById(anyLong())).thenReturn(null);

        assertThrows(RuntimeException.class, () -> companyService.updatePassword(1L, passwordChangeRequestDTO));
        verify(companyDAO, never()).save(any(Company.class));
    }

    @Test
    void updatePassword_ThrowsException_WhenEmailDoesNotMatch() {
        Company company = Company.builder()
                .id(1L)
                .email("test@example.com")
                .build();

        PasswordChangeRequestDTO passwordChangeRequestDTO = PasswordChangeRequestDTO.builder()
                .email("wrongemail@example.com")
                .password("oldPassword")
                .newPassword("newPassword")
                .build();

        when(companyDAO.findById(anyLong())).thenReturn(company);

        assertThrows(RuntimeException.class, () -> companyService.updatePassword(1L, passwordChangeRequestDTO));
        verify(companyDAO, never()).save(any(Company.class));
    }

    @Test
    void updatePassword_ThrowsException_WhenOldPasswordDoesNotMatch() {

        Company company = Company.builder()
                .id(1L)
                .email("test@example.com")
                .password("encodedOldPassword")
                .build();

        PasswordChangeRequestDTO passwordChangeRequestDTO = PasswordChangeRequestDTO.builder()
                .email("test@example.com")
                .password("wrongOldPassword")
                .newPassword("newPassword")
                .build();


        when(companyDAO.findById(anyLong())).thenReturn(company);
        when(passwordEncoder.matches("wrongOldPassword", "encodedOldPassword")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> companyService.updatePassword(1L, passwordChangeRequestDTO));

        verify(companyDAO, never()).save(any(Company.class));
    }


    @Test
    void deleteById_DeletesCompanySuccessfully_WhenCompanyExists() {
        Company company = new Company();
        company.setId(1L);
        company.setPhotoUrl("http://example.com/photo.jpg");
        company.setActivityList(List.of());

        when(companyDAO.findById(1L)).thenReturn(company);

        companyService.deleteById(1L);

        verify(imageManagement, times(1)).deleteImageByUrl("http://example.com/photo.jpg");
        verify(companyDAO, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_DeletesCompany_WhenNoImageExists() {
        Company company = new Company();
        company.setId(1L);
        company.setPhotoUrl(null);
        company.setActivityList(List.of());

        when(companyDAO.findById(1L)).thenReturn(company);

        companyService.deleteById(1L);

        verify(imageManagement, never()).deleteImageByUrl(anyString());
        verify(companyDAO, times(1)).deleteById(1L);
    }


    @Test
    void deleteById_ThrowsException_WhenCompanyDoesNotExist() {
        when(companyDAO.findById(1L)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> companyService.deleteById(1L));
        verify(companyDAO, never()).deleteById(anyLong());
    }

    @Test
    void deleteById_DeletesActivityImages_WhenCompanyHasActivitiesWithImages() throws Exception {
        Company company = new Company();
        company.setId(1L);
        company.setActivityList(List.of(
                Activity.builder().id(1L).title("Activity 1").url("http://example.com/photo1.jpg").build(),
                Activity.builder().id(2L).title("Activity 2").url(null).build()
        ));

        when(companyDAO.findById(1L)).thenReturn(company);

        companyService.deleteById(1L);

        verify(activityService, times(1)).deleteActivityImage(1L);
        verify(activityService, never()).deleteActivityImage(2L);
        verify(companyDAO, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_DoesNotDeleteActivityImages_WhenCompanyHasNoActivitiesWithImages() throws Exception {
        Company company = new Company();
        company.setId(1L);
        company.setActivityList(List.of(
                Activity.builder().id(1L).title("Activity 1").url(null).build(),
                Activity.builder().id(2L).title("Activity 2").url(null).build()
        ));

        when(companyDAO.findById(1L)).thenReturn(company);

        companyService.deleteById(1L);

        verify(activityService, never()).deleteActivityImage(anyLong());
        verify(companyDAO, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_ThrowsExceptionWhileDeletingImages_WhenCompanyHasActivitiesWithImages() throws Exception {
        Company company = new Company();
        company.setId(1L);
        company.setActivityList(List.of(
                Activity.builder().id(1L).title("Activity 1").url("http://example.com/photo1.jpg").build(),
                Activity.builder().id(2L).title("Activity 2").url(null).build()
        ));

        when(companyDAO.findById(1L)).thenReturn(company);
        doThrow(new Exception()).when(activityService).deleteActivityImage(1L);

        assertThrows(Exception.class, () -> companyService.deleteById(1L));
        verify(companyDAO, never()).deleteById(1L);

        
    }


}