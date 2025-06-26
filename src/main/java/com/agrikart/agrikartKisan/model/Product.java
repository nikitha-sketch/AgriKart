package com.agrikart.agrikartKisan.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String code; 
    private String name;
    private String category;  // Fertilizer, Pesticide, Tool, Seed
   // private String description;
    private double price;
    private String delivery;
    
    @Column(name = "usage_info")
    private String use;

    
    @Column(name = "cause")
    private String cause;

	private String imageUrl; // (Optional) if frontend shows image

    public Product() {}

    public Product(String name, String category, String use, double price,String code,String imageUrl,String delivery,String cause) {
        this.name = name;
        this.category = category;
        this.use=use;
        this.price = price;
        this.imageUrl = imageUrl;
        this.code=code;
        this.delivery=delivery;
        this.cause=cause;
    }

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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	

	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

