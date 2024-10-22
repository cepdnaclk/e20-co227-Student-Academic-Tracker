package com.project.co.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@IdClass(SResultId.class)
public class S_result {

    @Id
    private String Student_id;

    @Id
    private String Course_id;

    private String Grade;
    private double Value;
}
