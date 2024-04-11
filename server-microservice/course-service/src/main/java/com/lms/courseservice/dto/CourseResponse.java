package com.lms.courseservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private String id;
    private String userId;
    private String title;
    private String description;
    private String imageUrl;
    private Float price;
    private Boolean isPublished;
    private CategoryDTO category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Chapter[] chapters;

    public CourseResponse(String id, String title, String description, String imageUrl, Float price, Boolean isPublished, CategoryDTO category, LocalDateTime createdAt, LocalDateTime updatedAt, Chapter[] chapters) {
    }
}
