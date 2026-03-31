package com.ecommerce.service;

import com.ecommerce.dao.CategoryDAO;
import com.ecommerce.dao.CategoryDAOImpl;
import com.ecommerce.model.Category;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Business Layer: CategoryService
 * This service provides business logic for Category operations and invokes the
 * Data Access Layer (DAO).
 * All CRUD operations from CategoryDAO are exposed through this service.
 */
@Service
public class CategoryService {
    private final CategoryDAO categoryDAO;

    public CategoryService() {
        this.categoryDAO = new CategoryDAOImpl();
    }

    /**
     * Create a new category
     * 
     * @param category Category object to create
     * @return Created category with generated ID
     */
    public Category createCategory(Category category) throws SQLException {
        // Business logic: Validate category name is not empty
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        return categoryDAO.create(category);
    }

    /**
     * Retrieve category by ID
     * 
     * @param id Category ID
     * @return Category object or null if not found
     */
    public Category getCategoryById(Integer id) throws SQLException {
        return categoryDAO.getById(id);
    }

    /**
     * Get all categories
     * 
     * @return List of all categories
     */
    public List<Category> getAllCategories() throws SQLException {
        return categoryDAO.getAll();
    }

    /**
     * Update existing category
     * 
     * @param category Category object with updated information
     * @return true if update was successful
     */
    public boolean updateCategory(Category category) throws SQLException {
        // Business logic: Validate category exists
        Category existing = categoryDAO.getById(category.getCategoryId());
        if (existing == null) {
            throw new IllegalArgumentException("Category not found with ID: " + category.getCategoryId());
        }
        return categoryDAO.update(category);
    }

    /**
     * Delete a category by ID
     * 
     * @param id Category ID to delete
     * @return true if deletion was successful
     */
    public boolean deleteCategory(Integer id) throws SQLException {
        return categoryDAO.delete(id);
    }
}
