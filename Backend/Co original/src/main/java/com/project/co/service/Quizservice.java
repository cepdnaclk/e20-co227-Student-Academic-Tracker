package com.project.co.service;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.co.dto.Quizdto;
import com.project.co.entity.Quiz;
import com.project.co.repo.Quizrepo;


@Service
@Transactional
public class Quizservice {

    @Autowired
    private Quizrepo quizrepo;
    @Autowired
    private ModelMapper modelMapper;
    
    public Quizdto saveQuiz(Quizdto quizdto){

        quizrepo.save(modelMapper.map(quizdto,Quiz.class));
        return quizdto;
    }

    public List<Quizdto> findQuizByAdminId(String adminId,String CourseID){
        List<Quiz> quizs = quizrepo.findQuizByAdminId(adminId, CourseID);

        // Convert entities to DTOs
        return modelMapper.map(quizs, new TypeToken<List<Quizdto>>(){}.getType());
    }

    public Quizdto findQuiz(String adminId,String CourseID,String quizName){
        Quiz quiz = quizrepo.findQuiz(adminId, CourseID, quizName);
        // Convert entities to DTOs
        return modelMapper.map(quiz, Quizdto.class);
    }

    public void updateQuiz(String adminId, String courseId, String quizName, String value1, String value2, String value3,int value4) {
        quizrepo.updateQuiz(adminId, courseId, quizName, value1, value2, value3, value4);
    }

    public void deleteQuiz(String adminId,String CourseID,String quizName){
        Quiz quiz = quizrepo.findQuiz(adminId, CourseID, quizName);
        quizrepo.delete(quiz);

    }

    public List<Quizdto> findQuizById( String CourseID){
        List<Quiz> quiz = quizrepo.findQuizById(CourseID);

        // Convert entities to DTOs
        return modelMapper.map(quiz, new TypeToken<List<Quizdto>>(){}.getType());

    }

}
