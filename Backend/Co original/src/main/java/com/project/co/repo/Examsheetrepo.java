package com.project.co.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import com.project.co.entity.Examsheet;

public interface Examsheetrepo extends JpaRepository<Examsheet, Long> {

    @Query(value = "SELECT * FROM examsheet WHERE examsheet.admin_id = ?1 AND examsheet.course_id = ?2 AND examsheet.type=?3", nativeQuery = true)
    List<Examsheet> findsheet(String adminId,String courseId,String type);

}
