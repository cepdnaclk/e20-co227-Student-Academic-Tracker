package com.project.co.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class LectureNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lectureNoteName;
    private String publishDate;
    private String message;
    private String filePath;
    private String adminId;
    private String courseId;
    private byte[] fileContent;
    private String fileType;

}
