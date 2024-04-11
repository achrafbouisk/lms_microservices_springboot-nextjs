package com.lms.courseservice.service;


import com.lms.courseservice.dto.*;
import com.lms.courseservice.exception.CourseNotFoundException;
import com.lms.courseservice.exception.UnauthorizedException;
import com.lms.courseservice.model.Course;
import com.lms.courseservice.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final RestTemplate restTemplate;

    public ResponseEntity<?> getAllCourses() {
        // Fetch all courses from the repository
        List<Course> courses = courseRepository.findByIsPublishedTrue();
        if (courses == null || courses.isEmpty()) {
            return new ResponseEntity<>("No courses found", HttpStatus.NOT_FOUND);
        }
        List<CourseDTO> coursesWithChapters = new ArrayList<>();

        for (Course course : courses) {
            // Make a REST API call to the chapter service to fetch chapters for the current course
            ResponseEntity<Chapter[]> responseEntity = restTemplate.getForEntity(
                    "http://CHAPTER-SERVICE/api/chapters/" + course.getId(), Chapter[].class);
            Chapter[] chapters = responseEntity.getBody();

            // Create a CourseDTO object and append chapters to it
            CourseDTO courseDTO = new CourseDTO(course);
            if (chapters != null) {
                courseDTO.setChapters(Arrays.asList(chapters));
            }
            coursesWithChapters.add(courseDTO);
        }

        return new ResponseEntity<>(coursesWithChapters, HttpStatus.OK);
    }

    public ResponseEntity<?> findPublishedCoursesWithTitleContainingAndCategoryId(String title, String categoryId) {
        List<Course> courses = courseRepository.findByIsPublishedTrueAndTitleContainingAndCategoryId(title, categoryId);
        if (courses == null || courses.isEmpty()) {
            return new ResponseEntity<>("No courses found", HttpStatus.NOT_FOUND);
        }
        List<CourseDTO> coursesWithChapters = new ArrayList<>();

        for (Course course : courses) {
            // Make a REST API call to the chapter service to fetch chapters for the current course
            ResponseEntity<Chapter[]> responseEntity = restTemplate.getForEntity(
                    "http://CHAPTER-SERVICE/api/chapters/" + course.getId(), Chapter[].class);
            Chapter[] chapters = responseEntity.getBody();

            // Create a CourseDTO object and append chapters to it
            CourseDTO courseDTO = new CourseDTO(course);
            if (chapters != null) {
                courseDTO.setChapters(Arrays.asList(chapters));
            }
            coursesWithChapters.add(courseDTO);
        }

        return new ResponseEntity<>(coursesWithChapters, HttpStatus.OK);
    }
    public ResponseEntity<?> findPublishedCoursesWithTitleContaining(String title) {
        List<Course> courses = courseRepository.findByIsPublishedTrueAndTitleContaining(title);
        // Convert entities to DTOs
        List<CourseDTO> courseDTOs = courses.stream()
                .map(CourseDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(courseDTOs, HttpStatus.OK);
    }
    public ResponseEntity<?> findPublishedCoursesWithCategoryId(String categoryId) {
        List<Course> courses = courseRepository.findByIsPublishedTrueAndCategoryId(categoryId);
        // Convert entities to DTOs
        List<CourseDTO> courseDTOs = courses.stream()
                .map(CourseDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(courseDTOs, HttpStatus.OK);
    }

    public ResponseEntity<?> findCoursesByUserId(String userId) {
        List<Course> courses = courseRepository.findAllByUserIdOrderByCreatedAtDesc(userId);

        // Convert entities to DTOs
        List<CourseDTO> courseDTOs = courses.stream()
                .map(CourseDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(courseDTOs, HttpStatus.OK);
    }


    public ResponseEntity<?> getCourse(String courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        Chapter[] chaptersDtOs = restTemplate.getForObject("http://CHAPTER-SERVICE/api/chapters/" + course.getId(), Chapter[].class);
        List<Chapter> chapters = Arrays.asList(chaptersDtOs);
        CourseDTO courseDTO = new CourseDTO(course);
        courseDTO.setChapters(chapters);
        return new ResponseEntity<>(courseDTO, HttpStatus.OK);
    }

    public ResponseEntity<?> getCourseWithPublishedChapters(String courseId) {
        Course course =   courseRepository.findById(courseId).orElse(null);
        if(course != null){
            Chapter[] chaptersDtOs = restTemplate.getForObject(
                    "http://CHAPTER-SERVICE/api/chapters/"
                            + course.getId(), Chapter[].class);
            List<Chapter> chapters = Arrays.asList(chaptersDtOs);
            CourseDTO courseDTO = new CourseDTO(course);
            courseDTO.setChapters(chapters);

            return new ResponseEntity<>(courseDTO, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No Course Found",HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getCourseAndChapter(String courseId) {
        Course course =   courseRepository.findById(courseId).orElse(null);
        if(course != null){
            Chapter[] chaptersDtOs = restTemplate.getForObject(
                    "http://CHAPTER-SERVICE/api/chapters/"
                            + course.getId() + "/all-chapters", Chapter[].class);
            List<Chapter> chapters = Arrays.asList(chaptersDtOs);
            CourseDTO courseDTO = new CourseDTO(course);
            courseDTO.setChapters(chapters);

            return new ResponseEntity<>(courseDTO, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No Course Found",HttpStatus.NOT_FOUND);
        }
    }
    private boolean isTeacher(String userId) {
        // Implement your logic to check if the user is a teacher
        // You may use Spring Security or any other mechanism to authenticate and authorize users
        return true; // Placeholder logic, change as per your actual implementation
    }

    public ResponseEntity<?> createCourse(CreateCourseRequest request, String userId){
        if(userId == null || !isTeacher(userId)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Course course = new Course();
        course.setTitle(request.title());
        course.setUserId(userId);
        course.setIsPublished(false);

        Course savedCourse = courseRepository.save(course);
        return new ResponseEntity<>(savedCourse, HttpStatus.OK);
    }

    public ResponseEntity<?> updateCourse(String courseId, String userId, UpdateCourseRequest request) {
        if(userId == null) {
            throw new UnauthorizedException();
        }

        Course course = courseRepository.findByIdAndUserId(courseId, userId);
        if (course == null) {
            throw new CourseNotFoundException();
        }

        // Update fields only if they are not null in the request
        if (request.getTitle() != null) {
            course.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            course.setDescription(request.getDescription());
        }
        if (request.getImageUrl() != null) {
            course.setImageUrl(request.getImageUrl());
        }
        if (request.getPrice() != null) {
            course.setPrice(request.getPrice());
        }
        if (request.getCategoryId() != null) {
            course.setCategoryId(request.getCategoryId());
        }

        course = courseRepository.save(course);
        return ResponseEntity.ok(course);
    }

    public void deleteCourse(String courseId, String userId) {
        if (userId == null) {
            throw new UnauthorizedException();
        }

        Course course = courseRepository.findByIdAndUserId(courseId, userId);
        if (course == null) {
            throw new CourseNotFoundException();
        }
        // Call the Chapter Service to get chapters for the course
        ResponseEntity<Chapter[]> responseEntity = restTemplate.getForEntity("http://CHAPTER-SERVICE/api/chapters/" + courseId, Chapter[].class, courseId);
        Chapter[] chapters = responseEntity.getBody();

        if (chapters != null) {
            for (Chapter chapter : chapters) {
                if (chapter.getMuxData() != null && chapter.getMuxData().getAssetId() != null) {
                    // Call the Video Asset Service to delete the asset
                    ResponseEntity<Void> result = restTemplate.exchange("http://MUXDATA-SERVICE/api/mux-data/course?assetId=" + chapter.getMuxData().getAssetId(), HttpMethod.DELETE, null, Void.class, chapter.getMuxData().getAssetId());
                }
            }
        }
        // After deleting associated assets, delete the course
        courseRepository.delete(course);
    }

    public boolean publishCourse(String courseId, String userId) {
        Course course = courseRepository.findByIdAndUserId(courseId, userId);

        Chapter[] chapters = restTemplate.getForObject("http://CHAPTER-SERVICE/api/chapters/{courseId}", Chapter[].class, courseId);

        // Check if any chapter is published
        if (chapters != null) {
            for (Chapter chapter : chapters) {
                if (chapter.getIsPublished()) {
                    course.setIsPublished(true);
                }
            }
        }
        courseRepository.save(course);
        return true;
    }
    public boolean unPublishCourse(String courseId, String userId) {
        Course course = courseRepository.findByIdAndUserId(courseId, userId);
        course.setIsPublished(false);
        courseRepository.save(course);
        return false;
    }
}
