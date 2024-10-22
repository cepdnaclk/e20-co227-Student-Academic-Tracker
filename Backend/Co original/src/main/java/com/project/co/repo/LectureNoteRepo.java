package com.project.co.repo;

import java.util.List;

import com.project.co.entity.LectureNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.co.entity.LectureNote;
/*
public interface LectureNoteRepo extends JpaRepository<LectureNote, Long> {

    @Query(value = "SELECT * FROM lecture_note WHERE course_id = ?1", nativeQuery = true)
    List<LectureNote> findAllByCourseId(String courseId);

    @Query(value = "SELECT * FROM lecture_note WHERE lecture_note_name = :lectureNoteName AND course_id = :courseId", nativeQuery = true)
    LectureNote findByLectureNoteNameAndCourseId(
            @Param("lectureNoteName") String lectureNoteName,
            @Param("courseId") String courseId);

    @Query(value = "SELECT * FROM lecture_note WHERE lecture_note_name = :lectureNoteName AND course_id = :courseId AND admin_id = :adminId", nativeQuery = true)
    LectureNote findByLectureNoteNameAndCourseIdAndAdminId(
            @Param("lectureNoteName") String lectureNoteName,
            @Param("courseId") String courseId,
            @Param("adminId") String adminId);

    List<LectureNote> findByCourseId(String courseId);
}
*/

public interface LectureNoteRepo extends JpaRepository<LectureNote, Long> {

    @Query(value = "SELECT * FROM lecture_note WHERE admin_id= ?1 AND course_id=?2", nativeQuery = true)
    List<LectureNote> allLectureNote(String adminId, String courseId);

    @Query(value = "SELECT * FROM lecture_note WHERE lecture_note_name = :lectureNoteName AND course_id = :courseId AND admin_id = :adminId", nativeQuery = true)
    LectureNote findByLectureNoteNameAndCourseIdAndAdminId(@Param("lectureNoteName") String lectureNoteName, @Param("courseId") String courseId, @Param("adminId") String adminId);

    @Query(value = "SELECT * FROM lecture_note WHERE lecture_note_name = :lectureNoteName AND course_id = :courseId AND admin_id = :adminId ", nativeQuery = true)
    LectureNote findByLectureNoteNameAndCourseIdAndAdminIdAndDeadLineAndMessage(
            @Param("lectureNoteName") String lectureNoteName,
            @Param("courseId") String courseId,
            @Param("adminId") String adminId);

    List<LectureNote> findByCourseId(String courseId);
}
