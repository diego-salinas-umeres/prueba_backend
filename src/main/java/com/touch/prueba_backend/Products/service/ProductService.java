package com.touch.prueba_backend.Products.service;

import com.touch.prueba_backend.Categories.model.Category;
import com.touch.prueba_backend.Categories.repository.CategoryRepository;
import com.touch.prueba_backend.Products.dto.request.ProductCreateRequest;
import com.touch.prueba_backend.Products.dto.response.ProductPageResponse;
import com.touch.prueba_backend.Products.dto.response.ProductResponse;
import com.touch.prueba_backend.Products.model.Product;
import com.touch.prueba_backend.Products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ProductResponse createProduct(ProductCreateRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        if (request.getQuantity() < 0) {
            throw new RuntimeException("La cantidad no puede ser negativa");
        }

        Product product = new Product(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getQuantity(),
                category
        );

        Product saved = productRepository.save(product);

        return new ProductResponse(
                saved.getName(),
                saved.getPrice(),
                saved.getQuantity(),
                saved.getCategory().getName()
        );
    }

    public ProductPageResponse getPaginatedProducts(int page, int size, String name, String categoryName) {
        Page<Product> productPage = productRepository
                .findByNameContainingIgnoreCaseAndCategory_NameContainingIgnoreCase(
                        name != null ? name : "",
                        categoryName != null ? categoryName : "",
                        PageRequest.of(page, size)
                );

        var productResponses = productPage.getContent().stream()
                .map(p -> new ProductResponse(
                        p.getName(),
                        p.getPrice(),
                        p.getQuantity(),
                        p.getCategory().getName()
                ))
                .collect(Collectors.toList());

        return new ProductPageResponse(
                productResponses,
                productPage.getNumber(),
                productPage.getTotalPages(),
                productPage.getTotalElements()
        );
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productRepository.deleteById(id);
    }

}
