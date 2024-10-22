package com.project.co.repo;

import com.project.co.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, String> {

    @Query(value = "SELECT * FROM USER WHERE reg_no = ?1", nativeQuery = true)
    User getUserByUserID(String reg_no);

}