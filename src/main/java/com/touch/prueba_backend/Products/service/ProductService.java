package com.touch.prueba_backend.Products.service;

import com.itextpdf.text.pdf.*;
import com.touch.prueba_backend.Categories.model.Category;
import com.touch.prueba_backend.Categories.repository.CategoryRepository;
import com.touch.prueba_backend.Products.dto.request.ProductCreateRequest;
import com.touch.prueba_backend.Products.dto.request.ProductUpdateRequest;
import com.touch.prueba_backend.Products.dto.response.ProductPageResponse;
import com.touch.prueba_backend.Products.dto.response.ProductResponse;
import com.touch.prueba_backend.Products.model.Product;
import com.touch.prueba_backend.Products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.itextpdf.text.*;

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
                saved.getId(),
                saved.getName(),
                saved.getPrice(),
                saved.getQuantity(),
                saved.getCategory().getId(),
                saved.getCategory().getName()
        );
    }

    public ProductPageResponse getPaginatedProducts(int page, int size, String name, String categoryName) {
        Page<Product> productPage = productRepository
                .findByNameContainingIgnoreCaseAndCategory_NameContainingIgnoreCase(
                        name != null ? name : "",
                        categoryName != null ? categoryName : "",
                        PageRequest.of(page, size, Sort.by("id").ascending())
                );

        var productResponses = productPage.getContent().stream()
                .map(p -> new ProductResponse(
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getQuantity(),
                        p.getCategory().getId(),
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

    public byte[] generateLowStockReport() {
        List<Product> lowStockProducts = productRepository.findByQuantityLessThan(5);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Reporte de Productos con Bajo Inventario", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(4); // columnas: nombre, descripción, precio, cantidad
            table.setWidthPercentage(100);
            table.addCell("Nombre");
            table.addCell("Descripción");
            table.addCell("Precio");
            table.addCell("Cantidad");

            for (Product p : lowStockProducts) {
                table.addCell(p.getName());
                table.addCell(p.getDescription() != null ? p.getDescription() : "");
                table.addCell(p.getPrice().toString());
                table.addCell(p.getQuantity().toString());
            }

            document.add(table);
            document.close();

            return out.toByteArray();
        } catch (DocumentException e) {
            throw new RuntimeException("Error generando el PDF", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ProductResponse updateProduct(Long id, ProductUpdateRequest request) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        if (request.getQuantity() < 0) {
            throw new RuntimeException("La cantidad no puede ser negativa");
        }

        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setQuantity(request.getQuantity());
        existingProduct.setCategory(category);

        Product updatedProduct = productRepository.save(existingProduct);

        return new ProductResponse(
                updatedProduct.getId(),
                updatedProduct.getName(),
                updatedProduct.getPrice(),
                updatedProduct.getQuantity(),
                updatedProduct.getCategory().getId(),
                updatedProduct.getCategory().getName()
        );
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory().getId(),
                product.getCategory().getName()
        );
    }



}
