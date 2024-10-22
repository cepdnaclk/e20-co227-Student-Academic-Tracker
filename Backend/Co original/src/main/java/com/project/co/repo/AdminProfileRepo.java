package com.project.co.repo;

import com.project.co.entity.AdminProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminProfileRepo extends JpaRepository<AdminProfile, String> {

    @Query(value = "SELECT * FROM ADMIN_PROFILE WHERE adminID = ?1", nativeQuery = true)
    AdminProfile getAdminByUserID(String adminID);

}