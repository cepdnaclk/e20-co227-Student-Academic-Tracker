package com.project.co.service;

import com.project.co.dto.CombinedMarksDTO;
import com.project.co.repo.CombinedMarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@Service
public class CombinedMarksService {

    @Autowired
    private CombinedMarksRepository combinedMarksRepository;

    public List<CombinedMarksDTO> getCombinedMarks(String studentId, String courseId) {
        List<Object[]> results = combinedMarksRepository.findCombinedMarks(studentId, courseId);
        System.out.println(results);
        List<CombinedMarksDTO> combinedMarks = new ArrayList<>();
        for (Object[] result : results) {
            String source = (String) result[0];
            String studentIdResult = (String) result[1];
            String courseIdResult = (String) result[2];
            double percentage = (Double) result[3];

            combinedMarks.add(new CombinedMarksDTO(source, studentIdResult, courseIdResult, percentage));
        }
        return combinedMarks;
    }
}
