package com.lms.userprogressservice.controller;


import com.lms.userprogressservice.dto.UpdateUserProgressDTO;
import com.lms.userprogressservice.model.UserProgress;
import com.lms.userprogressservice.service.UserProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-progress")
public class UserProgressController {

    private final UserProgressService userProgressService;

    @GetMapping("/{chapterId}/progress/{userId}")
    public ResponseEntity<?> getUserProgress(@PathVariable String userId, @PathVariable String chapterId) {
        ResponseEntity<?> userProgress = userProgressService.getUserProgress(userId, chapterId);
        if (userProgress != null) {
            return ResponseEntity.ok(userProgress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/count-valid-completed-chapters")
    public int countValidCompletedChapters(@RequestParam String userId, @RequestParam List<String> publishedChapterIds) {
        return userProgressService.countValidCompletedChapters(userId, publishedChapterIds);
    }

    @GetMapping("/{chapterId}")
    public ResponseEntity<UserProgress> getUserProgressByChapterId(@PathVariable String chapterId) {
        return userProgressService.getUserProgressByChapterId(chapterId);
    }

    @PostMapping("/{chapterId}/{userId}/user-progress")
    public UserProgress updateUserProgress(@PathVariable String chapterId, @PathVariable String userId, @RequestBody UserProgress request) {
        return userProgressService.updateUserProgress(chapterId, userId, request);
    }

    @PutMapping
    public ResponseEntity<UserProgress> upsertUserProgress(
            @RequestBody UpdateUserProgressDTO request
            ) {
        UserProgress userProgress = userProgressService.upsertUserProgress(request);
        return ResponseEntity.ok(userProgress);
    }
}
