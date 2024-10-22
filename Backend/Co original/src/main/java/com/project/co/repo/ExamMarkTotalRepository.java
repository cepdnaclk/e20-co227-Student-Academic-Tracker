package com.project.co.repo;

import com.project.co.entity.ExamMarkTotal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamMarkTotalRepository extends JpaRepository<ExamMarkTotal, Long> {
    ExamMarkTotal findByStudentIdAndCourseId(String studentId, String courseId);
}