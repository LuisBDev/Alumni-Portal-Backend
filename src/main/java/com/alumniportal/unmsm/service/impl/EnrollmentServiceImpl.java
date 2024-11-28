package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.EnrollmentDTO;
import com.alumniportal.unmsm.model.Activity;
import com.alumniportal.unmsm.model.Enrollment;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.interfaces.IActivityDAO;
import com.alumniportal.unmsm.persistence.interfaces.IEnrollmentDAO;
import com.alumniportal.unmsm.persistence.interfaces.IUserDAO;
import com.alumniportal.unmsm.service.interfaces.IEnrollmentService;
import com.alumniportal.unmsm.util.EmailTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
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
public class EnrollmentServiceImpl implements IEnrollmentService {

    private final IEnrollmentDAO enrollmentDAO;

    private final IUserDAO userDAO;

    private final IActivityDAO activityDAO;

    private final ModelMapper modelMapper;

    private final LambdaClient lambdaClient;

    @Override
    public List<EnrollmentDTO> findAll() {
        return enrollmentDAO.findAll()
                .stream()
                .map(enrollment -> modelMapper.map(enrollment, EnrollmentDTO.class))
                .toList();
    }

    @Override
    public EnrollmentDTO findById(Long id) {
        Enrollment enrollment = enrollmentDAO.findById(id);
        if (enrollment == null) {
            return null;
        }
        return modelMapper.map(enrollment, EnrollmentDTO.class);
    }

    @Override
    public void save(Enrollment enrollment) {
        enrollmentDAO.save(enrollment);
    }

    @Override
    public void deleteById(Long id) {
        enrollmentDAO.deleteById(id);
    }

    @Override
    public List<EnrollmentDTO> findEnrollmentsByUserId(Long userId) {
        return enrollmentDAO.findEnrollmentsByUserId(userId)
                .stream()
                .map(enrollment -> modelMapper.map(enrollment, EnrollmentDTO.class))
                .toList();
    }

    @Override
    public List<EnrollmentDTO> findEnrollmentsByActivityId(Long activityId) {
        return enrollmentDAO.findEnrollmentsByActivityId(activityId)
                .stream()
                .map(enrollment -> modelMapper.map(enrollment, EnrollmentDTO.class))
                .toList();
    }

    @Override
    public EnrollmentDTO findEnrollmentByUserIdAndActivityId(Long userId, Long activityId) {
        Enrollment enrollment = enrollmentDAO.findEnrollmentByUserIdAndActivityId(userId, activityId);
        if (enrollment == null) {
            return null;
        }
        return modelMapper.map(enrollment, EnrollmentDTO.class);
    }


    public void saveEnrollment(Enrollment enrollment) {
        User user = userDAO.findById(enrollment.getUser().getId());
        if (user == null) {
            throw new IllegalArgumentException("Error: User not found!");
        }
        Activity activity = activityDAO.findById(enrollment.getActivity().getId());
        if (activity == null) {
            throw new IllegalArgumentException("Error: Activity not found!");
        }

        if (!activity.isEnrollable()) {
            throw new IllegalArgumentException("Error: Activity is not enrollable!");
        }

        if (enrollmentDAO.findEnrollmentByUserIdAndActivityId(user.getId(), activity.getId()) != null) {
            throw new IllegalArgumentException("Error: User is already enrolled in this activity!");
        }

//        Persistir enrollment
        enrollment.setUser(user);
        enrollment.setActivity(activity);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setStatus("ACTIVE");
        enrollmentDAO.save(enrollment);

//        Actualizar user
        user.getEnrollmentList().add(enrollment);
        userDAO.save(user);

//        Actualizar activity
        activity.getEnrollmentList().add(enrollment);
        activityDAO.save(activity);

        invokeLambdaWhenEnrollmentIsCreated("AlumniPortal | Confirmation of Activity Registration " + activity.getId(), enrollment);

    }

    public void invokeLambdaWhenEnrollmentIsCreated(String subject, Enrollment enrollment) {

        String htmlContent = EmailTemplate.generateHtmlContentEnrollCreated(
                enrollment.getActivity().getTitle(),
                enrollment.getActivity().getDescription(),
                enrollment.getActivity().getEventType(),
                enrollment.getActivity().getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                enrollment.getActivity().getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                enrollment.getActivity().getLocation(),
                enrollment.getActivity().isEnrollable(),
                enrollment.getEnrollmentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                enrollment.getStatus()

        );

        List<String> recipients = List.of(userDAO.findById(enrollment.getUser().getId()).getEmail());

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
            CompletableFuture<InvokeResponse> futureResponse = CompletableFuture.supplyAsync(() -> lambdaClient.invoke(invokeRequest));

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
