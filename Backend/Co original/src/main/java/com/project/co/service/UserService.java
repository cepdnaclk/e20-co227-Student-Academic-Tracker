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
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    public ModelMapper modelMapper;

    public UserDto saveUser(UserDto userDto){
        userRepo.save(modelMapper.map(userDto, User.class));
        return userDto;
    }

    public List<UserDto> getAllUsers(){
        List<User>userList = userRepo.findAll();
        return modelMapper.map(userList, new TypeToken<List<UserDto>>(){}.getType());
    }

    public UserDto updateUser(UserDto userDto){
        userRepo.save(modelMapper.map(userDto,User.class));
        return userDto;
    }

    public String deleteUser(String userID){
        if(userRepo.existsById(userID)){
            userRepo.deleteById(userID);
            return "Success";
        }
        else{
            return "Failed";
        }
    }

    public UserDto getUserByUserID(String userID){
        User user = userRepo.getUserByUserID(userID);
        return modelMapper.map(user, UserDto.class);
    }

}
