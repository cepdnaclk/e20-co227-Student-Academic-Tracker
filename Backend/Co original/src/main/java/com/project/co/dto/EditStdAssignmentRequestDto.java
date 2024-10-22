package com.project.co.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data

public class EditStdAssignmentRequestDto {

    private String assignmentName;
    private String studentId;
    private String courseId;
    private String filePath;
}
