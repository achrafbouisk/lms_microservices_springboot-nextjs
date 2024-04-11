package com.lms.userprogressservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Category {
    private String id;
    private String name;
    private List<Course> courses;

}
