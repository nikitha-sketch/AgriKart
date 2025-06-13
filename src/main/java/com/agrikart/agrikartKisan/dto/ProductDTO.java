package com.agrikart.agrikartKisan.dto;

import jakarta.validation.constraints.*;

public class ProductDTO {

    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(max = 100, message = "Product name must be under 100 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description must be under 500 characters")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Double price;

    @NotBlank(message = "Category is required")
    private String category;

    public ProductDTO() {}

    public ProductDTO(Long id, String name, String description, Double price, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

