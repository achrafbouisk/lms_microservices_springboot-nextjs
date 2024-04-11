package com.lms.chapterservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude
@Entity
@Table(name = "Chapter")
public class Chapter {
    @Id
    private String id = UUID.randomUUID().toString();
    private String title;
    private String description;
    private String videoUrl;
    private Integer position;
    private Boolean isPublished = false;
    private Boolean isFree = false;

    private String courseId;

    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
}
