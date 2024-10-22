package com.project.co.repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.co.entity.S_result;

public interface S_resultrepo extends JpaRepository<S_result,Long> {

    @Query(value="SELECT * FROM s_result WHERE student_id = ?1",nativeQuery=true)
    List<S_result> print_result(String Student_id);
}