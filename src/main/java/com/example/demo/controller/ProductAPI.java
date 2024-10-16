package com.example.demo.controller;

import com.example.demo.entity.Orders;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@SecurityRequirement(name = "api")
@CrossOrigin(origins = "*")
public class ProductAPI {

    @Autowired
    ProductService productService;



    // 1. API lấy danh sách lên -> phân trang ở back-end


    // 2. Tạo và lưu đơn hàng
    @PostMapping
    public ResponseEntity create(@RequestBody Product product) {
      Product newProduct = productService.create(product);
        return ResponseEntity.ok(newProduct);
    }

    // 3. Lịch sử mua hàng
    @GetMapping
    public ResponseEntity getAll() {
        List<Product> products = productService.getAll();
        return ResponseEntity.ok(products);
    }
}