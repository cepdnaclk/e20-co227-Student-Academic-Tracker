
package com.project.co.service;

import com.project.co.entity.QuizMarkTotal;
import com.project.co.repo.QuizMarkTotalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizMarkTotalService  {

    @Autowired
    private QuizMarkTotalRepository quizMarkTotalRepository;

    public void saveOrUpdateQuizMarkTotal(String studentId, double percentage, String courseId) {
        QuizMarkTotal quizMarkTotal = quizMarkTotalRepository.findByStudentIdAndCourseId(studentId, courseId);

        if (quizMarkTotal != null) {
            // Update the existing entry
            quizMarkTotal.setPercentage(percentage);
        } else {
            // Create a new entry
            quizMarkTotal = new QuizMarkTotal();
            quizMarkTotal.setStudentId(studentId);
            quizMarkTotal.setPercentage(percentage);
            quizMarkTotal.setCourseId(courseId);
        }

        // Save (or update) the entry
        quizMarkTotalRepository.save(quizMarkTotal);
    }
}