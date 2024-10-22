package com.project.co.service;

import com.project.co.entity.CourseWeight;
import com.project.co.repo.CourseWeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseWeightService {

    @Autowired
    private CourseWeightRepository courseWeightRepository;

    public List<CourseWeight> getAllWeights() {
        return courseWeightRepository.findAll();
    }

    public CourseWeight getWeightByCourseId(String courseId) {
        return courseWeightRepository.findById(courseId).orElse(null);
    }

    public CourseWeight saveWeight(CourseWeight courseWeight) {
        return courseWeightRepository.save(courseWeight);
    }

}
