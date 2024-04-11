package com.lms.userprogressservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "UserProgress")
public class UserProgress {
    @Id
    private String id = UUID.randomUUID().toString();
    private String userId;
    private Boolean isCompleted = false;
    private String chapterId;

    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    public UserProgress(String userId, String chapterId, boolean completed) {
    }
}
