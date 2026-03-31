package com.ecommerce.service;

import com.ecommerce.dao.UserDAO;
import com.ecommerce.dao.UserDAOImpl;
import com.ecommerce.model.User;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Business Layer: UserService
 * This service provides business logic for User operations and invokes the Data
 * Access Layer (DAO).
 * All CRUD operations from UserDAO are exposed through this service.
 */
@Service
public class UserService {
    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAOImpl();
    }

    /**
     * Register a new user in the system
     * 
     * @param user User object to create
     * @return Created user with generated ID
     */
    public User registerUser(User user) throws SQLException {
        // Business logic: Validate email format, check for duplicates, etc.
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return userDAO.create(user);
    }

    /**
     * Retrieve user by ID
     * 
     * @param id User ID
     * @return User object or null if not found
     */
    public User getUserById(Integer id) throws SQLException {
        return userDAO.getById(id);
    }

    /**
     * Get all registered users
     * 
     * @return List of all users
     */
    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAll();
    }

    /**
     * Update existing user information
     * 
     * @param user User object with updated information
     * @return true if update was successful
     */
    public boolean updateUser(User user) throws SQLException {
        // Business logic: Validate user exists before updating
        User existing = userDAO.getById(user.getUserId());
        if (existing == null) {
            throw new IllegalArgumentException("User not found with ID: " + user.getUserId());
        }
        return userDAO.update(user);
    }

    /**
     * Delete a user by ID
     * 
     * @param id User ID to delete
     * @return true if deletion was successful
     */
    public boolean deleteUser(Integer id) throws SQLException {
        return userDAO.delete(id);
    }

    /**
     * Find user by email address
     * 
     * @param email Email address to search for
     * @return User object or null if not found
     */
    public User getUserByEmail(String email) throws SQLException {
        return userDAO.getByEmail(email);
    }

    /**
     * Authenticate user with email and password
     * 
     * @param email    User's email
     * @param password User's password (should be hashed in production)
     * @return User object if authentication successful, null otherwise
     */
    public User authenticateUser(String email, String password) throws SQLException {
        User user = userDAO.getByEmail(email);
        if (user != null && user.getPasswordHash().equals(password)) {
            return user;
        }
        return null;
    }
}
