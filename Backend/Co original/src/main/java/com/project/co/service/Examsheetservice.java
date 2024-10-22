package com.project.co.service;
import java.io.IOException;
import java.util.List;

import com.project.co.repo.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.co.dto.Examsheetdto;
import com.project.co.entity.Examsheet;


import java.io.File;

@Service
@Transactional
public class Examsheetservice {

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    public Examsheetrepo examsheetrepo;

    public List<Examsheet> findsheet(String adminId,String courseId,String type){
        List<Examsheet> sheet = examsheetrepo.findsheet(adminId, courseId, type);

        // Convert entities to DTOs
        return modelMapper.map(sheet, new TypeToken<List<Examsheetdto>>(){}.getType());
    }
    

}
