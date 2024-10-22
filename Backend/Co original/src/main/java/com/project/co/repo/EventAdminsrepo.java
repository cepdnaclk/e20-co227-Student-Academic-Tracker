package com.project.co.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.co.entity.EventAdmin;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface EventAdminsrepo extends JpaRepository<EventAdmin, Long> {
    @Query(value="SELECT * FROM eventadmin WHERE admin_id = ?1",nativeQuery=true)
    List<EventAdmin> findAllEventsByAdminId(String Admin_id);
}
