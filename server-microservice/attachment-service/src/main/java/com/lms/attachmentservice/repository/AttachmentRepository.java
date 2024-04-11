package com.lms.attachmentservice.repository;

import com.lms.attachmentservice.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, String> {
    void deleteByIdAndCourseId(String attachmentId, String courseId);

    List<Attachment> getAllAttachmentsByCourseId(String courseId);
}
