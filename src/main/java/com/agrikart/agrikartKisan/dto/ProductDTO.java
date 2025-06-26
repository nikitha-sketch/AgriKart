package com.agrikart.agrikartKisan.dto;

import jakarta.validation.constraints.*;

public class ProductDTO {

    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(max = 100, message = "Product name must be under 100 characters")
    private String name;

    //@NotBlank(message = "Description is required")
    //@Size(max = 500, message = "Description must be under 500 characters")
    //private String description;

    private String use;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Double price;

    @NotBlank(message = "Category is required")
    private String category;

    private String code;
    private String imageUrl;
    private String delivery;
    
    private String cause;
    public ProductDTO() {}

    public ProductDTO(Long id, String name, Double price, String category,String code,String imageUrl,String delivery,String cause,String use) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.imageUrl=imageUrl;
        this.delivery=delivery;
        this.use=use;
        this.cause=cause;
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

    

    public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}
	
}

