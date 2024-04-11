package com.lms.courseservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCourseRequest{
    private String title;
    private String description;
    private String imageUrl;
    private Float price;
    private Boolean isPublished;
    private String categoryId;
    private String attachmentId;
    private String purchaseId;

}
