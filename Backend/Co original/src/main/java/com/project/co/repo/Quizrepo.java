package com.project.co.repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.co.entity.Quiz;

public interface Quizrepo extends JpaRepository<Quiz, Long> {

    @Query(value = "SELECT * FROM quiz WHERE quiz.admin_id = ?1 AND quiz.course_id = ?2", nativeQuery = true)
    List<Quiz> findQuizByAdminId(String adminId,String CourseID);

    @Query(value = "SELECT * FROM quiz WHERE quiz.admin_id = ?1 AND quiz.course_id = ?2 AND quiz.quiz_name=?3", nativeQuery = true)
    Quiz findQuiz(String adminId,String CourseID,String quizName);
    
    @Modifying
    @Query(value = "UPDATE quiz SET quiz.quiz_name = :value1, quiz.dead_line = :value2, quiz.message = :value3, quiz.quiz_code = :value4 WHERE quiz.admin_id = :adminId AND quiz.course_id = :courseId AND quiz.quiz_name = :quizName", nativeQuery = true)
    void updateQuiz(@Param("adminId") String adminId, 
                    @Param("courseId") String courseId, 
                    @Param("quizName") String quizName,
                    @Param("value1") String value1, 
                    @Param("value2") String value2, 
                    @Param("value3") String value3,
                    @Param("value4") int value4);

    @Query(value = "SELECT * FROM quiz WHERE quiz.course_id = ?1", nativeQuery = true)
    List<Quiz> findQuizById(String courseId);

}
