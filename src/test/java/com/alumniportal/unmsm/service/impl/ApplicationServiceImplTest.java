package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.Data.ApplicationProvider;
import com.alumniportal.unmsm.dto.ApplicationDTO;
import com.alumniportal.unmsm.model.Application;
import com.alumniportal.unmsm.persistence.IApplicationDAO;
import com.alumniportal.unmsm.persistence.IJobOfferDAO;
import com.alumniportal.unmsm.persistence.IUserDAO;
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
    private ModelMapper modelMapper;

    @Mock
    private LambdaClient lambdaClient;

    @InjectMocks
    private ApplicationServiceImpl applicationService;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        applicationService = new ApplicationServiceImpl(applicationDAO, userDAO, jobOfferDAO, modelMapper, lambdaClient);
    }

    @Test
    void testFindAll() {

        when(applicationDAO.findAll()).thenReturn(ApplicationProvider.applicationList());

        List<ApplicationDTO> applicationDTOList = applicationService.findAll();

        assertEquals("User 1", applicationDTOList.get(0).getUserName());
        assertEquals("User 2", applicationDTOList.get(1).getUserName());
        assertEquals("REJECTED", applicationDTOList.get(1).getStatus());
    }

    @Test
    void testFindById() {
        when(applicationDAO.findById(anyLong())).thenReturn(ApplicationProvider.applicationOne());

        ApplicationDTO applicationDTO = applicationService.findById(anyLong());

        assertEquals("User 1", applicationDTO.getUserName());
        assertEquals("PENDING", applicationDTO.getStatus());
    }

    @Test
    void testFindById_WhenApplicationIsNull() {
        when(applicationDAO.findById(anyLong())).thenReturn(null);

        ApplicationDTO applicationDTO = applicationService.findById(anyLong());

        assertNull(applicationDTO);
    }

    @Test
    void testSaveApplication() {

        Application application = ApplicationProvider.applicationOne();

        doNothing().when(applicationDAO).save(any());
        applicationService.save(application);

        verify(applicationDAO, times(1)).save(any());
    }


    @Test
    void testDeleteById() {

        doNothing().when(applicationDAO).deleteById(anyLong());
        applicationService.deleteById(anyLong());

        verify(applicationDAO, times(1)).deleteById(anyLong());

    }

    @Test
    void testFindApplicationsByUserId() {
        when(applicationDAO.findApplicationsByUserId(anyLong())).thenReturn(ApplicationProvider.applicationList());

        List<ApplicationDTO> applicationDTOList = applicationService.findApplicationsByUserId(anyLong());

        assertEquals("User 1", applicationDTOList.get(0).getUserName());
        assertEquals("User 2", applicationDTOList.get(1).getUserName());
        assertEquals("REJECTED", applicationDTOList.get(1).getStatus());
    }

    @Test
    void testFindApplicationsByJobOfferId() {
        when(applicationDAO.findApplicationsByJobOfferId(anyLong())).thenReturn(ApplicationProvider.applicationList());

        List<ApplicationDTO> applicationDTOList = applicationService.findApplicationsByJobOfferId(anyLong());

        assertEquals("PENDING", applicationDTOList.get(0).getStatus());
    }

    @Test
    void testFindApplicationByUserIdAndJobOfferId() {
        when(applicationDAO.findApplicationByUserIdAndJobOfferId(anyLong(), anyLong())).thenReturn(ApplicationProvider.applicationOne());

        ApplicationDTO applicationDTO = applicationService.findApplicationByUserIdAndJobOfferId(anyLong(), anyLong());

        assertEquals("User 1", applicationDTO.getUserName());
        assertEquals("PENDING", applicationDTO.getStatus());
    }

}
