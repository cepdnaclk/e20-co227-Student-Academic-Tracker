package com.project.co.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data


public class EditAssignmentRequestdto {
    private String assignmentName;
    private String adminId;
    private String courseId;
    private String deadLine;
    private String message;
    private String filePath;

    // Getters and setters
}
