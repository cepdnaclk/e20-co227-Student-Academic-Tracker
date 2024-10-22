package com.project.co.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.co.entity.S_rank;

public interface S_rankrepo extends JpaRepository<S_rank, String> {

    @Query(value="SELECT * FROM s_rank",nativeQuery=true)
    List<S_rank> print_rank();

}
