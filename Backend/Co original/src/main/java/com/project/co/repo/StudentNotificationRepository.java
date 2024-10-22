package com.project.co.repo;

import com.project.co.entity.Notification;
import com.project.co.entity.StudentNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentNotificationRepository extends JpaRepository<StudentNotification, Long> {
    @Query("SELECT sn FROM StudentNotification sn WHERE sn.student.id = :studentId AND sn.isRead = false")
    List<StudentNotification> findUnreadNotificationsByStudentId(@Param("studentId") String studentId);

    @Query(nativeQuery = true, value = "SELECT * FROM student_notification " +
            "WHERE student_id = :studentId AND assignment_id = :assignmentId")
    List<StudentNotification> findByStudentIdAndNotificationId(@Param("studentId") String studentId, @Param("assignmentId") Long assignmentId);

}
