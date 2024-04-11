package com.lms.categoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private String id;
    private String userId;
    private String title;
    private String description;
    private String imageUrl;
    private Float price;
    private Boolean isPublished;
    private String categoryId;
    private List<Chapter> chapters;
    // You can add more fields if needed

    // Constructor to convert from entity to DTO
    public CourseDTO(CourseDTO course) {
        this.id = course.getId();
        this.userId = course.getUserId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.imageUrl = course.getImageUrl();
        this.price = course.getPrice();
        this.isPublished = course.getIsPublished();
        this.categoryId = course.getCategoryId() != null ? course.getCategoryId() : null;
        //this.categoryName = course.getCategory() != null ? course.getCategory().getName() : null;
    }

}