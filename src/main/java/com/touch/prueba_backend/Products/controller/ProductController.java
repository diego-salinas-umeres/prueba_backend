package com.touch.prueba_backend.Products.controller;

import com.touch.prueba_backend.Products.dto.request.ProductCreateRequest;
import com.touch.prueba_backend.Products.dto.response.ProductPageResponse;
import com.touch.prueba_backend.Products.dto.response.ProductResponse;
import com.touch.prueba_backend.Products.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductCreateRequest request
    ) {
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated")
    public ResponseEntity<ProductPageResponse> getPaginatedProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category
    ) {
        ProductPageResponse response = productService.getPaginatedProducts(page, size, name, category);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
