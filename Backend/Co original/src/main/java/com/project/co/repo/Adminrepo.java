package com.project.co.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.co.entity.Admin;
import com.project.co.entity.Student;

public interface Adminrepo  extends  JpaRepository<Admin, String>{
    @Query(value="SELECT * FROM lecturer WHERE lecturer_id = ?1 AND password = ?2",nativeQuery=true)
    Student login_lecturer(String Lecturer_id,String password);
}
