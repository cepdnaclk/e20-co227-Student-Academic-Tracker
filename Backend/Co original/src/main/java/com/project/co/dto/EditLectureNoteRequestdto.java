package com.project.co.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data

public class EditLectureNoteRequestdto {

    private String lectureNoteName;
    private String adminId;
    private String courseId;
    private String publishDate;
    private String message;
    private String filePath;

}
