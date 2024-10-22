package com.project.co.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.co.dto.S_rankdto;
import com.project.co.dto.S_resultrdto;
import com.project.co.entity.S_rank;
import com.project.co.entity.S_result;
import com.project.co.repo.S_rankrepo;
import com.project.co.repo.S_resultrepo;

@Service
@Transactional

public class S_resultservice {

    @Autowired
    private S_resultrepo s_resultrepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private S_rankrepo s_rankrepo;

    // Grading system
    public S_resultrdto saveuser(S_resultrdto s_resultrdto){

        String Grade;
        Grade = s_resultrdto.getGrade();

        if(Grade.equals("A") || Grade.equals("A+")){
            s_resultrdto.setValue(4);
        }
        if(Grade.equals("A-")){
            s_resultrdto.setValue(3.7);
        }
        if(Grade.equals("B+") ){
            s_resultrdto.setValue(3.3);
        }
        if(Grade.equals("B") ){
            s_resultrdto.setValue(3);
        }
        if(Grade.equals("B-") ){
            s_resultrdto.setValue(2.7);
        }
        if(Grade.equals("C+") ){
            s_resultrdto.setValue(2.3);
        }
        if(Grade.equals("C") ){
            s_resultrdto.setValue(2);
        }
        if(Grade.equals("C-") ){
            s_resultrdto.setValue(1.7);
        }
        if(Grade.equals("D+") ){
            s_resultrdto.setValue(1.3);
        }

        if(Grade.equals("D") ){
            s_resultrdto.setValue(1);
        }

        if(Grade.equals("E") ){
            s_resultrdto.setValue(0);
        }

        s_resultrepo.save(modelMapper.map(s_resultrdto,S_result.class));
        return s_resultrdto;
    }

    // print result
    public List<S_resultrdto> print_result(String Student_id){
        List<S_result> result_list = s_resultrepo.print_result(Student_id);
        return modelMapper.map(result_list, new TypeToken<List<S_resultrdto>>(){}.getType());
    }

    // Get average value
    public String average_result(String Student_id){
        List<S_result> result_list = s_resultrepo.print_result(Student_id);
        double Sum_value=0; 
        int count=0;
        for (S_result result : result_list) {
            Sum_value+=result.getValue();
            count++;
        }

        double averageValue = Sum_value / count;
        
        // Create object from S_rank
        S_rankdto s_rankdto = new S_rankdto();
        s_rankdto.setStudent_id(Student_id);
        s_rankdto.setAverage_result(averageValue);
        s_rankrepo.save(modelMapper.map(s_rankdto,S_rank.class));
        
        return String.valueOf(averageValue);
    }

}
