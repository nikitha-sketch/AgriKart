package com.agrikart.agrikartKisan.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders") // "order" is a reserved keyword in SQL, so better use "orders"
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many orders can be placed by one user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // An order can have multiple products (simplified as many-to-many)
    @ManyToMany
    @JoinTable(
        name = "order_products",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    private LocalDateTime orderDate;

    private Double totalAmount;

    private String status; // e.g., "PENDING", "COMPLETED", "CANCELLED"
    private int quantity;
    
    public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// Constructors
    public Order() {
        this.orderDate = LocalDateTime.now();
        this.status = "PENDING";
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
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
}
