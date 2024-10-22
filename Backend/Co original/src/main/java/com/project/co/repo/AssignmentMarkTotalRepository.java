package com.project.co.repo;

import com.project.co.entity.AssignmentMarkTotal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentMarkTotalRepository extends JpaRepository<AssignmentMarkTotal, Long> {
    AssignmentMarkTotal findByStudentIdAndCourseId(String studentId, String courseId);
}