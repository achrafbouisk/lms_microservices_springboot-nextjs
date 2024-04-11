package com.lms.chapterservice.controller;


import com.lms.chapterservice.dto.UpdateChapterRequest;
import com.lms.chapterservice.dto.UpdatePositionRequest;
import com.lms.chapterservice.dto.CreateChapterRequest;
import com.lms.chapterservice.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/chapters")
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;

    @GetMapping("/{courseId}")
    public ResponseEntity<?> getChaptersByCourseId(@PathVariable String courseId) {
        return chapterService.getChaptersByCourseId(courseId);
    }

    @GetMapping("/{courseId}/all-chapters")
    public ResponseEntity<?> getChapters(@PathVariable String courseId) {
        return chapterService.getChapters(courseId);
    }

    @GetMapping("/chapter/{chapterId}")
    public ResponseEntity<?> getChapter(@PathVariable String chapterId) {
        return chapterService.getChapter(chapterId);
    }

    @GetMapping("/published-chapters/{courseId}")
    public ResponseEntity<?> getPublishedChapters(@PathVariable String courseId) {
        return chapterService.findPublishedChaptersByCourseId(courseId);
    }


    @GetMapping("/{chapterId}/courses/{courseId}")
    public ResponseEntity<?> getChapterByIdAndByCourseId(@PathVariable String courseId, @PathVariable String chapterId) {
        return chapterService.getChapterByIdAndByCourseId(courseId, chapterId);
    }

    @GetMapping("/courses/{courseId}/next-chapter/{currentPosition}")
    public ResponseEntity<?> getNextChapter(@PathVariable String courseId, @PathVariable int currentPosition) {
        ResponseEntity<?> nextChapterOptional = chapterService.getNextChapter(courseId, currentPosition);
        if (nextChapterOptional != null) {
            return ResponseEntity.ok(nextChapterOptional);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/courses/{courseId}")
    public ResponseEntity<?> createChapter(@PathVariable String courseId, @RequestBody CreateChapterRequest request) {
        return chapterService.createChapter(courseId, request);
    }

    @PutMapping("/reorder")
    public ResponseEntity<?> updateChapterPositions(@RequestBody List<UpdatePositionRequest> request) {
        return chapterService.reorderChapters(request);
    }

    @DeleteMapping("/{chapterId}/courses/{courseId}")
    public ResponseEntity<?> deleteChapter(@PathVariable String courseId, @PathVariable String chapterId) {
        return chapterService.deleteChapter(courseId, chapterId);
    }

    @PatchMapping("/{chapterId}/courses/{courseId}")
    public ResponseEntity<?> updateChapter(@PathVariable String courseId, @PathVariable String chapterId, @RequestBody UpdateChapterRequest request) {
        return chapterService.updateChapter(courseId, chapterId, request);
    }

    @PatchMapping("/{chapterId}/courses/{courseId}/publish")
    public ResponseEntity<?> publishChapter(@PathVariable String courseId, @PathVariable String chapterId) {
        return chapterService.publishChapter(courseId, chapterId);
    }

    @PatchMapping("/{chapterId}/courses/{courseId}/unpublish")
    public ResponseEntity<?> unPublishChapter(@PathVariable String courseId, @PathVariable String chapterId) {
        return chapterService.unPublishChapter(courseId, chapterId);
    }

}
