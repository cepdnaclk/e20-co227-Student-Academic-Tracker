package com.project.co.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "quizMarkTotal")

public class QuizMarkTotal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates unique values for the id
    private Long id;

    @Column(name = "student_id", nullable = false) // Maps to student_id column, can't be null
    private String studentId;

    @Column(name = "percentage", nullable = false) // Maps to percentage column, can't be null
    private Double percentage;

    @Column(name = "courseId", nullable = false) // Maps to percentage column, can't be null
    private String courseId;
}