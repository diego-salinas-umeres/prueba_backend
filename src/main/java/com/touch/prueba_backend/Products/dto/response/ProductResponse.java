package com.touch.prueba_backend.Products.dto.response;

import java.math.BigDecimal;

public class ProductResponse {
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String categoryName;

    public ProductResponse(String name, BigDecimal price, Integer quantity, String categoryName) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.categoryName = categoryName;
    }

    // Getters y setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}
