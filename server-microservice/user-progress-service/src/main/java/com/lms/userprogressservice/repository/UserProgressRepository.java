package com.lms.userprogressservice.repository;

import com.lms.userprogressservice.model.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, String> {

    UserProgress findByUserIdAndChapterId(String userId, String chapterId);
    int countByUserIdAndChapterIdInAndIsCompleted(String userId, List<String> chapterIds, boolean isCompleted);

    UserProgress findByChapterId(String chapterId);
}
