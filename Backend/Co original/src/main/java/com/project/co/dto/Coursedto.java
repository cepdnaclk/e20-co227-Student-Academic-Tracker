package com.project.co.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

public class Coursedto {

    private String Course_id;
    private String Course_name;
    private String Course_description;
    private String Course_offered_by;

    public Coursedto(String Course_id, String Course_name, String Course_description, String Course_offered_by ) {
        this.Course_id = Course_id;
        this.Course_name = Course_name;
        this.Course_description = Course_description;
        this.Course_offered_by = Course_offered_by;

    }

}
