package com.ecommerce.controller;

import com.ecommerce.model.Category;
import com.ecommerce.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

/**
 * REST API Controller: CategoryController
 * This controller exposes RESTful endpoints for Category operations.
 * All endpoints invoke the Business Layer (CategoryService).
 */
@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController() {
        this.categoryService = new CategoryService();
    }

    /**
     * POST /api/categories - Create a new category
     * 
     * @param category Category object from request body
     * @return Created category with HTTP 201
     */
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        try {
            Category createdCategory = categoryService.createCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * GET /api/categories/{id} - Get category by ID
     * 
     * @param id Category ID
     * @return Category object or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id) {
        try {
            Category category = categoryService.getCategoryById(id);
            if (category != null) {
                return ResponseEntity.ok(category);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * GET /api/categories - Get all categories
     * 
     * @return List of all categories
     */
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(categories);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * PUT /api/categories/{id} - Update category
     * 
     * @param id       Category ID
     * @param category Updated category object
     * @return Success message or error
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        try {
            category.setCategoryId(id);
            boolean updated = categoryService.updateCategory(category);
            if (updated) {
                return ResponseEntity.ok("Category updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to update category");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * DELETE /api/categories/{id} - Delete category
     * 
     * @param id Category ID
     * @return Success message or error
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        try {
            boolean deleted = categoryService.deleteCategory(id);
            if (deleted) {
                return ResponseEntity.ok("Category deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }
}
