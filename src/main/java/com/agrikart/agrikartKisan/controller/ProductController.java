package com.agrikart.agrikartKisan.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.agrikart.agrikartKisan.dto.ProductDTO;
import com.agrikart.agrikartKisan.model.Product;
import com.agrikart.agrikartKisan.model.User;
import com.agrikart.agrikartKisan.service.ProductService;
import com.agrikart.agrikartKisan.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    // ✅ Admin Only: Add Product
    @PostMapping
    public ResponseEntity<?> saveOrUpdateProduct(@Valid @RequestBody ProductDTO productDTO,
                                                 @RequestParam String email,
                                                 @RequestParam String password) {
        User user = userService.loginUser(email, password);

        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return ResponseEntity.status(403).body("Only admin can add or update products.");
        }

        // 🔁 If productDTO has ID, update existing
        if (productDTO.getId() != null) {
            Optional<Product> existingOpt = productService.getProductById(productDTO.getId());
            if (existingOpt.isPresent()) {
                Product existing = existingOpt.get();
                existing.setName(productDTO.getName());
                existing.setCategory(productDTO.getCategory());
                existing.setPrice(productDTO.getPrice());
                existing.setDelivery(productDTO.getDelivery());
                existing.setImageUrl(productDTO.getImageUrl());
                existing.setUse(productDTO.getUse());
                existing.setCause(productDTO.getCause());
                // Don't update product code to avoid duplication errors

                Product updated = productService.saveOrUpdateProduct(existing);
                return ResponseEntity.ok(mapToDTO(updated));
            }
        }

        // 🔁 Otherwise, treat as new product — check for duplicate code
        if (productService.existsByCode(productDTO.getCode())) {
            return ResponseEntity.badRequest().body("Product code already exists. Use a unique code.");
        }

        Product product = mapToEntity(productDTO);
        Product savedProduct = productService.saveOrUpdateProduct(product);

        return ResponseEntity.ok(mapToDTO(savedProduct));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                           @Valid @RequestBody ProductDTO productDTO,
                                           @RequestParam String email,
                                           @RequestParam String password) {
        User user = userService.loginUser(email, password);
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return ResponseEntity.status(403).body("Only admin can update products.");
        }

        Optional<Product> existingOpt = productService.getProductById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product existing = existingOpt.get();

        // Update fields
        existing.setName(productDTO.getName());
        existing.setCategory(productDTO.getCategory());
        existing.setPrice(productDTO.getPrice());
        existing.setDelivery(productDTO.getDelivery());
        existing.setImageUrl(productDTO.getImageUrl());
        existing.setUse(productDTO.getUse());
        existing.setCause(productDTO.getCause());
        // Don't update code unless you want to

        Product updated = productService.saveOrUpdateProduct(existing);
        return ResponseEntity.ok(mapToDTO(updated));
    }

    // ✅ Public: Get All Products
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> dtos = products.stream()
                                        .map(this::mapToDTO)
                                        .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // ✅ Public: Get Products by Category (alias)
    @GetMapping("/category")
    public ResponseEntity<List<ProductDTO>> getByCategory(@RequestParam String name) {
        List<Product> products = productService.getProductsByCategory(name);
        List<ProductDTO> dtos = products.stream()
                                        .map(this::mapToDTO)
                                        .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // ✅ Public: Get Product by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Optional<Product> productOpt = productService.getProductById(id);
        return productOpt.map(product -> ResponseEntity.ok(mapToDTO(product)))
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Admin Only: Delete Product
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id,
                                           @RequestParam String email,
                                           @RequestParam String password) {
        User user = userService.loginUser(email, password);
        if (user != null && "ADMIN".equalsIgnoreCase(user.getRole())) {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted.");
        } else {
            return ResponseEntity.status(403).body("Only admin can delete products.");
        }
    }

    // ✅ Admin Only: Bulk Upload Products
    @PostMapping("/bulk-upload")
    public ResponseEntity<?> bulkUploadProducts(@RequestBody List<ProductDTO> productDTOs,
                                                @RequestParam String email,
                                                @RequestParam String password) {
        User user = userService.loginUser(email, password);
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return ResponseEntity.status(403).body("Only admin can upload products.");
        }

        List<Product> saved = productDTOs.stream()
            .map(this::mapToEntity)
            .map(productService::saveOrUpdateProduct)
            .toList();

        List<ProductDTO> responseDTOs = saved.stream()
            .map(this::mapToDTO)
            .toList();

        return ResponseEntity.ok(responseDTOs);
    }

    // 🔁 Mapping helpers
    private Product mapToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setUse(dto.getUse());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());
        product.setCode(dto.getCode());
        product.setImageUrl(dto.getImageUrl());
        product.setCause(dto.getCause());
        product.setDelivery(dto.getDelivery());
        return product;
    }

    private ProductDTO mapToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setUse(product.getUse());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory());
        dto.setCode(product.getCode());
        dto.setImageUrl(product.getImageUrl());
        dto.setCause(product.getCause());
        dto.setDelivery(product.getDelivery());
        return dto;
    }
}
