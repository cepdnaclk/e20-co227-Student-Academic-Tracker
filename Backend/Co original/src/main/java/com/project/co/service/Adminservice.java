package com.project.co.service;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.co.entity.Admin;
import com.project.co.repo.Adminrepo;

@Service
@Transactional

public class Adminservice {
    @Autowired
    private Adminrepo adminrepo;


    // Handling login part
    public String login_lecturer(String Lecturer_id, String password) {
        try {
            // Fetch lecturer by id, throw exception if not found
            Admin admin = adminrepo.findById(Lecturer_id)
                .orElseThrow(() -> new NoSuchElementException());

            // Check if the password matches
            if (admin.getPassword().equals(password)) {
                return admin.getAdmin_name();
            } else {
                return "Password is wrong";
            }
        } catch (NoSuchElementException e) {
            // Handle the case where the lecturer is not found
            return "Admin not found";
        }
    }
}

