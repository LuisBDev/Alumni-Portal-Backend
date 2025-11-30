package com.alumniportal.unmsm.mapper;

import com.alumniportal.unmsm.dto.request.NotificationRequestDTO;
import com.alumniportal.unmsm.dto.response.NotificationResponseDTO;
import com.alumniportal.unmsm.model.Notification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationMapper {

    private final ModelMapper modelMapper;

    public NotificationResponseDTO entityToDTO(Notification notification) {

        NotificationResponseDTO.NotificationResponseDTOBuilder dtoBuilder = NotificationResponseDTO.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .type(notification.getType())
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt())
                .readAt(notification.getReadAt());

        if (notification.getUser() != null) {
            dtoBuilder.userId(notification.getUser().getId())
                    .userName(notification.getUser().getName())
                    .userEmail(notification.getUser().getEmail());
        }

        if (notification.getJobOffer() != null) {
            dtoBuilder.jobOfferId(notification.getJobOffer().getId())
                    .jobOfferTitle(notification.getJobOffer().getTitle())
                    .jobOfferDescription(notification.getJobOffer().getDescription());
        }

        if (notification.getCompany() != null) {
            dtoBuilder.companyId(notification.getCompany().getId())
                    .companyName(notification.getCompany().getName())
                    .companyPhotoUrl(notification.getCompany().getPhotoUrl());
        }

        return dtoBuilder.build();
    }

    public List<NotificationResponseDTO> entityListToDTOList(List<Notification> notifications) {
        return notifications.stream()
                .map(this::entityToDTO)
                .toList();
    }

    public Notification dtoToEntity(NotificationResponseDTO notificationResponseDTO) {
        return modelMapper.map(notificationResponseDTO, Notification.class);
    }

    public List<Notification> dtoListToEntityList(List<NotificationResponseDTO> notificationResponseDTOS) {
        return notificationResponseDTOS.stream()
                .map(this::dtoToEntity)
                .toList();
    }

    public Notification requestDtoToEntity(NotificationRequestDTO notificationRequestDTO) {
        return modelMapper.map(notificationRequestDTO, Notification.class);
    }
}
