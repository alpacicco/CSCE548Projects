package com.ecommerce.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(List.of(
                Map.of("productId", 1, "name", "Laptop", "price", 1200),
                Map.of("productId", 2, "name", "Shirt", "price", 40)
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(
                Map.of("productId", id, "name", "Sample Product")
        );
    }
}
