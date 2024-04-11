package com.lms.chapterservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonFormat
public class UserProgressDTO {
    private String id;
    private String userId;
    private Boolean isCompleted;
    private String chapterId;

}
