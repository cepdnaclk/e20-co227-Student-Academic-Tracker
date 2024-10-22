package com.project.co.service;

import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;


import com.project.co.dto.*;
import com.project.co.repo.*;
import com.project.co.entity.*;

@Service
@Transactional
public class AdminProfileService {
    @Autowired
    private AdminProfileRepo adminProfileRepo;

    @Autowired
    public ModelMapper modelMapper;

    public AdminProfileDto saveAdminProfile(AdminProfileDto adminProfileDto){
        adminProfileRepo.save(modelMapper.map(adminProfileDto, AdminProfile.class));
        return adminProfileDto;
    }

    public List<AdminProfileDto> getAllAdmins(){
        List<AdminProfile>userList = adminProfileRepo.findAll();
        return modelMapper.map(userList, new TypeToken<List<AdminProfileDto>>(){}.getType());
    }

    public AdminProfileDto updateAdminProfile(AdminProfileDto adminProfileDto){
        adminProfileRepo.save(modelMapper.map(adminProfileDto, AdminProfile.class));
        return adminProfileDto;
    }

    public String deleteAdmin(String adminID){
        if(adminProfileRepo.existsById(adminID)){
            adminProfileRepo.deleteById(adminID);
            return "Success";
        }
        else{
            return "Failed";
        }
    }

    public AdminProfileDto getAdminByUserID(String adminID){
        AdminProfile adminProfile = adminProfileRepo.getAdminByUserID(adminID);
        return modelMapper.map(adminProfile, AdminProfileDto.class);
    }

}
