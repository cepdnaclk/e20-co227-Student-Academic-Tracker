package com.project.co.dto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Admindto {

    private String Admin_id;
    private String Password;
    private String Admin_name;
    private String Status;

    @Enumerated(EnumType.STRING)
    private Role role;
}
