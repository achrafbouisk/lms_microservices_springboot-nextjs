package com.lms.attachmentservice.controller;


import com.lms.attachmentservice.dto.CreateAttachmentRequest;
import com.lms.attachmentservice.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @GetMapping("/{courseId}")
    public ResponseEntity<?> getAllByCourseId(@PathVariable String courseId) {
        return attachmentService.getAllByCourseId(courseId);
    }

    @PostMapping("/{courseId}/{userId}")
    public ResponseEntity<?> createAttachment(@PathVariable String courseId, @PathVariable String userId, @RequestBody CreateAttachmentRequest request) {
        return attachmentService.createAttachment(courseId, userId, request);
    }

    @DeleteMapping("/{courseId}/{userId}/{attachmentId}")
    public ResponseEntity<?> deleteAttachment(@PathVariable String courseId, @PathVariable String userId, @PathVariable String attachmentId) {
        return attachmentService.deleteAttachment(courseId, userId, attachmentId);
    }
}
