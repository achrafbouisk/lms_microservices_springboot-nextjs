package com.lms.courseservice.controller;

import com.lms.courseservice.dto.CourseDTO;
import com.lms.courseservice.dto.CreateCourseRequest;
import com.lms.courseservice.dto.UpdateCourseRequest;
import com.lms.courseservice.exception.CourseNotFoundException;
import com.lms.courseservice.exception.MissingRequiredFieldsException;
import com.lms.courseservice.exception.UnauthorizedException;
import com.lms.courseservice.model.Course;
import com.lms.courseservice.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/courses-by-user/{userId}")
    public ResponseEntity<?> getCourses(@PathVariable String userId) {
        return courseService.findCoursesByUserId(userId);
    }

    @GetMapping("/v2")
    public ResponseEntity<?> getCourses(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String categoryId
    ) {
        if (title == null && categoryId == null) {
            return (ResponseEntity<?>) courseService.getAllCourses();
        }

        if (title != null && categoryId != null) {
            return courseService.findPublishedCoursesWithTitleContainingAndCategoryId(title, categoryId);
        } else if (title != null) {
            return courseService.findPublishedCoursesWithTitleContaining(title);
        } else {
            return courseService.findPublishedCoursesWithCategoryId(categoryId);
        }
    }

    @GetMapping("/get-course-chapter/{courseId}")
    public ResponseEntity<?> getCourseAndChapter(@PathVariable String courseId) {
        ResponseEntity<?> course = courseService.getCourseAndChapter(courseId);
        return ResponseEntity.ok(course);
    }

    @GetMapping("/get-course-with-published-chapters/{courseId}")
    public ResponseEntity<?> getCourseWithPublishedChapters(@PathVariable String courseId) {
        ResponseEntity<?> course = courseService.getCourseWithPublishedChapters(courseId);
        return ResponseEntity.ok(course);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<?> getCourse(@PathVariable String courseId) {
        ResponseEntity<?> course = courseService.getCourse(courseId);
        return ResponseEntity.ok(course);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> createCourse(@RequestBody CreateCourseRequest request, @PathVariable String userId){
        ResponseEntity<?> course = courseService.createCourse(request, userId);
        return ResponseEntity.ok(course);
    }

    @PatchMapping("/{courseId}/{userId}")
    public ResponseEntity<?> updateCourse(@PathVariable String courseId, @PathVariable String userId, @RequestBody UpdateCourseRequest request) {
        try {
            return courseService.updateCourse(courseId, userId, request);
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        } catch (CourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Error");
        }
    }

    @DeleteMapping("/{courseId}/{userId}")
    public ResponseEntity<?> deleteCourse(@PathVariable String courseId, @PathVariable String userId) {
        try {
            courseService.deleteCourse(courseId, userId);
            return ResponseEntity.ok().build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        } catch (CourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Error");
        }
    }

    @PatchMapping("/{courseId}/{userId}/publish")
    public ResponseEntity<?> publishCourse(@PathVariable String courseId, @PathVariable String userId) {
            courseService.publishCourse(courseId, userId);
            return ResponseEntity.ok("Course published successfully");
    }

    @PatchMapping("/{courseId}/{userId}/unpublish")
    public ResponseEntity<?> unPublishCourse(@PathVariable String courseId, @PathVariable String userId) {
        try {
            courseService.unPublishCourse(courseId, userId);
            return ResponseEntity.ok("Course Unpublished successfully");
        } catch (CourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}
