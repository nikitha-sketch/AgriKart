package com.agrikart.agrikartKisan.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OrderDTO {

    private Long id;
    
    private String userName;
    
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotEmpty(message = "Order items list cannot be empty")
    private List<@Valid OrderItemDTO> orderItems;

    @Positive(message = "Total amount must be positive")
    private Double totalAmount;
    
    private List<ProductDTO> product;
    
    private String productName;
    public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}

	private int quantity;
    @NotBlank(message = "Status is required")
    private String status;

    private LocalDateTime orderDate;
    
    
//    private Integer quantity;
    public OrderDTO(Long id, String userName, Long userId, List<OrderItemDTO> orderItems, LocalDateTime orderDate, Double totalAmount, String status,String productName) {
        this.id = id;
        this.userName = userName;
        this.userId = userId;
        this.orderItems = orderItems;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.productName=productName;
    }

    
    
    public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	// Getters and Setters
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

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
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

//	public Integer getQuantity() {
//		return quantity;
//	}
//
//	public void setQuantity(Integer quantity) {
//		this.quantity = quantity;
//	}
    
    
}
