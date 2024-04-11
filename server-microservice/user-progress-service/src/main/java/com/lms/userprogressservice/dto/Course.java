package com.lms.userprogressservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private String id;
    private String userId;
    private String title;
    private String description;
    private String imageUrl;
    private Float price;
    private Boolean isPublished = false;
    private Category category;
}
