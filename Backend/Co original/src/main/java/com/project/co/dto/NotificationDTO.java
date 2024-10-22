package com.project.co.dto;

import com.project.co.entity.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {

    private Long id;                  // Notification ID
    private String message;           // Notification message
    private NotificationType type;    // Type of the notification (ASSIGNMENT or QUIZ)
    private LocalDateTime createdAt;  // Timestamp of when the notification was created
    private String course_id;
    private boolean isRead;           // Status if the notification is read or not


    public NotificationDTO(Long id, String message, LocalDateTime createdAt, boolean isRead, NotificationType type, String course_id) {
        this.id = id;
        this.message = message;
        this.createdAt = createdAt;
        this.isRead = isRead;
        this.type = type;
        this.course_id =course_id;
    }
}
