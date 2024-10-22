package com.project.co.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data


public class CombinedMarksDTO {
    private String source; // 'exam', 'assignment', or 'quiz'
    private String studentId;
    private String courseId;
    private double percentage;

    public CombinedMarksDTO(String source, String studentId, String courseId, double percentage) {
        this.source = source;
        this.studentId = studentId;
        this.courseId = courseId;
        this.percentage = percentage;
    }


}
