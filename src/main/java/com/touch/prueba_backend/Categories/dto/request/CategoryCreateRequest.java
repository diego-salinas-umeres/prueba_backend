package com.touch.prueba_backend.Categories.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CategoryCreateRequest {

    @NotBlank
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}