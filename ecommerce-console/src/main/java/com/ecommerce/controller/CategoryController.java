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
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        List<Map<String, Object>> categories = List.of(
                Map.of("categoryId", 1, "name", "Electronics"),
                Map.of("categoryId", 2, "name", "Clothing"),
                Map.of("categoryId", 3, "name", "Books")
        );

        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(
                Map.of("categoryId", id, "name", "Sample Category")
        );
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Map<String, Object> category) {
        return ResponseEntity.ok("Category created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id,
                                            @RequestBody Map<String, Object> category) {
        return ResponseEntity.ok("Category updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        return ResponseEntity.ok("Category deleted successfully");
    }
}
