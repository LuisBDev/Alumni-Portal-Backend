package com.alumniportal.unmsm.dto.request;

import com.alumniportal.unmsm.model.NotificationType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequestDTO {

    @NotNull
    private Long userId;

    @NotNull
    private String message;

    @NotNull
    private NotificationType type;

    private Long jobOfferId;

    private Long companyId;
}
