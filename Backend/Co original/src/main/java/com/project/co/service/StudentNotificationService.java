package com.project.co.service;

import com.project.co.dto.StudentNotificationDTO;
import com.project.co.entity.Notification;
import com.project.co.entity.StudentNotification;
import com.project.co.repo.StudentNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentNotificationService {

    @Autowired
    private StudentNotificationRepository studentnotificationRepository;


    public void saveNotification(StudentNotificationDTO notificationDTO) {
        StudentNotification notification = new StudentNotification();
        notification.setCourse_id(notificationDTO.getCourse_id());
        notification.setMessage(notificationDTO.getMessage());
        notification.setRead(notificationDTO.isRead());

        studentnotificationRepository.save(notification);
    }

    public List<StudentNotification> getUnreadNotificationsStudentByAdminId(String student_id) {
        return studentnotificationRepository.findUnreadNotificationsByStudentId(student_id);
    }

    public void markNotificationAsRead(String studentId, Long assignmentId) {
        List<StudentNotification> studentNotifications = studentnotificationRepository
                .findByStudentIdAndNotificationId(studentId, assignmentId);

        if (studentNotifications.isEmpty()) {
            throw new RuntimeException("Notification not found for this student");
        }

        studentNotifications.forEach(studentNotification -> {
            studentNotification.setRead(true);
            studentnotificationRepository.save(studentNotification);
        });
    }
}