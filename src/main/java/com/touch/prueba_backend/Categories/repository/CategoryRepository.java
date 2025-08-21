package com.touch.prueba_backend.Categories.repository;

import com.touch.prueba_backend.Categories.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}
