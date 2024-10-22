package com.project.co.service;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.co.dto.Coursedto;
import com.project.co.entity.Course;
import com.project.co.repo.Courserepo;

@Service
@Transactional
public class Courseservice {
    @Autowired
    private Courserepo courserepo;
    @Autowired
    private ModelMapper modelMapper;

    public Coursedto saveuser(Coursedto coursedto){

        courserepo.save(modelMapper.map(coursedto,Course.class));
        return coursedto;
    }

    public List<Coursedto> findAllCoursesByStudentId(String studentId){
        List<Course> courses = courserepo.findAllCoursesByStudentId(studentId);

        // Convert entities to DTOs
        return courses.stream()
                .map(course -> new Coursedto(course.getCourse_id(),course.getCourse_Name(),course.getCourse_Description(), course.getCourse_offered_by()))
                .collect(Collectors.toList());

    }

    public boolean isEnrolledInCourse(String studentId, String courseId) {
        return courserepo.isEnrolledInCourse(studentId, courseId);
    }

    public List<Coursedto> findAllCoursesByAdminId(String adminId){
        List<Course> courses = courserepo.findAllCoursesByAdminId(adminId);

        // Convert entities to DTOs
        return courses.stream()
                .map(course -> new Coursedto(course.getCourse_id(),course.getCourse_Name(),course.getCourse_Description(), course.getCourse_offered_by()))
                .collect(Collectors.toList());

    }

    public List<Coursedto> findCoursesByAdminId(String adminId, String CourseID){
        List<Course> courses = courserepo.findCoursesByAdminId(adminId,CourseID);

        // Convert entities to DTOs
        return modelMapper.map(courses, new TypeToken<List<Coursedto>>(){}.getType());

    }

    public List<Coursedto> findAllRegisteringCourses(){
        List<Course> courses = courserepo.findAllRegisteringCourses();
        return modelMapper.map(courses, new TypeToken<List<Coursedto>>(){}.getType());
    }

    public List<Coursedto> offeredById(String courseId) {
        // Assuming `CourseEntity` is your entity class representing the course table
        List<Course> courses = courserepo.findByCourseId(courseId);

        if (courses.isEmpty()) {
            throw new EntityNotFoundException("No courses found for the given courseId: " + courseId);
        }

        // Map the CourseEntity objects to Coursedto objects
        List<Coursedto> courseDtos = courses.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return courseDtos;
    }

    // Method to convert CourseEntity to Coursedto
    private Coursedto convertToDto(Course course) {
        Coursedto dto = new Coursedto();
        dto.setCourse_offered_by(course.getCourse_offered_by()); // Assuming you have this field in Coursedto
        dto.setCourse_id(course.getCourse_id());
        dto.setCourse_name(course.getCourse_Name());
        // Set other fields as necessary
        return dto;
    }
}

