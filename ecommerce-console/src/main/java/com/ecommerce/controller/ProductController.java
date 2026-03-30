package com.ecommerce.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<Map<String, Object>> products = List.of(
                Map.of("productId", 1, "name", "Laptop", "price", 1200, "stock", 10, "categoryId", 1),
                Map.of("productId", 2, "name", "Shirt", "price", 40, "stock", 50, "categoryId", 2)
        );

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(
                Map.of("productId", id, "name", "Sample Product", "price", 100)
        );
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(
                List.of(Map.of("productId", 1, "categoryId", categoryId))
        );
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<?> checkStock(@PathVariable Integer id) {
        return ResponseEntity.ok(Map.of("inStock", true));
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Map<String, Object> product) {
        return ResponseEntity.ok("Product created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id,
                                           @RequestBody Map<String, Object> product) {
        return ResponseEntity.ok("Product updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        return ResponseEntity.ok("Product deleted successfully");
    }
}
