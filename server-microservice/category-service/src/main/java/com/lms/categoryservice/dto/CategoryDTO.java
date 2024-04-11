package com.lms.categoryservice.dto;

import com.lms.categoryservice.model.Category;
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
    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        // Map other fields if needed
    }
}