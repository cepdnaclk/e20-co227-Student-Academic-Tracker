package com.project.co.entity;

import java.io.Serializable;
import java.util.Objects;

public class SResultId implements Serializable {

    private String Student_id;
    private String Course_id;

    public SResultId() {}

    public SResultId(String Student_id, String Course_id) {
        this.Student_id = Student_id;
        this.Course_id = Course_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SResultId that = (SResultId) o;
        return Student_id.equals(that.Student_id) &&
                Course_id.equals(that.Course_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Student_id, Course_id);
    }
}
