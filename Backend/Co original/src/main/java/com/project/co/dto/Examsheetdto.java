package com.project.co.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data

public class Examsheetdto {
    
    private Long id;

    private String url;
    private String adminId;
    private String courseId;
    private String type;
}
