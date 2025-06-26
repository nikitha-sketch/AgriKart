package com.agrikart.agrikartKisan.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agrikart.agrikartKisan.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category); // fixed: was findByType
    Optional<Product> findByNameIgnoreCase(String name);
    Optional<Product> findByCode(String code);
    List<Product> findByCategoryIgnoreCase(String category);
    boolean existsByCode(String code);



}
