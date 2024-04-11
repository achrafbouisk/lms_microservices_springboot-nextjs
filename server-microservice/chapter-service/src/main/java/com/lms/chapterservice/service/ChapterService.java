package com.lms.chapterservice.service;


import com.lms.chapterservice.dto.*;
import com.lms.chapterservice.model.Chapter;
import com.lms.chapterservice.repository.ChapterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChapterService {

    private final ChapterRepository chapterRepository;
    private final RestTemplate restTemplate;


    public ResponseEntity<?> getChaptersByCourseId(String courseId) {
        if(courseId == null) {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }
        List<Chapter> chapters = chapterRepository.findByCourseIdAndIsPublishedTrueOrderByPositionAsc(courseId);
        System.out.println("CHAPTERS" + chapters);
        return ResponseEntity.ok(chapters);
    }

    public ResponseEntity<?> getChapters(String courseId) {
        if(courseId == null) {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }
        List<Chapter> chapters = chapterRepository.findByCourseIdOrderByPositionAsc(courseId);
        return ResponseEntity.ok(chapters);
    }
    public ResponseEntity<?> getChapter(String chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId).orElse(null);

        return new ResponseEntity<>(chapter, HttpStatus.OK);
    }
    public ResponseEntity<?> getChapterByIdAndByCourseId(String courseId, String chapterId) {
        Chapter chapter = chapterRepository.findByIdAndCourseId(chapterId, courseId);

        return new ResponseEntity<>(chapter, HttpStatus.OK);
    }
    public ResponseEntity<?> findPublishedChaptersByCourseId(String courseId) {
        List<Chapter> chapters = chapterRepository.findByCourseIdAndIsPublishedTrue(courseId);
        return ResponseEntity.ok(chapters);
    }
    public ResponseEntity<?> getNextChapter(String courseId, int currentPosition) {
        Optional<Chapter> chapterOptional = chapterRepository.findFirstPublishedChapterWithPositionGreaterThan(courseId, currentPosition);
        if (chapterOptional.isPresent()) {
            Chapter chapter = chapterOptional.get();
            return new ResponseEntity<>(chapter, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<?> createChapter(String courseId, CreateChapterRequest request){
        Chapter lastChapter = chapterRepository.findFirstByCourseIdOrderByCreatedAtDesc(courseId);
        int newPosition;
        if(lastChapter != null){
            newPosition = lastChapter.getPosition() + 1;
        } else {
            newPosition = 1;
        }

        Chapter chapter = new Chapter();
        chapter.setTitle(request.getTitle());
        chapter.setCourseId(courseId);
        chapter.setPosition(newPosition);

        chapterRepository.save(chapter);
        return new ResponseEntity<>(chapter, HttpStatus.OK);

    }
    public ResponseEntity<?> reorderChapters(List<UpdatePositionRequest> request) {
        // Update chapter positions
        for (UpdatePositionRequest chapterRequest : request) {
            Chapter chapter = chapterRepository.findById(chapterRequest.getId()).orElse(null);
            if (chapter != null) {
                chapter.setPosition(chapterRequest.getPosition());
                chapterRepository.save(chapter);
            }
        }

        return ResponseEntity.ok("Chapters position updated successfully");
    }
    public ResponseEntity<?> deleteChapter(String courseId, String chapterId) {
        Chapter chapter = chapterRepository.findByIdAndCourseId(chapterId, courseId);
        System.out.println("CHAPTER : " + chapter);
        if (chapter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chapter not found");
        }
        if(chapter.getVideoUrl() != null){
            // Create headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            // Create request body
            String requestBody = "{\"chapterId\": \"" + chapterId + "\", \"videoUrl\": \"" + chapter.getVideoUrl() + "\"}";
            // Create HTTP entity
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            // Make DELETE request with request body
            String url = "http://MUX-SERVICE/api/mux-data/delete";
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);

            // Process response
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("DELETE request with request body successful");
            } else {
                System.out.println("DELETE request with request body failed");
            }
        }

        chapterRepository.deleteById(chapterId);

        List<Chapter> publishedChaptersInCourse = chapterRepository.findByCourseIdAndIsPublishedTrue(courseId);
        if(publishedChaptersInCourse == null){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            CourseUpdateRequest request = new CourseUpdateRequest();
            request.setPublished(false); // Set isPublished to false

            // Create HTTP entity
            HttpEntity<CourseUpdateRequest> entity = new HttpEntity<>(request, headers);

            Course responseEntity = restTemplate.patchForObject("http://COURSE-SERVICE/api/courses/" + courseId, entity, Course.class);
            if (responseEntity != null) {
                return new ResponseEntity<>("Course Updated", HttpStatus.OK);
            }else {
                return new ResponseEntity<>("Failed to update course", HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Chapter Deleted", HttpStatus.OK);
    }

    public ResponseEntity<?> updateChapter(String courseId, String chapterId, UpdateChapterRequest request) {
        Chapter chapter = chapterRepository.findByIdAndCourseId(chapterId, courseId);
        if (chapter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chapter not found");
        }
        if (request.getTitle() != null) {
            chapter.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            chapter.setDescription(request.getDescription());
        }
        if (request.getVideoUrl() != null) {
            chapter.setVideoUrl(request.getVideoUrl());
        }
        if (request.getIsFree() != null) {
            chapter.setIsFree(request.getIsFree());
        }

        if(chapter.getVideoUrl() != null){
            // Create headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            // Create request body
            RequestBody requestBody = new RequestBody(chapterId, chapter.getVideoUrl());
            // Create HTTP entity
            HttpEntity<?> entity = new HttpEntity<>(requestBody, headers);

            // Make DELETE request with request body
            String url = "http://MUX-SERVICE/api/mux-data/update";
            ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            System.out.println(response.getBody());
            // Process response
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("DELETE request with request body successful");
            } else {
                System.out.println("DELETE request with request body failed");
            }
        }


        chapter = chapterRepository.save(chapter);

        return new ResponseEntity<>(chapter, HttpStatus.OK);
    }

    public ResponseEntity<?> publishChapter(String courseId, String chapterId){
        Chapter chapter = chapterRepository.findByIdAndCourseId(chapterId, courseId);
        if (chapter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chapter not found");
        }

        // Update chapter details
        chapter.setIsPublished(true); // Set isPublished to true
        // Save the updated chapter
        chapter = chapterRepository.save(chapter);

        return ResponseEntity.ok(chapter);
    }

    public ResponseEntity<?> unPublishChapter(String courseId, String chapterId){
        // Make a REST API request to retrieve the course
        Course course = restTemplate.getForObject("http://COURSE-SERVICE/api/courses/" + courseId, Course.class);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }

        Chapter chapter = chapterRepository.findByIdAndCourseId(chapterId, courseId);
        if (chapter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chapter not found");
        }


        // Update chapter details
        chapter.setIsPublished(false); // Set isPublished to true
        // Save the updated chapter
        chapter = chapterRepository.save(chapter);

        List<Chapter> publishedChaptersInCourse = chapterRepository.findByCourseIdAndIsPublishedFalse(courseId);
        if(publishedChaptersInCourse == null){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            CourseUpdateRequest request = new CourseUpdateRequest();
            request.setPublished(false); // Set isPublished to false

            // Create HTTP entity
            HttpEntity<CourseUpdateRequest> entity = new HttpEntity<>(request, headers);

            Course responseEntity = restTemplate.patchForObject("http://COURSE-SERVICE/api/courses/" + courseId, entity, Course.class);
            if (responseEntity != null) {
                return new ResponseEntity<>("Course Updated", HttpStatus.OK);
            }else {
                return new ResponseEntity<>("Failed to update course", HttpStatus.OK);
            }
        }

        return ResponseEntity.ok(chapter);
    }
}
