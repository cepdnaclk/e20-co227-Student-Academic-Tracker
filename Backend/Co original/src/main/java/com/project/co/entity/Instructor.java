package com.project.co.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class Instructor {

    @Id
    private String Instructor_id;

    private String Instructor_name;
    private String Instructor_status;
    private String Assign_lab;

}
