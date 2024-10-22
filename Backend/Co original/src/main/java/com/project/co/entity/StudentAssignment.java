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

public class StudentAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String assignmentName;
    private String filePath;
    private String studentId;
    private String courseId;
    private byte[] fileContent;
    private String fileType;
    private Long assignmentId;


}