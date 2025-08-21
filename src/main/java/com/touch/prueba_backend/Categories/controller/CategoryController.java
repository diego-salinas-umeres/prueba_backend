package com.touch.prueba_backend.Categories.controller;

import com.touch.prueba_backend.Categories.dto.request.CategoryCreateRequest;
import com.touch.prueba_backend.Categories.dto.response.CategoryResponse;
import com.touch.prueba_backend.Categories.model.Category;
import com.touch.prueba_backend.Categories.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(
            @Valid @RequestBody CategoryCreateRequest request
    ) {
        Category category = categoryService.createCategory(request);
        return ResponseEntity.ok(category);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

}