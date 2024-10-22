package com.project.co.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.co.entity.StudentAssignment;

import org.springframework.data.repository.query.Param;

public interface StudentAssignmentRepo extends JpaRepository<StudentAssignment, Long>{

    @Query(value = "SELECT * FROM student_assignment WHERE student_id= ?1 AND course_id=?2 AND assignment_id=?3", nativeQuery = true)
    List<StudentAssignment> allStdAssignment(String studentId, String courseId ,Long assignmentId);

    @Query(value = "SELECT * FROM student_assignment WHERE assignment_name = :assignmentName AND course_id = :courseId AND student_id = :studentId ", nativeQuery = true)
    StudentAssignment findByAssignmentNameAndCourseIdAndStudentId(@Param("assignmentName") String assignmentName, @Param("courseId") String courseId, @Param("studentId") String studentId);

}
