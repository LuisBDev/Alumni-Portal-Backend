package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.response.NotificationResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.NotificationMapper;
import com.alumniportal.unmsm.model.*;
import com.alumniportal.unmsm.persistence.interfaces.*;
import com.alumniportal.unmsm.service.interfaces.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements INotificationService {

    private final INotificationDAO notificationDAO;
    private final IUserDAO userDAO;
    private final ICompanyDAO companyDAO;
    private final IJobOfferDAO jobOfferDAO;
    private final ICompanyFollowerDAO companyFollowerDAO;
    private final NotificationMapper notificationMapper;


    @Override
    @Transactional
    public void createNotificationsForJobOffer(Long jobOfferId) {
        JobOffer jobOffer = jobOfferDAO.findById(jobOfferId);
        if (jobOffer == null) {
            throw new AppException("Job offer not found!", "NOT_FOUND");
        }

        Company company = jobOffer.getCompany();
        if (company == null) {
            throw new AppException("Company not found for this job offer!", "NOT_FOUND");
        }

        List<CompanyFollower> followers = companyFollowerDAO.findFollowersByCompanyId(company.getId());

        String message = String.format("La empresa %s ha publicado una nueva oferta de trabajo: %s", company.getName(), jobOffer.getTitle());

        followers.forEach(companyFollower -> {
            Notification notification = Notification.builder()
                    .user(companyFollower.getUser())
                    .company(company)
                    .jobOffer(jobOffer)
                    .message(message)
                    .type(NotificationType.JOB_OFFER_PUBLISHED)
                    .isRead(false)
                    .createdAt(LocalDateTime.now())
                    .build();

            notificationDAO.save(notification);
        });


    }

    @Override
    @Transactional
    public NotificationResponseDTO markAsRead(Long notificationId) {
        Notification notification = notificationDAO.findById(notificationId);
        if (notification == null) {
            throw new AppException("Notification not found!", "NOT_FOUND");
        }

        notification.setIsRead(true);
        notification.setReadAt(LocalDateTime.now());
        notificationDAO.save(notification);

        return notificationMapper.entityToDTO(notification);
    }

    @Override
    @Transactional
    public NotificationResponseDTO markAsUnread(Long notificationId) {
        Notification notification = notificationDAO.findById(notificationId);
        if (notification == null) {
            throw new AppException("Notification not found!", "NOT_FOUND");
        }

        notification.setIsRead(false);
        notification.setReadAt(null);
        notificationDAO.save(notification);

        return notificationMapper.entityToDTO(notification);
    }

    @Override
    @Transactional
    public void markAllAsReadByUserId(Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new AppException("User not found!", "NOT_FOUND");
        }

        List<Notification> unreadNotifications = notificationDAO.findUnreadNotificationsByUserId(userId);
        LocalDateTime now = LocalDateTime.now();

        for (Notification notification : unreadNotifications) {
            notification.setIsRead(true);
            notification.setReadAt(now);
            notificationDAO.save(notification);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> getNotificationsByUserId(Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new AppException("User not found!", "NOT_FOUND");
        }

        List<Notification> notifications = notificationDAO.findNotificationsByUserId(userId);
        return notificationMapper.entityListToDTOList(notifications);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> getUnreadNotificationsByUserId(Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new AppException("User not found!", "NOT_FOUND");
        }

        List<Notification> notifications = notificationDAO.findUnreadNotificationsByUserId(userId);
        return notificationMapper.entityListToDTOList(notifications);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> getReadNotificationsByUserId(Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new AppException("User not found!", "NOT_FOUND");
        }

        List<Notification> notifications = notificationDAO.findReadNotificationsByUserId(userId);
        return notificationMapper.entityListToDTOList(notifications);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countUnreadNotificationsByUserId(Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new AppException("User not found!", "NOT_FOUND");
        }

        return notificationDAO.countUnreadNotificationsByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> getNotificationsByJobOfferId(Long jobOfferId) {
        JobOffer jobOffer = jobOfferDAO.findById(jobOfferId);
        if (jobOffer == null) {
            throw new AppException("Job offer not found!", "NOT_FOUND");
        }

        List<Notification> notifications = notificationDAO.findNotificationsByJobOfferId(jobOfferId);
        return notificationMapper.entityListToDTOList(notifications);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> getNotificationsByCompanyId(Long companyId) {
        Company company = companyDAO.findById(companyId);
        if (company == null) {
            throw new AppException("Company not found!", "NOT_FOUND");
        }

        List<Notification> notifications = notificationDAO.findNotificationsByCompanyId(companyId);
        return notificationMapper.entityListToDTOList(notifications);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> getNotificationsByUserIdAndType(Long userId, NotificationType type) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new AppException("User not found!", "NOT_FOUND");
        }

        List<Notification> notifications = notificationDAO.findNotificationsByUserIdAndType(userId, type);
        return notificationMapper.entityListToDTOList(notifications);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> findAll() {
        List<Notification> notifications = notificationDAO.findAll();
        return notificationMapper.entityListToDTOList(notifications);
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationResponseDTO findById(Long id) {
        Notification notification = notificationDAO.findById(id);
        if (notification == null) {
            throw new AppException("Notification not found!", "NOT_FOUND");
        }
        return notificationMapper.entityToDTO(notification);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Notification notification = notificationDAO.findById(id);
        if (notification == null) {
            throw new AppException("Notification not found!", "NOT_FOUND");
        }
        notificationDAO.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllByUserId(Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new AppException("User not found!", "NOT_FOUND");
        }

        List<Notification> notifications = notificationDAO.findNotificationsByUserId(userId);
        for (Notification notification : notifications) {
            notificationDAO.deleteById(notification.getId());
        }
    }
}
