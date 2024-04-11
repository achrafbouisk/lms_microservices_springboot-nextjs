package com.lms.chapterservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MuxData {
    private String id;
    private String assetId;
    private String playbackId;
    private String chapterId;
}
