package com.project.co.service;

import com.project.co.dto.Enrollmentdto;
import com.project.co.dto.Studentdto;
import com.project.co.entity.Course;
import com.project.co.entity.Student;
import com.project.co.repo.Courserepo;
import com.project.co.repo.Studentrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private Studentrepo studentRepository;

    @Autowired
    private Courserepo courseRepository;

    @Override
    public Studentdto enrollStudent(Enrollmentdto enrollmentDTO) {
        Student student = studentRepository.findById(enrollmentDTO.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(enrollmentDTO.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        student.getCourses().add(course);
        course.getStudents().add(student);

        studentRepository.save(student);
        courseRepository.save(course);

        // Convert the Student entity to Studentdto and return it
        return convertToStudentdto(student);
    }

    private Studentdto convertToStudentdto(Student student) {
        Studentdto studentdto = new Studentdto();
        studentdto.setStudent_id(student.getStudent_id());
        studentdto.setStudent_name(student.getStudent_name());
        // Add other fields as needed
        return studentdto;
    }
}
