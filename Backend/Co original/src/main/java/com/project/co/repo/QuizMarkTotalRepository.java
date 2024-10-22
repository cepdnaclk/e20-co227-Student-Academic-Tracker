

package com.project.co.repo;

import com.project.co.entity.QuizMarkTotal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizMarkTotalRepository extends JpaRepository<QuizMarkTotal, Long> {
    QuizMarkTotal findByStudentIdAndCourseId(String studentId, String courseId);
}