package com.agrikart.agrikartKisan.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrderItemDTO {

	@NotNull(message = "Product ID is required")
	private Long productId;

	private String productCode;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	@NotBlank(message = "Product name is required")
	private String productName;

	@NotBlank(message = "Product image URL is required")
	private String productImage;

	@NotNull(message = "Product price is required")
	@Min(value = 1, message = "Price must be positive")
	private Double productPrice;

	@NotNull(message = "Quantity is required")
	@Min(value = 1, message = "Quantity must be at least 1")
	private Integer quantity;
	
	public OrderItemDTO(String productName, String productCode, Double productPrice, Integer quantity) {
        this.productName = productName;
        this.productCode = productCode;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	// Getters and setters
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
