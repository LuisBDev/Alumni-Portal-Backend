package com.alumniportal.unmsm.dto.response;

import com.alumniportal.unmsm.model.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponseDTO {

    private Long id;
    private String message;
    private NotificationType type;
    private Boolean isRead;
    private LocalDateTime createdAt;
    private LocalDateTime readAt;

    private Long userId;
    private String userName;
    private String userEmail;

    private Long jobOfferId;
    private String jobOfferTitle;
    private String jobOfferDescription;

    private Long companyId;
    private String companyName;
    private String companyPhotoUrl;
}
