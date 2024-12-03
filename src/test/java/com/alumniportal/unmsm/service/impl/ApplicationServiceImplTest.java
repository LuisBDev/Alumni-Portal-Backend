package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.Data.ApplicationProvider;
import com.alumniportal.unmsm.dto.ResponseDTO.ApplicationResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.ApplicationMapper;
import com.alumniportal.unmsm.model.Application;
import com.alumniportal.unmsm.persistence.interfaces.IApplicationDAO;
import com.alumniportal.unmsm.persistence.interfaces.IJobOfferDAO;
import com.alumniportal.unmsm.persistence.interfaces.IUserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import software.amazon.awssdk.services.lambda.LambdaClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceImplTest {

    @Mock
    private IApplicationDAO applicationDAO;

    @Mock
    private IUserDAO userDAO;

    @Mock
    private IJobOfferDAO jobOfferDAO;

    //    Real ModelMapper
    private ApplicationMapper applicationMapper;

    @Mock
    private LambdaClient lambdaClient;

    @InjectMocks
    private ApplicationServiceImpl applicationService;

    @BeforeEach
    void setUp() {
        applicationMapper = new ApplicationMapper(new ModelMapper());
        applicationService = new ApplicationServiceImpl(applicationDAO, userDAO, jobOfferDAO, applicationMapper, lambdaClient);
    }

    @Test
    void testFindAll() {

        when(applicationDAO.findAll()).thenReturn(ApplicationProvider.applicationList());

        List<ApplicationResponseDTO> applicationResponseDTOList = applicationService.findAll();

        assertEquals("User 1", applicationResponseDTOList.get(0).getUserName());
        assertEquals("User 2", applicationResponseDTOList.get(1).getUserName());
        assertEquals("REJECTED", applicationResponseDTOList.get(1).getStatus());
    }

    @Test
    void testFindAll_WhenApplicationListIsEmpty() {
        when(applicationDAO.findAll()).thenReturn(List.of());

        assertThrows(AppException.class, () -> applicationService.findAll());

        verify(applicationDAO, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(applicationDAO.findById(anyLong())).thenReturn(ApplicationProvider.applicationOne());

        ApplicationResponseDTO applicationResponseDTO = applicationService.findById(anyLong());

        assertEquals("User 1", applicationResponseDTO.getUserName());
        assertEquals("PENDING", applicationResponseDTO.getStatus());
    }

    @Test
    void testFindById_WhenApplicationIsNull() {
        when(applicationDAO.findById(anyLong())).thenReturn(null);

        assertThrows(AppException.class, () -> applicationService.findById(anyLong()));

        verify(applicationDAO, times(1)).findById(anyLong());
    }

    @Test
    void testSave() {

        Application application = ApplicationProvider.applicationOne();

        doNothing().when(applicationDAO).save(any());
        applicationService.save(application);

        verify(applicationDAO, times(1)).save(any());
    }


    @Test
    void testDeleteById() {

        doNothing().when(applicationDAO).deleteById(anyLong());
        when(applicationDAO.findById(anyLong())).thenReturn(ApplicationProvider.applicationOne());
        applicationService.deleteById(anyLong());

        verify(applicationDAO, times(1)).deleteById(anyLong());

    }

    @Test
    void testDeleteById_WhenApplicationIsNull() {
        when(applicationDAO.findById(anyLong())).thenReturn(null);

        assertThrows(AppException.class, () -> applicationService.deleteById(anyLong()));

        verify(applicationDAO, times(1)).findById(anyLong());
    }

    @Test
    void testFindApplicationsByUserId() {
        when(applicationDAO.findApplicationsByUserId(anyLong())).thenReturn(ApplicationProvider.applicationList());

        List<ApplicationResponseDTO> applicationResponseDTOList = applicationService.findApplicationsByUserId(anyLong());

        assertEquals("User 1", applicationResponseDTOList.get(0).getUserName());
        assertEquals("User 2", applicationResponseDTOList.get(1).getUserName());
        assertEquals("REJECTED", applicationResponseDTOList.get(1).getStatus());
    }

    @Test
    void testFindApplicationsByUserId_WhenApplicationListIsEmpty() {
        when(applicationDAO.findApplicationsByUserId(anyLong())).thenReturn(List.of());

        assertThrows(AppException.class, () -> applicationService.findApplicationsByUserId(anyLong()));

        verify(applicationDAO, times(1)).findApplicationsByUserId(anyLong());
    }

    @Test
    void testFindApplicationsByJobOfferId() {
        when(applicationDAO.findApplicationsByJobOfferId(anyLong())).thenReturn(ApplicationProvider.applicationList());

        List<ApplicationResponseDTO> applicationResponseDTOList = applicationService.findApplicationsByJobOfferId(anyLong());

        assertEquals("PENDING", applicationResponseDTOList.get(0).getStatus());
    }

    @Test
    void testFindApplicationsByJobOfferId_WhenApplicationListIsEmpty() {
        when(applicationDAO.findApplicationsByJobOfferId(anyLong())).thenReturn(List.of());

        assertThrows(AppException.class, () -> applicationService.findApplicationsByJobOfferId(anyLong()));

        verify(applicationDAO, times(1)).findApplicationsByJobOfferId(anyLong());
    }

    @Test
    void testFindApplicationByUserIdAndJobOfferId() {
        when(applicationDAO.findApplicationByUserIdAndJobOfferId(anyLong(), anyLong())).thenReturn(ApplicationProvider.applicationOne());

        ApplicationResponseDTO applicationResponseDTO = applicationService.findApplicationByUserIdAndJobOfferId(anyLong(), anyLong());

        assertEquals("User 1", applicationResponseDTO.getUserName());
        assertEquals("PENDING", applicationResponseDTO.getStatus());
    }

    @Test
    void testFindApplicationByUserIdAndJobOfferId_WhenApplicationIsNull() {
        when(applicationDAO.findApplicationByUserIdAndJobOfferId(anyLong(), anyLong())).thenReturn(null);

        assertThrows(AppException.class, () -> applicationService.findApplicationByUserIdAndJobOfferId(anyLong(), anyLong()));
        verify(applicationDAO, times(1)).findApplicationByUserIdAndJobOfferId(anyLong(), anyLong());
    }

//    @Test
//    public void testSaveApplication_Success() {
//        // Arrange
//        Application application = new Application();
//        User user = new User();
//        user.setId(1L);
//        user.setApplicationList(new ArrayList<>());  // Inicializamos la lista de aplicaciones
//
//        JobOffer jobOffer = new JobOffer();
//        jobOffer.setId(1L);
//        jobOffer.setApplicationList(new ArrayList<>()); // Inicializamos la lista de aplicaciones del JobOffer
//        jobOffer.setCompany(Company.builder()
//                .id(1L)
//                .name("Company 1")
//                .build());
//
//        application.setUser(user);
//        application.setJobOffer(jobOffer);
//
//        // Mocking de las dependencias
//        when(userDAO.findById(1L)).thenReturn(user);
//        when(jobOfferDAO.findById(1L)).thenReturn(jobOffer);
//        when(applicationDAO.findApplicationByUserIdAndJobOfferId(user.getId(), jobOffer.getId())).thenReturn(null); // No application exists
//        doNothing().when(applicationDAO).save(any(Application.class)); // Mock para save de application
//        doNothing().when(userDAO).save(any(User.class)); // Mock para save de user
//        doNothing().when(jobOfferDAO).save(any(JobOffer.class)); // Mock para save de jobOffer
//
//        // Act
//        applicationService.saveApplication(application);
//
//        // Assert
//        verify(userDAO).save(user); // Verifica que userDAO.save fue llamado
//        verify(jobOfferDAO).save(jobOffer); // Verifica que jobOfferDAO.save fue llamado
//        verify(applicationDAO).save(application); // Verifica que applicationDAO.save fue llamado
//    }

}
