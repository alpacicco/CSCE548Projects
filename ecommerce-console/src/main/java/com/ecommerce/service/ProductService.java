package com.ecommerce.service;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.dao.ProductDAOImpl;
import com.ecommerce.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * Business Layer: ProductService
 * This service provides business logic for Product operations and invokes the
 * Data Access Layer (DAO).
 * All CRUD operations from ProductDAO are exposed through this service.
 */
@Service
public class ProductService {
    private final ProductDAO productDAO;

    public ProductService() {
        this.productDAO = new ProductDAOImpl();
    }

    /**
     * Add a new product to the catalog
     * 
     * @param product Product object to create
     * @return Created product with generated ID
     */
    public Product addProduct(Product product) throws SQLException {
        // Business logic: Validate product data
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product price must be non-negative");
        }
        if (product.getStock() != null && product.getStock() < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        return productDAO.create(product);
    }

    /**
     * Retrieve product by ID
     * 
     * @param id Product ID
     * @return Product object or null if not found
     */
    public Product getProductById(Integer id) throws SQLException {
        return productDAO.getById(id);
    }

    /**
     * Get all products in the catalog
     * 
     * @return List of all products
     */
    public List<Product> getAllProducts() throws SQLException {
        return productDAO.getAll();
    }

    /**
     * Update existing product information
     * 
     * @param product Product object with updated information
     * @return true if update was successful
     */
    public boolean updateProduct(Product product) throws SQLException {
        // Business logic: Validate product exists and data is valid
        Product existing = productDAO.getById(product.getProductId());
        if (existing == null) {
            throw new IllegalArgumentException("Product not found with ID: " + product.getProductId());
        }
        if (product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product price must be non-negative");
        }
        return productDAO.update(product);
    }

    /**
     * Delete a product by ID
     * 
     * @param id Product ID to delete
     * @return true if deletion was successful
     */
    public boolean deleteProduct(Integer id) throws SQLException {
        return productDAO.delete(id);
    }

    /**
     * Get products by category
     * 
     * @param categoryId Category ID
     * @return List of products in the specified category
     */
    public List<Product> getProductsByCategory(Integer categoryId) throws SQLException {
        return productDAO.getByCategory(categoryId);
    }

    /**
     * Check if product is in stock
     * 
     * @param productId Product ID
     * @return true if product is in stock
     */
    public boolean isProductInStock(Integer productId) throws SQLException {
        Product product = productDAO.getById(productId);
        return product != null && product.getStock() != null && product.getStock() > 0;
    }

    /**
     * Update product stock quantity
     * 
     * @param productId Product ID
     * @param quantity  New stock quantity
     * @return true if update was successful
     */
    public boolean updateStock(Integer productId, Integer quantity) throws SQLException {
        Product product = productDAO.getById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        product.setStock(quantity);
        return productDAO.update(product);
    }
}
