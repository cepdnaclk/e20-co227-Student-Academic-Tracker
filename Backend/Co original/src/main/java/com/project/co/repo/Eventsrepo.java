package com.project.co.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.co.entity.Event;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface Eventsrepo extends JpaRepository<Event, Long> {
    @Query(value="SELECT * FROM event WHERE student_id = ?1",nativeQuery=true)
    List<Event> findAllEventsByStudentId(String Student_id);
}
