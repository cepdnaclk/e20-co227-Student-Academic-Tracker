package com.project.co.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "course")

public class Course {

    @Id
    private String Course_id;

    private String Course_Name;
    private String Course_Description;
    private String Course_offered_by;

    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    // course is the child entity so all mapping are done in parent entity which is student entity
    private Set<Student> students;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(Course_id, course.Course_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Course_id);
    }
}
