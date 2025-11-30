package com.alumniportal.unmsm.repository;

import com.alumniportal.unmsm.model.Notification;
import com.alumniportal.unmsm.model.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select n from Notification n where n.user.id = ?1 order by n.createdAt desc")
    List<Notification> findNotificationsByUserId(Long userId);

    @Query("select n from Notification n where n.user.id = ?1 and n.isRead = false order by n.createdAt desc")
    List<Notification> findUnreadNotificationsByUserId(Long userId);

    @Query("select n from Notification n where n.user.id = ?1 and n.isRead = true order by n.createdAt desc")
    List<Notification> findReadNotificationsByUserId(Long userId);

    @Query("select count(n) from Notification n where n.user.id = ?1 and n.isRead = false")
    Long countUnreadNotificationsByUserId(Long userId);

    @Query("select n from Notification n where n.jobOffer.id = ?1")
    List<Notification> findNotificationsByJobOfferId(Long jobOfferId);

    @Query("select n from Notification n where n.company.id = ?1 order by n.createdAt desc")
    List<Notification> findNotificationsByCompanyId(Long companyId);

    @Query("select n from Notification n where n.user.id = ?1 and n.type = ?2 order by n.createdAt desc")
    List<Notification> findNotificationsByUserIdAndType(Long userId, NotificationType type);
}

