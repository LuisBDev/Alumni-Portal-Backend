package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.data.ApplicationProvider;
import com.alumniportal.unmsm.dto.response.ApplicationResponseDTO;
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
    void findAllReturnsApplicationList() {

        when(applicationDAO.findAll()).thenReturn(ApplicationProvider.applicationList());

        List<ApplicationResponseDTO> applicationResponseDTOList = applicationService.findAll();

        assertEquals("User 1", applicationResponseDTOList.get(0).getUserName());
        assertEquals("User 2", applicationResponseDTOList.get(1).getUserName());
        assertEquals("REJECTED", applicationResponseDTOList.get(1).getStatus());
    }

    @Test
    void findAllThrowsExceptionWhenApplicationListIsEmpty() {
        when(applicationDAO.findAll()).thenReturn(List.of());

        assertThrows(AppException.class, () -> applicationService.findAll());

        verify(applicationDAO, times(1)).findAll();
    }

    @Test
    void findByIdReturnsApplication() {
        when(applicationDAO.findById(anyLong())).thenReturn(ApplicationProvider.applicationOne());

        ApplicationResponseDTO applicationResponseDTO = applicationService.findById(anyLong());

        assertEquals("User 1", applicationResponseDTO.getUserName());
        assertEquals("PENDING", applicationResponseDTO.getStatus());
    }

    @Test
    void findByIdThrowsExceptionWhenApplicationIsNull() {
        when(applicationDAO.findById(anyLong())).thenReturn(null);

        assertThrows(AppException.class, () -> applicationService.findById(anyLong()));

        verify(applicationDAO, times(1)).findById(anyLong());
    }

    @Test
    void saveSavesApplicationSuccessfully() {

        Application application = ApplicationProvider.applicationOne();

        doNothing().when(applicationDAO).save(any());
        applicationService.save(application);

        verify(applicationDAO, times(1)).save(any());
    }


    @Test
    void deleteByIdDeletesApplicationSuccessfully() {

        doNothing().when(applicationDAO).deleteById(anyLong());
        when(applicationDAO.findById(anyLong())).thenReturn(ApplicationProvider.applicationOne());
        applicationService.deleteById(anyLong());

        verify(applicationDAO, times(1)).deleteById(anyLong());

    }

    @Test
    void deleteByIdThrowsExceptionWhenApplicationIsNull() {
        when(applicationDAO.findById(anyLong())).thenReturn(null);

        assertThrows(AppException.class, () -> applicationService.deleteById(anyLong()));

        verify(applicationDAO, times(1)).findById(anyLong());
    }

    @Test
    void findApplicationsByUserIdReturnsApplicationList() {
        when(applicationDAO.findApplicationsByUserId(anyLong())).thenReturn(ApplicationProvider.applicationList());

        List<ApplicationResponseDTO> applicationResponseDTOList = applicationService.findApplicationsByUserId(anyLong());

        assertEquals("User 1", applicationResponseDTOList.get(0).getUserName());
        assertEquals("User 2", applicationResponseDTOList.get(1).getUserName());
        assertEquals("REJECTED", applicationResponseDTOList.get(1).getStatus());
    }

    @Test
    void findApplicationsByUserIdThrowsExceptionWhenApplicationListIsEmpty() {
        when(applicationDAO.findApplicationsByUserId(anyLong())).thenReturn(List.of());

        assertThrows(AppException.class, () -> applicationService.findApplicationsByUserId(anyLong()));

        verify(applicationDAO, times(1)).findApplicationsByUserId(anyLong());
    }

    @Test
    void findApplicationsByJobOfferIdReturnsApplicationList() {
        when(applicationDAO.findApplicationsByJobOfferId(anyLong())).thenReturn(ApplicationProvider.applicationList());

        List<ApplicationResponseDTO> applicationResponseDTOList = applicationService.findApplicationsByJobOfferId(anyLong());

        assertEquals("PENDING", applicationResponseDTOList.get(0).getStatus());
    }

    @Test
    void findApplicationsByJobOfferIdThrowsExceptionWhenApplicationListIsEmpty() {
        when(applicationDAO.findApplicationsByJobOfferId(anyLong())).thenReturn(List.of());

        assertThrows(AppException.class, () -> applicationService.findApplicationsByJobOfferId(anyLong()));

        verify(applicationDAO, times(1)).findApplicationsByJobOfferId(anyLong());
    }

    @Test
    void findApplicationByUserIdAndJobOfferIdReturnsApplication() {
        when(applicationDAO.findApplicationByUserIdAndJobOfferId(anyLong(), anyLong())).thenReturn(ApplicationProvider.applicationOne());

        ApplicationResponseDTO applicationResponseDTO = applicationService.findApplicationByUserIdAndJobOfferId(anyLong(), anyLong());

        assertEquals("User 1", applicationResponseDTO.getUserName());
        assertEquals("PENDING", applicationResponseDTO.getStatus());
    }

    @Test
    void findApplicationByUserIdAndJobOfferIdThrowsExceptionWhenApplicationIsNull() {
        when(applicationDAO.findApplicationByUserIdAndJobOfferId(anyLong(), anyLong())).thenReturn(null);

        assertThrows(AppException.class, () -> applicationService.findApplicationByUserIdAndJobOfferId(anyLong(), anyLong()));
        verify(applicationDAO, times(1)).findApplicationByUserIdAndJobOfferId(anyLong(), anyLong());
    }


}
