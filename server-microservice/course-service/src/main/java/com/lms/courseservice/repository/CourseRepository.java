package com.lms.courseservice.repository;

import com.lms.courseservice.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    Course findByIdAndUserId(String courseId, String userId);
    List<Course> findByUserIdOrderByCreatedAtDesc(String userId);
    List<Course> findByIsPublishedTrueAndTitleContainingAndCategoryId(String title, String categoryId);
    List<Course> findAllByUserIdOrderByCreatedAtDesc(String userId);
    List<Course> findByIsPublishedTrueAndCategoryId(String categoryId);
    List<Course> findByIsPublishedTrueAndTitleContaining(String title);

    List<Course> findByIsPublishedTrue();
}
