package com.touch.prueba_backend.Categories.service;


import com.touch.prueba_backend.Categories.dto.request.CategoryCreateRequest;
import com.touch.prueba_backend.Categories.dto.response.CategoryResponse;
import com.touch.prueba_backend.Categories.model.Category;
import com.touch.prueba_backend.Categories.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(CategoryCreateRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("La categoría ya existe");
        }

        Category category = new Category();
        category.setName(request.getName());

        return categoryRepository.save(category);
    }

    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(c -> new CategoryResponse(c.getId(), c.getName()))
                .toList();
    }
}