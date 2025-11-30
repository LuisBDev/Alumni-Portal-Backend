package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.Notification;
import com.alumniportal.unmsm.model.NotificationType;
import com.alumniportal.unmsm.persistence.interfaces.INotificationDAO;
import com.alumniportal.unmsm.repository.INotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationDAOImpl implements INotificationDAO {

    private final INotificationRepository notificationRepository;

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification findById(Long id) {
        return notificationRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Notification notification) {
        notificationRepository.save(notification);
    }

    @Override
    public void deleteById(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public List<Notification> findNotificationsByUserId(Long userId) {
        return notificationRepository.findNotificationsByUserId(userId);
    }

    @Override
    public List<Notification> findUnreadNotificationsByUserId(Long userId) {
        return notificationRepository.findUnreadNotificationsByUserId(userId);
    }

    @Override
    public List<Notification> findReadNotificationsByUserId(Long userId) {
        return notificationRepository.findReadNotificationsByUserId(userId);
    }

    @Override
    public Long countUnreadNotificationsByUserId(Long userId) {
        return notificationRepository.countUnreadNotificationsByUserId(userId);
    }

    @Override
    public List<Notification> findNotificationsByJobOfferId(Long jobOfferId) {
        return notificationRepository.findNotificationsByJobOfferId(jobOfferId);
    }

    @Override
    public List<Notification> findNotificationsByCompanyId(Long companyId) {
        return notificationRepository.findNotificationsByCompanyId(companyId);
    }

    @Override
    public List<Notification> findNotificationsByUserIdAndType(Long userId, NotificationType type) {
        return notificationRepository.findNotificationsByUserIdAndType(userId, type);
    }
}

