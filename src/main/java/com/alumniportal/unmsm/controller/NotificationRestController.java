package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.response.NotificationResponseDTO;
import com.alumniportal.unmsm.service.interfaces.INotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationRestController {

    private final INotificationService notificationService;


    @PatchMapping("/{id}/mark-as-read")
    @Operation(summary = "Mark notification as read")
    @ApiResponse(responseCode = "200", description = "Notification marked as read")
    public ResponseEntity<NotificationResponseDTO> markAsRead(@PathVariable Long id) {
        NotificationResponseDTO response = notificationService.markAsRead(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/mark-as-unread")
    @Operation(summary = "Mark notification as unread")
    @ApiResponse(responseCode = "200", description = "Notification marked as unread")
    public ResponseEntity<NotificationResponseDTO> markAsUnread(@PathVariable Long id) {
        NotificationResponseDTO response = notificationService.markAsUnread(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/user/{userId}/mark-all-as-read")
    @Operation(summary = "Mark all notifications as read for a user")
    @ApiResponse(responseCode = "204", description = "All notifications marked as read")
    public ResponseEntity<Void> markAllAsReadByUserId(@PathVariable Long userId) {
        notificationService.markAllAsReadByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all notifications for a user")
    @ApiResponse(responseCode = "200", description = "List of notifications")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByUserId(@PathVariable Long userId) {
        List<NotificationResponseDTO> response = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}/unread")
    @Operation(summary = "Get unread notifications for a user")
    @ApiResponse(responseCode = "200", description = "List of unread notifications")
    public ResponseEntity<List<NotificationResponseDTO>> getUnreadNotificationsByUserId(@PathVariable Long userId) {
        List<NotificationResponseDTO> response = notificationService.getUnreadNotificationsByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}/read")
    @Operation(summary = "Get read notifications for a user")
    @ApiResponse(responseCode = "200", description = "List of read notifications")
    public ResponseEntity<List<NotificationResponseDTO>> getReadNotificationsByUserId(@PathVariable Long userId) {
        List<NotificationResponseDTO> response = notificationService.getReadNotificationsByUserId(userId);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get notification by id")
    @ApiResponse(responseCode = "200", description = "Notification found")
    public ResponseEntity<NotificationResponseDTO> findById(@PathVariable Long id) {
        NotificationResponseDTO response = notificationService.findById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete notification by id")
    @ApiResponse(responseCode = "204", description = "Notification deleted")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        notificationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/user/{userId}")
    @Operation(summary = "Delete all notifications for a user")
    @ApiResponse(responseCode = "204", description = "All notifications deleted")
    public ResponseEntity<Void> deleteAllByUserId(@PathVariable Long userId) {
        notificationService.deleteAllByUserId(userId);
        return ResponseEntity.noContent().build();
    }
}
