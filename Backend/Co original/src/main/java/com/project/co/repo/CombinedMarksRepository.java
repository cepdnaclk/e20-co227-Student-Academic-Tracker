package com.project.co.repo;

import com.project.co.entity.CombinedMarks;
import com.project.co.entity.ExamMarkTotal;
import com.project.co.entity.AssignmentMarkTotal;
import com.project.co.entity.QuizMarkTotal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CombinedMarksRepository extends JpaRepository<CombinedMarks, Long> {

    @Query(value = """
            SELECT 'exam' AS source, student_id, course_id, percentage
            FROM exam_mark_total
            WHERE student_id = :studentId AND course_id = :courseId
            UNION ALL
            SELECT 'assignment' AS source, student_id, course_id, percentage
            FROM assignment_mark_total
            WHERE student_id = :studentId AND course_id = :courseId
            UNION ALL
            SELECT 'quiz' AS source, student_id, course_id, percentage
            FROM quiz_mark_total
            WHERE student_id = :studentId AND course_id = :courseId
            """, nativeQuery = true)
    List<Object[]> findCombinedMarks(@Param("studentId") String studentId, @Param("courseId") String courseId);
}
