package com.lms.chapterservice.repository;

import com.lms.chapterservice.model.Chapter;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, String> {
    List<Chapter> findByCourseIdOrderByPositionAsc(String courseId);
    @Transactional
    @Modifying
    @Query("UPDATE Chapter c SET c.position = :position WHERE c.id = :chapterId")
    void updateChapterPosition(String chapterId, int position);
    Chapter findFirstByCourseIdOrderByCreatedAtDesc(String courseId);
    Chapter findByIdAndCourseId(String chapterId, String courseId);
    List<Chapter> findByCourseIdAndIsPublishedTrue(String courseId);
    List<Chapter> findByCourseIdAndIsPublishedFalse(String courseId);
    @Query("SELECT c FROM Chapter c WHERE c.courseId = :courseId AND c.isPublished = true AND c.position > :position ORDER BY c.position ASC")
    Optional<Chapter> findFirstPublishedChapterWithPositionGreaterThan(@Param("courseId") String courseId, @Param("position") int position);

    List<Chapter> findByCourseIdAndIsPublishedTrueOrderByPositionAsc(String courseId);
}
