package com.alumniportal.unmsm.service.interfaces;

import com.alumniportal.unmsm.dto.response.NotificationResponseDTO;
import com.alumniportal.unmsm.model.NotificationType;

import java.util.List;

public interface INotificationService {


    void createNotificationsForJobOffer(Long jobOfferId);

    NotificationResponseDTO markAsRead(Long notificationId);

    NotificationResponseDTO markAsUnread(Long notificationId);

    void markAllAsReadByUserId(Long userId);

    List<NotificationResponseDTO> getNotificationsByUserId(Long userId);

    List<NotificationResponseDTO> getUnreadNotificationsByUserId(Long userId);

    List<NotificationResponseDTO> getReadNotificationsByUserId(Long userId);

    Long countUnreadNotificationsByUserId(Long userId);

    List<NotificationResponseDTO> getNotificationsByJobOfferId(Long jobOfferId);

    List<NotificationResponseDTO> getNotificationsByCompanyId(Long companyId);

    List<NotificationResponseDTO> getNotificationsByUserIdAndType(Long userId, NotificationType type);

    List<NotificationResponseDTO> findAll();

    NotificationResponseDTO findById(Long id);

    void deleteById(Long id);

    void deleteAllByUserId(Long userId);
}

