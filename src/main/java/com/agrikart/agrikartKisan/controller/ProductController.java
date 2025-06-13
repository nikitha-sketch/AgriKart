package com.agrikart.agrikartKisan.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductDTO productDTO,
                                        @RequestParam String email,
                                        @RequestParam String password) {
        User user = userService.loginUser(email, password); // now returns User or null
        if (user != null && "ADMIN".equalsIgnoreCase(user.getRole())) {
            Product product = mapToEntity(productDTO);
            Product savedProduct = productService.saveProduct(product);
            return ResponseEntity.ok(mapToDTO(savedProduct));
        } else {
            return ResponseEntity.status(403).body("Only admin can add products.");
        }
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

    // ✅ Public: Get Products by Type
    @GetMapping("/type/{type}")
    public ResponseEntity<List<ProductDTO>> getProductsByType(@PathVariable String type) {
        List<Product> products = productService.getProductsByCategory(type);
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
        User user = userService.loginUser(email, password);  // returns User or null
        if (user != null && "ADMIN".equalsIgnoreCase(user.getRole())) {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted.");
        } else {
            return ResponseEntity.status(403).body("Only admin can delete products.");
        }
    }


    // 🔁 Mapping Helpers
    private Product mapToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());
        return product;
    }

    private ProductDTO mapToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory());
        return dto;
    }
}
