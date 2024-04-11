package com.lms.userprogressservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserProgressDTO {
    private String userId;
    private Boolean isCompleted = false;
    private String chapterId;
}
