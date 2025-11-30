package com.alumniportal.unmsm.persistence.interfaces;

import com.alumniportal.unmsm.model.Notification;
import com.alumniportal.unmsm.model.NotificationType;

import java.util.List;

public interface INotificationDAO {

    List<Notification> findAll();

    Notification findById(Long id);

    void save(Notification notification);

    void deleteById(Long id);

    List<Notification> findNotificationsByUserId(Long userId);

    List<Notification> findUnreadNotificationsByUserId(Long userId);

    List<Notification> findReadNotificationsByUserId(Long userId);

    Long countUnreadNotificationsByUserId(Long userId);

    List<Notification> findNotificationsByJobOfferId(Long jobOfferId);

    List<Notification> findNotificationsByCompanyId(Long companyId);

    List<Notification> findNotificationsByUserIdAndType(Long userId, NotificationType type);
}

