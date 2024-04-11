package com.lms.courseservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseWithChaptersDTO {
    private String id;
    private String userId;
    private String title;
    private String description;
    private String imageUrl;
    private Float price;
    private Boolean isPublished;
    private String categoryId;
    private String attachmentId;
    private String purchaseId;
    private List<Chapter> chapters;
}
