package com.lms.chapterservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterProgressRequest {
    private String userId;
    private String chapterId;
    private boolean isCompleted;

    // getters and setters
}