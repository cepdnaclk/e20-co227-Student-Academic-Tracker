package com.project.co.repo;

import com.project.co.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query(value = "SELECT n.* FROM Notification n JOIN student_course c ON n.Course_id = c.cid WHERE c.student_id = ?1 AND n.is_read = false", nativeQuery = true)
    List<Notification> findUnreadNotificationsByAdminIdAndCourseEnrollment(String student_id);
}
