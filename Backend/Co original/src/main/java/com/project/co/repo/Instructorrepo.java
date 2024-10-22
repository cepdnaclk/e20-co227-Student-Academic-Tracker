package com.project.co.repo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.co.entity.Instructor;

public interface Instructorrepo extends JpaRepository<Instructor, String> {

}
