package com.lms.userprogressservice.service;


import com.lms.userprogressservice.dto.UpdateUserProgressDTO;
import com.lms.userprogressservice.model.UserProgress;
import com.lms.userprogressservice.repository.UserProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProgressService {
    private final UserProgressRepository userProgressRepository;

    public ResponseEntity<?> getUserProgress(String userId, String chapterId) {
        UserProgress userProgress = userProgressRepository.findByUserIdAndChapterId(userId, chapterId);
        if (userProgress != null) {
            return new ResponseEntity<>(userProgress, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build(); // Return 404 NOT_FOUND
        }
    }
    public int countValidCompletedChapters(String userId, List<String> publishedChapterIds) {
        return userProgressRepository.countByUserIdAndChapterIdInAndIsCompleted(userId, publishedChapterIds, true);
    }

    public UserProgress updateUserProgress(String chapterId, String userId, UserProgress request){
        UserProgress userProgress = new UserProgress();
        userProgress.setUserId(userId);
        userProgress.setChapterId(chapterId);
        userProgress.setIsCompleted(request.getIsCompleted());
        return userProgressRepository.save(userProgress);
    }

    public UserProgress upsertUserProgress(UpdateUserProgressDTO request) {
        UserProgress userProgress = userProgressRepository.findByUserIdAndChapterId(request.getUserId(), request.getChapterId());
        if (userProgress != null) {
            userProgress.setIsCompleted(request.getIsCompleted());
        } else {
            userProgress = new UserProgress();
            userProgress.setUserId(request.getUserId());
            userProgress.setChapterId(request.getChapterId());
            userProgress.setIsCompleted(request.getIsCompleted());
        }
        return userProgressRepository.save(userProgress);
    }

    public ResponseEntity<UserProgress> getUserProgressByChapterId(String chapterId) {
        UserProgress userProgress = userProgressRepository.findByChapterId(chapterId);
        return ResponseEntity.ok(userProgress);
    }
}
