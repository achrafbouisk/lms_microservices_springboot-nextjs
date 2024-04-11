package com.lms.categoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chapter {
    String id;
    String title;
    String description;
    String videoUrl;
    Integer position;
    Boolean isPublished;
    Boolean isFree;
    MuxData muxData;
    String courseId;
}
