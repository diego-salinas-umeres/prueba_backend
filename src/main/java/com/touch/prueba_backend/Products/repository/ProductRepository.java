package com.touch.prueba_backend.Products.repository;

import com.touch.prueba_backend.Products.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContainingIgnoreCaseAndCategory_NameContainingIgnoreCase(
            String name, String categoryName, Pageable pageable
    );

    List<Product> findByQuantityLessThan(int quantity);

}
