package com.project.co.repo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.co.entity.Department;

public interface  Departmentrepo extends JpaRepository<Department, String> {
}
