package com.project.co.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.project.co.entity.S_attend;

public interface S_attendrepo extends JpaRepository<S_attend, Long> {

    @Modifying
    @Query(value="UPDATE s_attend SET days = days+1 WHERE student_id = ?1 AND course_id = ?2",nativeQuery=true)
    int attend(String Student_id,String Course_id);

    @Query(value = "SELECT * FROM s_attend WHERE course_id=?1", nativeQuery = true)
    List<S_attend> attendCourse(String courseId);

    @Query(value = "SELECT * FROM s_attend WHERE course_id = ?1 AND student_id = ?2", nativeQuery = true)
    S_attend getAttendance(String Course_id,String Student_id);
}
