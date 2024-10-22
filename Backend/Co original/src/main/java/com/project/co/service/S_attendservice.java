package com.project.co.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.co.dto.S_attenddto;
import com.project.co.entity.S_attend;
import com.project.co.repo.S_attendrepo;


@Service
@Transactional

public class S_attendservice {

    @Autowired
    private S_attendrepo s_attendrepo;

    @Autowired
    private ModelMapper modelMapper;

    
    public S_attenddto saveuser(S_attenddto s_attenddto){

        s_attenddto.setDays(0);
        s_attendrepo.save(modelMapper.map(s_attenddto,S_attend.class));
        return s_attenddto;
    }

    public List<S_attenddto> attendCourse(String CourseID){
        List<S_attend> attend = s_attendrepo.attendCourse(CourseID);
        return modelMapper.map(attend, new TypeToken<List<S_attenddto>>(){}.getType());
    }

    // Update value in days
    public void attend(String Student_id,String Course_id){
        s_attendrepo.attend(Student_id,Course_id);
    }

    public S_attenddto getAttendance(String Student_id, String Course_id){
        S_attend s_attend = s_attendrepo.getAttendance(Course_id,Student_id);
        return modelMapper.map(s_attend, S_attenddto.class);
    }

}
