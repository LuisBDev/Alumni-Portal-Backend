package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.request.ApplicationRequestDTO;
import com.alumniportal.unmsm.dto.response.ApplicationResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.ApplicationMapper;
import com.alumniportal.unmsm.model.Application;
import com.alumniportal.unmsm.persistence.interfaces.IApplicationDAO;
import com.alumniportal.unmsm.persistence.interfaces.IJobOfferDAO;
import com.alumniportal.unmsm.persistence.interfaces.IUserDAO;
import com.alumniportal.unmsm.service.interfaces.IApplicationService;
import com.alumniportal.unmsm.util.EmailTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.alumniportal.unmsm.model.JobOffer;
import com.alumniportal.unmsm.model.User;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements IApplicationService {

    private final IApplicationDAO applicationDAO;

    private final IUserDAO userDAO;

    private final IJobOfferDAO jobOfferDAO;

    private final ApplicationMapper applicationMapper;

    private final LambdaClient lambdaClient;

    @Override
    public List<ApplicationResponseDTO> findAll() {
        List<Application> applications = applicationDAO.findAll();
        if (applications.isEmpty()) {
            throw new AppException("Error: There are no applications", "NOT_FOUND");
        }
        return applicationMapper.entityListToDTOList(applications);

    }

    @Override
    public ApplicationResponseDTO findById(Long id) {
        Application application = applicationDAO.findById(id);
        if (application == null) {
            throw new AppException("Error: Application with id " + id + " not found", "NOT_FOUND");
        }
        return applicationMapper.entityToDTO(application);
    }

    @Override
    public void save(Application application) {
        applicationDAO.save(application);
    }

    @Override
    public void deleteById(Long id) {
        Application application = applicationDAO.findById(id);
        if (application == null) {
            throw new AppException("Error: Application with id " + id + " not found", "NOT_FOUND");
        }
        applicationDAO.deleteById(id);
    }

    @Override
    public List<ApplicationResponseDTO> findApplicationsByUserId(Long userId) {
        List<Application> applicationsByUserId = applicationDAO.findApplicationsByUserId(userId);
        if (applicationsByUserId.isEmpty()) {
            throw new AppException("Error: There are no applications for user with id " + userId, "NOT_FOUND");
        }
        return applicationMapper.entityListToDTOList(applicationsByUserId);
    }

    @Override
    public List<ApplicationResponseDTO> findApplicationsByJobOfferId(Long jobOfferId) {
        List<Application> applicationsByJobOfferId = applicationDAO.findApplicationsByJobOfferId(jobOfferId);
        if (applicationsByJobOfferId.isEmpty()) {
            throw new AppException("Error: There are no applications for job offer with id " + jobOfferId, "NOT_FOUND");
        }
        return applicationMapper.entityListToDTOList(applicationsByJobOfferId);
    }

    @Override
    public ApplicationResponseDTO findApplicationByUserIdAndJobOfferId(Long userId, Long jobOfferId) {
        Application application = applicationDAO.findApplicationByUserIdAndJobOfferId(userId, jobOfferId);
        if (application == null) {
            throw new AppException("Error: Application not found for user with id " + userId + " and job offer with id " + jobOfferId, "NOT_FOUND");
        }
        return applicationMapper.entityToDTO(application);
    }


    public ApplicationResponseDTO saveApplication(ApplicationRequestDTO applicationRequestDTO) {
        User user = userDAO.findById(applicationRequestDTO.getUser().getId());
        if (user == null) {
            throw new AppException("Error: User with id " + applicationRequestDTO.getUser().getId() + " not found", "NOT_FOUND");
        }

        JobOffer jobOffer = jobOfferDAO.findById(applicationRequestDTO.getJobOffer().getId());
        if (jobOffer == null) {
            throw new AppException("Error: JobOffer with id " + applicationRequestDTO.getJobOffer().getId() + " not found", "NOT_FOUND");
        }

        if (applicationDAO.findApplicationByUserIdAndJobOfferId(user.getId(), jobOffer.getId()) != null) {
            throw new AppException("Error: User with id " + user.getId() + " has already applied to JobOffer with id " + jobOffer.getId(), "BAD_REQUEST");
        }

        Application application = applicationMapper.requestDtoToEntity(applicationRequestDTO);


        // Establecemos las relaciones
        application.setUser(user);
        application.setJobOffer(jobOffer);
        application.setApplicationDate(LocalDate.now());
        application.setStatus("PENDING");

        // Persistimos la aplicación
        applicationDAO.save(application);

        invokeLambdaWhenApplicationIsCreated("AlumniPortal | Successful Application " + application.getId(), application);
        return applicationMapper.entityToDTO(application);
    }

    public void invokeLambdaWhenApplicationIsCreated(String subject, Application application) {
        String htmlContent = EmailTemplate.generateHtmlContentApplication(
                application.getStatus(),
                application.getApplicationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                application.getJobOffer().getId().toString(),
                application.getJobOffer().getCompany().getId().toString()
        );


        List<String> recipients = List.of(userDAO.findById(application.getUser().getId()).getEmail());

        try {
            // Se crea el payload en JSON
            Map<String, Object> payload = Map.of(
                    "subject", subject,
                    "htmlContent", htmlContent,
                    "recipients", recipients
            );
            String payloadJson = new ObjectMapper().writeValueAsString(payload);

            // Se crea la solicitud para invocar Lambda
            InvokeRequest invokeRequest = InvokeRequest.builder()
                    .functionName("arn:aws:lambda:us-east-2:047719652432:function:alumnilambda")
                    .payload(SdkBytes.fromUtf8String(payloadJson))
                    .build();

            // Se invoca la función Lambda
//            InvokeResponse invokeResponse = lambdaClient.invoke(invokeRequest);
//            String response = invokeResponse.payload().asUtf8String();
//            System.out.println("Lambda response: " + response);

            // Se invoca la función Lambda de manera asíncrona
            CompletableFuture<InvokeResponse> futureResponse = CompletableFuture.supplyAsync(() -> lambdaClient.invoke(invokeRequest));

            // Manejar la respuesta de la invocación asíncrona
            futureResponse.thenAccept(invokeResponse -> {
                String response = invokeResponse.payload().asUtf8String();
                System.out.println("Lambda response: " + response);
            }).exceptionally(e -> {
                System.err.println("Error al invocar Lambda: " + e.getMessage());
                return null;
            });

        } catch (Exception e) {
            System.err.println("Error al invocar Lambda: " + e.getMessage());
        }
    }


}
