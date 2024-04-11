package com.lms.courseservice.dto;

import com.lms.courseservice.model.Course;
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
    private String category;
    private List<Chapter> chapters;
    // You can add more fields if needed

    // Constructor to convert from entity to DTO
    public CourseDTO(Course course) {
        this.id = course.getId();
        this.userId = course.getUserId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.imageUrl = course.getImageUrl();
        this.price = course.getPrice();
        this.isPublished = course.getIsPublished();
        this.category = course.getCategoryId() != null ? course.getCategoryId() : null;
        //this.categoryName = course.getCategory() != null ? course.getCategory().getName() : null;
    }

}