package com.agrikart.agrikartKisan.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agrikart.agrikartKisan.model.Product;
import com.agrikart.agrikartKisan.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    
    public Product saveOrUpdateProduct(Product product) {
        Optional<Product> existingProduct = productRepository.findByCode(product.getCode());

        if (existingProduct.isPresent()) {
            System.out.println("Updating product with code: " + product.getCode());
            Product existing = existingProduct.get();
            existing.setName(product.getName());
            existing.setPrice(product.getPrice());
            existing.setUse(product.getUse());
            existing.setImageUrl(product.getImageUrl());
            existing.setDelivery(product.getDelivery());
            existing.setCategory(product.getCategory());
            existing.setCause(product.getCause());
            return productRepository.save(existing);
        } else {
            System.out.println("Creating new product with code: " + product.getCode());
            return productRepository.save(product);
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category);
    }


    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public boolean existsByCode(String code) {
        return productRepository.existsByCode(code);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
