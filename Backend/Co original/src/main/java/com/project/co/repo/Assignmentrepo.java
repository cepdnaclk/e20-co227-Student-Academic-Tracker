package com.project.co.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.co.entity.Assignment;
import org.springframework.data.repository.query.Param;


public interface Assignmentrepo extends JpaRepository<Assignment, Long> {

    @Query(value = "SELECT * FROM assignment WHERE admin_id= ?1 AND course_id=?2", nativeQuery = true)
    List<Assignment> allAssignment(String adminId,String courseId);

    @Query(value = "SELECT * FROM assignment WHERE assignment_name = :assignmentName AND course_id = :courseId AND admin_id = :adminId", nativeQuery = true)
    Assignment findByAssignmentNameAndCourseIdAndAdminId(@Param("assignmentName") String assignmentName, @Param("courseId") String courseId, @Param("adminId") String adminId);

    @Query(value = "SELECT * FROM assignment WHERE assignment_name = :assignmentName AND course_id = :courseId AND admin_id = :adminId ", nativeQuery = true)
    Assignment findByAssignmentNameAndCourseIdAndAdminIdAndDeadLineAndMessage(
            @Param("assignmentName") String assignmentName,
            @Param("courseId") String courseId,
            @Param("adminId") String adminId);

    List<Assignment> findByCourseId(String courseId);
}
 