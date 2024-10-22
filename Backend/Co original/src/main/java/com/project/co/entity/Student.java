package com.project.co.entity;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class Student implements UserDetails {

    @Id
    private String Student_id;

    private String Student_email;
    private String Student_name;
    private String Password;
    private String Student_Calendar;
    private String Student_Result;
    private int Student_Rank;




    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany
    //parent entity
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "Student_id", referencedColumnName ="Student_id" ),
            inverseJoinColumns = @JoinColumn(name = "CId",referencedColumnName = "Course_id")
    )
    private Set<Course> courses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(Student_id, student.Student_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Student_id);
    }
    // --------------------------------------------------------------------------- //
    // New variable for security purpose
    // New methods for security purpose

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return Student_id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
