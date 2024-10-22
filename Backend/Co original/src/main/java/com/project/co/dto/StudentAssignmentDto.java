package com.project.co.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data

public class StudentAssignmentDto {

    private Long id;
    private String assignmentName;
    private String filePath;
    private String studentId;
    private String courseId;
    private byte[] fileContent;
    private String fileType;
    private Long assignmentId;


    public StudentAssignmentDto(String assignmentName, String filePath) {
        this.assignmentName = assignmentName;
        this.filePath = filePath;
    }


    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

}
