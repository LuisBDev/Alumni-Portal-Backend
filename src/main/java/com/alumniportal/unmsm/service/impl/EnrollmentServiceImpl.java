package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.request.EnrollmentRequestDTO;
import com.alumniportal.unmsm.dto.response.EnrollmentResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.EnrollmentMapper;
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

    private final EnrollmentMapper enrollmentMapper;

    private final LambdaClient lambdaClient;

    @Override
    public List<EnrollmentResponseDTO> findAll() {
        List<Enrollment> enrollmentList = enrollmentDAO.findAll();

        return enrollmentMapper.entityListToDTOList(enrollmentList);
    }

    @Override
    public EnrollmentResponseDTO findById(Long id) {
        Enrollment enrollment = enrollmentDAO.findById(id);
        if (enrollment == null) {
            throw new AppException("Enrollment not found", "NOT_FOUND");
        }
        return enrollmentMapper.entityToDTO(enrollment);
    }

    @Override
    public void save(Enrollment enrollment) {
        enrollmentDAO.save(enrollment);
    }

    @Override
    public void deleteById(Long id) {
        Enrollment enrollment = enrollmentDAO.findById(id);
        if (enrollment == null) {
            throw new AppException("Enrollment not found", "NOT_FOUND");
        }
        enrollmentDAO.deleteById(id);
    }

    @Override
    public List<EnrollmentResponseDTO> findEnrollmentsByUserId(Long userId) {
        List<Enrollment> enrollmentsByUserId = enrollmentDAO.findEnrollmentsByUserId(userId);

        return enrollmentMapper.entityListToDTOList(enrollmentsByUserId);
    }

    @Override
    public List<EnrollmentResponseDTO> findEnrollmentsByActivityId(Long activityId) {
        List<Enrollment> enrollmentsByActivityId = enrollmentDAO.findEnrollmentsByActivityId(activityId);

        return enrollmentMapper.entityListToDTOList(enrollmentsByActivityId);
    }

    @Override
    public EnrollmentResponseDTO findEnrollmentByUserIdAndActivityId(Long userId, Long activityId) {
        Enrollment enrollment = enrollmentDAO.findEnrollmentByUserIdAndActivityId(userId, activityId);
        if (enrollment == null) {
            throw new AppException("No enrollment exists by userId: " + userId + " and activityId: " + activityId, "NOT_FOUND");
        }
        return enrollmentMapper.entityToDTO(enrollment);
    }


    public EnrollmentResponseDTO saveEnrollment(EnrollmentRequestDTO enrollmentRequestDTO) {
        User user = userDAO.findById(enrollmentRequestDTO.getUser().getId());
        if (user == null) {
            throw new AppException("Error: User not found!", "NOT_FOUND");
        }
        Activity activity = activityDAO.findById(enrollmentRequestDTO.getActivity().getId());
        if (activity == null) {
            throw new AppException("Error: Activity not found!", "NOT_FOUND");
        }

        if (!activity.isEnrollable()) {
            throw new AppException("Error: Activity is not enrollable!", "BAD_REQUEST");
        }

        if (enrollmentDAO.findEnrollmentByUserIdAndActivityId(user.getId(), activity.getId()) != null) {
            throw new AppException("Error: User is already enrolled in this activity!", "BAD_REQUEST");
        }

        Enrollment enrollment = enrollmentMapper.requestDtoToEntity(enrollmentRequestDTO);

        enrollment.setUser(user);
        enrollment.setActivity(activity);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setStatus("ACTIVE");

        enrollmentDAO.save(enrollment);

        invokeLambdaWhenEnrollmentIsCreated("AlumniPortal | Confirmation of Activity Registration " + activity.getId(), enrollment);

        return enrollmentMapper.entityToDTO(enrollment);
    }

    public void invokeLambdaWhenEnrollmentIsCreated(String subject, Enrollment enrollment) {

        String htmlContent = EmailTemplate.generateHtmlContentEnrollCreated(
                enrollment.getActivity().getTitle(),
                enrollment.getActivity().getDescription(),
                enrollment.getActivity().getEventType(),
                enrollment.getActivity().getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                enrollment.getActivity().getEndDate() != null ? enrollment.getActivity().getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "",
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
