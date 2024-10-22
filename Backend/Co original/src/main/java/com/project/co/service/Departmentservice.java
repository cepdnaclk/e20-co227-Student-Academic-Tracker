package com.project.co.service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.co.dto.Departmentdto;
import com.project.co.entity.Department;
import com.project.co.repo.Departmentrepo;

@Service
@Transactional
public class Departmentservice {
    @Autowired
    private Departmentrepo departmentrepo;
    @Autowired
    private ModelMapper modelMapper;

     public Departmentdto saveuser(Departmentdto departmentdto){
        
        departmentrepo.save(modelMapper.map(departmentdto,Department.class));
        return departmentdto;
    }
}
