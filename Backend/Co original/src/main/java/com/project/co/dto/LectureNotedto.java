package com.project.co.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class LectureNotedto {

    private Long id;
    private String lectureNoteName;
    private String publishDate;
    private String message;
    private String filePath;
    private String adminId;
    private String courseId;
    private byte[] fileContent;
    private String fileType;

    public LectureNotedto(String lectureNoteName, String publishDate , String message , String filePath) {
        this.lectureNoteName = lectureNoteName;
        this.publishDate = publishDate;
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
