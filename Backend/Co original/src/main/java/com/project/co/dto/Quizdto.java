package com.project.co.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data


public class Quizdto {

    private long id;
    private String Admin_id;
    private String Course_id;
    private String quizName;
    private String deadLine;
    private int quizCode;
    private String message; 
}
