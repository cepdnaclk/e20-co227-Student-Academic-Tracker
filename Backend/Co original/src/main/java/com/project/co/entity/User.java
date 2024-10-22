package com.project.co.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    private String reg_no;
    private String first_name;
    private  String last_name;
    private String email;
    private String country;
    private String city;
    private String mobile;


}
