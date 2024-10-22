package com.project.co.entity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor

@Data
@Builder
@Entity
@Table(name = "course_weights")
public class CourseWeight {

    @Id
    private String courseId; // Unique identifier for the course

    private double examPercentage; // Percentage weight for exams
    private double assignmentPercentage; // Percentage weight for assignments
    private double quizPercentage; // Percentage weight for quizzes


    public CourseWeight(String courseId, double examPercentage, double assignmentPercentage, double quizPercentage) {
        this.courseId = courseId;
        this.examPercentage = examPercentage;
        this.assignmentPercentage = assignmentPercentage;
        this.quizPercentage = quizPercentage;
    }

}
