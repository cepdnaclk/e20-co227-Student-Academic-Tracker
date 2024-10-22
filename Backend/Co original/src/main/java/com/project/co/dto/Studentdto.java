package com.project.co.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Studentdto {

    private String Student_id;
    private String Student_email;
    private String Student_name;
    private String Password;
    private String Student_Calendar;
    private String Student_Result;
    private int Student_Rank;

    @Enumerated(EnumType.STRING)
    private Role role;


}
