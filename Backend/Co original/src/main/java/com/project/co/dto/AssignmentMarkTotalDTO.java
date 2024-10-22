package com.project.co.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

public class AssignmentMarkTotalDTO {

    private String studentId;
    private double percentage;
    private String courseId;

}