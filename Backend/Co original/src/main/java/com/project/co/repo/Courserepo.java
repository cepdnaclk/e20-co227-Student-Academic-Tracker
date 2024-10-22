package com.project.co.repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.co.entity.Course;

@Repository
public interface  Courserepo extends JpaRepository<Course, String> {
    @Query(value = "SELECT * FROM student_course JOIN course ON student_course.cId = course.course_id WHERE student_course.student_id = ?1", nativeQuery = true)
    List<Course> findAllCoursesByStudentId(String Student_id);

    @Query(value = "SELECT COUNT(*) > 0 FROM student_course WHERE student_id = ?1 AND course_id = ?2", nativeQuery = true)
    boolean isEnrolledInCourse(String studentId, String courseId);

    @Query(value = "SELECT * FROM course WHERE course.course_offered_by = ?1", nativeQuery = true)
    List<Course> findAllCoursesByAdminId(String adminId);

    @Query(value = "SELECT * FROM course WHERE course.course_offered_by = ?1 AND course.course_id = ?2", nativeQuery = true)
    List<Course> findCoursesByAdminId(String adminId,String CourseID);

    @Query(value = "SELECT * FROM course",nativeQuery = true)
    List<Course> findAllRegisteringCourses();

    @Query(value = "SELECT * FROM course WHERE course_id = :courseId", nativeQuery = true)
    List<Course> findByCourseId(@Param("courseId") String courseId);
}
