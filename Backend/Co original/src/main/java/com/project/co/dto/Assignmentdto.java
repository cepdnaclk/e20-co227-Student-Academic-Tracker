package com.project.co.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data


public class Assignmentdto {

    private Long id;
    private String assignmentName;
    private String deadLine;
    private String message;
    private String filePath;
    private String adminId; 
    private String courseId;
    private byte[] fileContent;
    private String fileType;

    public Assignmentdto(String assignmentName, String deadLine , String message , String filePath) {
        this.assignmentName = assignmentName;
        this.deadLine = deadLine;
        this.message = message;
        this.filePath = filePath;
    }


    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

}
