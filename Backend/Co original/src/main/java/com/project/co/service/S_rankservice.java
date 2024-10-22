package com.project.co.service;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.co.dto.S_rankdto;
import com.project.co.entity.S_rank;
import com.project.co.repo.S_rankrepo;

@Service
@Transactional

public class S_rankservice {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private S_rankrepo s_rankrepo;

    // print rank list
    public List<S_rankdto> print_result(){
        List<S_rank> rank_list = s_rankrepo.print_rank();
        return modelMapper.map(rank_list, new TypeToken<List<S_rankdto>>(){}.getType());
    }

}
