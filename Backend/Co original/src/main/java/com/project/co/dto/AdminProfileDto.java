package com.project.co.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminProfileDto {
    private String adminID;
    private String first_name;
    private  String last_name;
    private String email;
    private String country;
    private String city;
    private String mobile;

}