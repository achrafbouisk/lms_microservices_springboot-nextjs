package com.lms.categoryservice.controller;

import com.lms.categoryservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getCategories(){
        return categoryService.getCategories();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategory(@PathVariable String categoryId){
        return categoryService.getCategory(categoryId);
    }
}
