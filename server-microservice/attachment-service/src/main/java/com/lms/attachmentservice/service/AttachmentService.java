package com.lms.attachmentservice.service;

import com.lms.attachmentservice.dto.Course;
import com.lms.attachmentservice.dto.CreateAttachmentRequest;
import com.lms.attachmentservice.model.Attachment;
import com.lms.attachmentservice.repository.AttachmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final RestTemplate restTemplate;

    public ResponseEntity<?> getAllByCourseId(String courseId){
        List<Attachment> attachments = attachmentRepository.getAllAttachmentsByCourseId(courseId);

        if(attachments != null){
            return new ResponseEntity<>(attachments, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<?> createAttachment(String courseId, String userId, CreateAttachmentRequest request) {
        if(userId == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Make a REST API request to retrieve the course
        Course course = restTemplate.getForObject("http://COURSE-SERVICE/api/courses/" + courseId, Course.class);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }

        Attachment attachment = new Attachment();
        attachment.setName(getLastSegmentOfUrl(request.getUrl()));
        attachment.setUrl(request.getUrl());
        attachment.setCourseId(courseId);

        Attachment savedAttachment = attachmentRepository.save(attachment);

        return new ResponseEntity<>(savedAttachment, HttpStatus.OK);
    }

    public String getLastSegmentOfUrl(String url) {
        String[] parts = url.split("/");
        return parts[parts.length - 1];
    }

    @Transactional
    public ResponseEntity<?> deleteAttachment(String courseId, String userId, String attachmentId) {
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Make a REST API request to retrieve the course
        Course course = restTemplate.getForObject("http://COURSE-SERVICE/api/courses/" + courseId, Course.class);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        // Ensure that the transaction is active for repository operations
        attachmentRepository.deleteByIdAndCourseId(attachmentId, courseId);

        return new ResponseEntity<>("Attachment Deleted", HttpStatus.OK);
    }
}
