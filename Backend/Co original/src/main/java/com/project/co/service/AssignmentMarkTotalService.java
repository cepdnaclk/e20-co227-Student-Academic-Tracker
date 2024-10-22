
package com.project.co.service;

import com.project.co.entity.AssignmentMarkTotal;
import com.project.co.repo.AssignmentMarkTotalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignmentMarkTotalService  {

    @Autowired
    private AssignmentMarkTotalRepository assignmentMarkTotalRepository;

    public void saveOrUpdateAssignmentMarkTotal(String studentId, double percentage, String courseId) {
        AssignmentMarkTotal assignmentMarkTotal = assignmentMarkTotalRepository.findByStudentIdAndCourseId(studentId, courseId);

        if (assignmentMarkTotal != null) {
            // Update the existing entry
            assignmentMarkTotal.setPercentage(percentage);
        } else {
            // Create a new entry
            assignmentMarkTotal = new AssignmentMarkTotal();
            assignmentMarkTotal.setStudentId(studentId);
            assignmentMarkTotal.setPercentage(percentage);
            assignmentMarkTotal.setCourseId(courseId);
        }

        // Save (or update) the entry
        assignmentMarkTotalRepository.save(assignmentMarkTotal);
    }
}