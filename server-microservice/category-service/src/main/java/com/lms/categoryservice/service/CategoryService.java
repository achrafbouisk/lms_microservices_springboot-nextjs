package com.lms.categoryservice.service;

import com.lms.categoryservice.dto.CategoryDTO;
import com.lms.categoryservice.model.Category;
import com.lms.categoryservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public ResponseEntity<?> getCategories(){
        List<Category> categories = categoryRepository.findAll();

        // Convert Category entities to DTOs
        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(CategoryDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(categoryDTOs, HttpStatus.OK);
    }

    public ResponseEntity<?> getCategory(String categoryId){
        Category category = categoryRepository.findById(categoryId).orElse(null);

        CategoryDTO categoryDTO = new CategoryDTO(category);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }
}
