package com.lms.attachmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAttachmentRequest {
    private String name;
    private String url;
    private String courseId;
}
