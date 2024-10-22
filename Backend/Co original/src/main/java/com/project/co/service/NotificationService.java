package com.project.co.service;

import com.project.co.dto.NotificationDTO;
import com.project.co.entity.Notification;
import com.project.co.repo.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;


    public void saveNotification(NotificationDTO notificationDTO) {
        Notification notification = new Notification();
        notification.setCourse_id(notificationDTO.getCourse_id());
        notification.setMessage(notificationDTO.getMessage());
        notification.setCreatedAt(notificationDTO.getCreatedAt());
        notification.setRead(notificationDTO.isRead());
        notification.setType(notificationDTO.getType());

        notificationRepository.save(notification);
    }

    public List<Notification> getUnreadNotificationsByAdminId(String student_id) {
        return notificationRepository.findUnreadNotificationsByAdminIdAndCourseEnrollment(student_id);
    }

    /*public void markNotificationAsRead(String studentId, Long notificationId) {
        StudentNotification studentNotification = studentNotificationRepository
                .findByStudentIdAndNotificationId(studentId, notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found for this student"));

        studentNotification.setRead(true);
        studentNotificationRepository.save(studentNotification);
    }*/
}
