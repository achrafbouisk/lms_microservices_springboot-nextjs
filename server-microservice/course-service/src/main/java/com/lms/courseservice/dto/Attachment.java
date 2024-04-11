package com.lms.courseservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
    private String id;
    private String name;
    private String url;
    private String courseId;
}
