package com.lms.courseservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private String id;
    private String name;
    // You can add more fields if needed

    // Constructor to convert from entity to DTO
    public CategoryDTO(CategoryDTO category) {
        this.id = category.getId();
        this.name = category.getName();
        // Map other fields if needed
    }
}