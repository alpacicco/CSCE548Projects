package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

/**
 * REST API Controller: ProductController
 * This controller exposes RESTful endpoints for Product operations.
 * All endpoints invoke the Business Layer (ProductService).
 */
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;

    public ProductController() {
        this.productService = new ProductService();
    }

    /**
     * POST /api/products - Add a new product
     * 
     * @param product Product object from request body
     * @return Created product with HTTP 201
     */
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            Product createdProduct = productService.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * GET /api/products/{id} - Get product by ID
     * 
     * @param id Product ID
     * @return Product object or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
        try {
            Product product = productService.getProductById(id);
            if (product != null) {
                return ResponseEntity.ok(product);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * GET /api/products - Get all products
     * 
     * @return List of all products
     */
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * PUT /api/products/{id} - Update product
     * 
     * @param id      Product ID
     * @param product Updated product object
     * @return Success message or error
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        try {
            product.setProductId(id);
            boolean updated = productService.updateProduct(product);
            if (updated) {
                return ResponseEntity.ok("Product updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to update product");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * DELETE /api/products/{id} - Delete product
     * 
     * @param id Product ID
     * @return Success message or error
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        try {
            boolean deleted = productService.deleteProduct(id);
            if (deleted) {
                return ResponseEntity.ok("Product deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * GET /api/products/category/{categoryId} - Get products by category
     * 
     * @param categoryId Category ID
     * @return List of products in the category
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable Integer categoryId) {
        try {
            List<Product> products = productService.getProductsByCategory(categoryId);
            return ResponseEntity.ok(products);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * GET /api/products/{id}/stock - Check if product is in stock
     * 
     * @param id Product ID
     * @return Boolean indicating stock availability
     */
    @GetMapping("/{id}/stock")
    public ResponseEntity<?> checkStock(@PathVariable Integer id) {
        try {
            boolean inStock = productService.isProductInStock(id);
            return ResponseEntity.ok(java.util.Map.of("inStock", inStock));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * PUT /api/products/{id}/stock - Update product stock
     * 
     * @param id        Product ID
     * @param stockData Map containing quantity
     * @return Success message or error
     */
    @PutMapping("/{id}/stock")
    public ResponseEntity<?> updateStock(@PathVariable Integer id,
            @RequestBody java.util.Map<String, Integer> stockData) {
        try {
            Integer quantity = stockData.get("quantity");
            boolean updated = productService.updateStock(id, quantity);
            if (updated) {
                return ResponseEntity.ok("Stock updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to update stock");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }
}
