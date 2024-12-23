package com.example.demo.repository;



import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductById(Long productId);
}