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
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(List.of(
                Map.of("categoryId", 1, "name", "Electronics"),
                Map.of("categoryId", 2, "name", "Clothing")
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(
                Map.of("categoryId", id, "name", "Sample Category")
        );
    }
}
