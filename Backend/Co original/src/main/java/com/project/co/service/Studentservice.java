package com.project.co.service;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.co.dto.Role;
import com.project.co.dto.Studentdto;
import com.project.co.entity.Student;
import com.project.co.repo.Studentrepo;


@Service
@Transactional

public class Studentservice {

    @Autowired
    private Studentrepo studentrepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public Studentdto saveuser(Studentdto studentdto) {
        studentdto.setPassword(passwordEncoder.encode(studentdto.getPassword()));
        studentdto.setRole(Role.STUDENT);

        studentrepo.save(modelMapper.map(studentdto, Student.class));
        
        // Return the saved StudentDTO
        return studentdto;
    }
    

    // Handling login part
    public String login_student(String Student_id, String Password) {
        try {
            // Fetch student by id, throw exception if not found
            Student student = studentrepo.findById(Student_id)
                .orElseThrow(() -> new NoSuchElementException());
    
            // Check if the student name matches
            if ((passwordEncoder.matches(Password, student.getPassword()))) {
                return student.getStudent_name();
            } else {
                return "Password is wrong";
            }
        } catch (NoSuchElementException e) {
            // Handle the case where the student is not found
            return "Student not found";
        }
    }
    

}
