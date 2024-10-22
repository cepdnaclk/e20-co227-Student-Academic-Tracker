package com.project.co.repo;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.co.entity.Student;
import org.springframework.data.repository.query.Param;


public interface Studentrepo extends JpaRepository<Student, String> {

    @Query(value="SELECT * FROM student WHERE student_id = ?1 AND password= ?2",nativeQuery=true)
    Student login_student(String Student_id,String Password);


    // For security
    @Query(value="SELECT * FROM student WHERE student_id = ?1", nativeQuery=true)
    Optional<Student> findByStudent_id(String student_id);

    @Query(value = "SELECT s.* FROM student s JOIN student_course sc ON s.student_id = sc.student_id WHERE sc.cid = ?1", nativeQuery = true)
    List<Student> findStudentsByCourseId(String courseId);

}
