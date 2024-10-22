package com.project.co.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentNotificationDTO {
    private Long id;
    private String studentId;
    private Long notificationId;
    private boolean isRead;
    private String course_id;
    private String message;
}
