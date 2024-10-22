package com.project.co.service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.co.dto.Instructordto;
import com.project.co.entity.Instructor;
import com.project.co.repo.Instructorrepo;

@Service
@Transactional

public class Instructorservice {
    @Autowired
    private Instructorrepo instructorrepo;
    @Autowired
    private ModelMapper modelMapper;

     public Instructordto saveuser(Instructordto instructordto){
        
        instructorrepo.save(modelMapper.map(instructordto,Instructor.class));
        return instructordto;
    }

}
