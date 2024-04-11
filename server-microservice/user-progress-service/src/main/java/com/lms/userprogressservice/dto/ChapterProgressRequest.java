package com.lms.userprogressservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterProgressRequest {
    private boolean isCompleted;

    // getters and setters
}