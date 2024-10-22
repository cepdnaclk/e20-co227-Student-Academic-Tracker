package com.project.co.service;

import com.project.co.entity.ExamMarkTotal;
import com.project.co.repo.ExamMarkTotalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamMarkTotalService {

    @Autowired
    private ExamMarkTotalRepository examMarkTotalRepository;

    public void saveOrUpdateExamMarkTotal(String studentId, double percentage, String courseId) {
        // Retrieve the existing entry by both studentId and courseId
        ExamMarkTotal examMarkTotal = examMarkTotalRepository.findByStudentIdAndCourseId(studentId, courseId);

        if (examMarkTotal != null) {
            // Update the existing entry
            examMarkTotal.setPercentage(percentage);
        } else {
            // Create a new entry
            examMarkTotal = new ExamMarkTotal();
            examMarkTotal.setStudentId(studentId);
            examMarkTotal.setPercentage(percentage);
            examMarkTotal.setCourseId(courseId);
        }

        // Save (or update) the entry
        examMarkTotalRepository.save(examMarkTotal);
    }
}