package com.touch.prueba_backend.Products.dto.response;

import java.util.List;

public class ProductPageResponse {
    private List<ProductResponse> products;
    private int currentPage;
    private int totalPages;
    private long totalItems;

    public ProductPageResponse(List<ProductResponse> products, int currentPage, int totalPages, long totalItems) {
        this.products = products;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }

    public List<ProductResponse> getProducts() { return products; }
    public void setProducts(List<ProductResponse> products) { this.products = products; }

    public int getCurrentPage() { return currentPage; }
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

    public long getTotalItems() { return totalItems; }
    public void setTotalItems(long totalItems) { this.totalItems = totalItems; }
}