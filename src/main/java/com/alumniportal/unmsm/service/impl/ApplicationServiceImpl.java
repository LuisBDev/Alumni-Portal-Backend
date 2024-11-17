package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.ApplicationDTO;
import com.alumniportal.unmsm.model.Application;
import com.alumniportal.unmsm.persistence.IApplicationDAO;
import com.alumniportal.unmsm.persistence.IJobOfferDAO;
import com.alumniportal.unmsm.persistence.IUserDAO;
import com.alumniportal.unmsm.service.IApplicationService;
import com.alumniportal.unmsm.util.EmailTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ApplicationServiceImpl implements IApplicationService {

    @Autowired
    private IApplicationDAO applicationDAO;

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private IJobOfferDAO jobOfferDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LambdaClient lambdaClient;

    @Override
    public List<ApplicationDTO> findAll() {
        return applicationDAO.findAll()
                .stream()
                .map(application -> modelMapper.map(application, ApplicationDTO.class))
                .toList();
    }

    @Override
    public ApplicationDTO findById(Long id) {
        Application application = applicationDAO.findById(id);
        if (application == null) {
            return null;
        }
        return modelMapper.map(application, ApplicationDTO.class);
    }

    @Override
    public void save(Application application) {
        applicationDAO.save(application);
    }

    @Override
    public void deleteById(Long id) {
        applicationDAO.deleteById(id);
    }

    @Override
    public List<ApplicationDTO> findApplicationsByUserId(Long userId) {
        return applicationDAO.findApplicationsByUserId(userId)
                .stream()
                .map(application -> modelMapper.map(application, ApplicationDTO.class))
                .toList();
    }

    @Override
    public List<ApplicationDTO> findApplicationsByJobOfferId(Long jobOfferId) {
        return applicationDAO.findApplicationsByJobOfferId(jobOfferId)
                .stream()
                .map(application -> modelMapper.map(application, ApplicationDTO.class))
                .toList();
    }

    @Override
    public ApplicationDTO findApplicationByUserIdAndJobOfferId(Long userId, Long jobOfferId) {
        Application application = applicationDAO.findApplicationByUserIdAndJobOfferId(userId, jobOfferId);
        if (application == null) {
            return null;
        }
        return modelMapper.map(application, ApplicationDTO.class);
    }


    public void saveApplication(Application application) {
        User user = userDAO.findById(application.getUser().getId());
        if (user == null) {
            throw new IllegalArgumentException("Error: User not found!");
        }

        JobOffer jobOffer = jobOfferDAO.findById(application.getJobOffer().getId());
        if (jobOffer == null) {
            throw new IllegalArgumentException("Error: JobOffer not found!");
        }

        if (applicationDAO.findApplicationByUserIdAndJobOfferId(user.getId(), jobOffer.getId()) != null) {
            throw new IllegalArgumentException("Error: User already applied to this JobOffer!");
        }


        // Establecemos las relaciones
        application.setUser(user);
        application.setJobOffer(jobOffer);
        application.setApplicationDate(LocalDate.now());
        application.setStatus("PENDING");

        // Persistimos la aplicación
        applicationDAO.save(application);

        // Actualizamos el user con la nueva aplicación
        user.getApplicationList().add(application);
        userDAO.save(user);

        // Actualizamos la lista de aplicaciones en el JobOffer
        jobOffer.getApplicationList().add(application);
        jobOfferDAO.save(jobOffer);


        invokeLambdaWhenApplicationIsCreated("AlumniPortal | Successful Application " + application.getId(), application);

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
