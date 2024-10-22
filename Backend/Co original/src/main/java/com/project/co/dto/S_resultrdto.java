package com.project.co.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class S_resultrdto {

    private long id;
    private String Student_id;
    private String Course_id;
    private String Grade;
    private double Value;

}
