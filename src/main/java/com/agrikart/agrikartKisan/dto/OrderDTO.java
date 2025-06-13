package com.agrikart.agrikartKisan.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.*;


public class OrderDTO {

    private Long id;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotEmpty(message = "Product ID list cannot be empty")
    private List<@NotNull(message = "Product ID is required") Long> productIds;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @Positive(message = "Total amount must be positive")
    private Double totalAmount;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Long> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<Long> productIds) {
		this.productIds = productIds;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	@NotBlank(message = "Status is required")
    private String status;

    private LocalDateTime orderDate;
}
