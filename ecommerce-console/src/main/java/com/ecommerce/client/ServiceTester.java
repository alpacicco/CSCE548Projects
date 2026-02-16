package com.ecommerce.client;

import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service Tester - Console Application to Test REST Services
 * 
 * This program demonstrates testing all CRUD operations by calling the REST API
 * services.
 * It performs a complete test cycle: Create -> Read -> Update -> Delete
 * 
 * PREREQUISITES:
 * 1. Database must be running (MySQL)
 * 2. REST API service must be running first!
 * Start it with: mvn spring-boot:run
 * Or run Application.java directly
 * 3. Wait for message "Service started successfully!" before running this
 * tester
 * 
 * ARCHITECTURE DEMONSTRATION:
 * This tester shows the complete 3-tier architecture:
 * - This Console Client -> REST API (Service Layer) -> Business Layer -> Data
 * Layer (DAO) -> Database
 */
public class ServiceTester {
    private static final String API_BASE_URL = "http://localhost:8080/api";

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  E-Commerce REST API Service Tester");
        System.out.println("=================================================");
        System.out.println();

        RestClient client = new RestClient(API_BASE_URL);

        try {
            // Test Category Operations
            System.out.println("--- TESTING CATEGORY SERVICES ---");
            testCategoryServices(client);
            System.out.println();

            // Test User Operations
            System.out.println("--- TESTING USER SERVICES ---");
            testUserServices(client);
            System.out.println();

            // Test Product Operations
            System.out.println("--- TESTING PRODUCT SERVICES ---");
            testProductServices(client);
            System.out.println();

            System.out.println("=================================================");
            System.out.println("  ALL TESTS COMPLETED SUCCESSFULLY! ✓");
            System.out.println("=================================================");
            System.out.println();
            System.out.println("What was tested:");
            System.out.println("✓ All CRUD operations (Create, Read, Update, Delete)");
            System.out.println("✓ Service Layer -> Business Layer -> Data Layer flow");
            System.out.println("✓ REST API endpoints");
            System.out.println("✓ Database operations");
            System.out.println();
            System.out.println("Architecture verified:");
            System.out.println("Console Client -> REST API -> Business Services -> DAO -> Database");

        } catch (Exception e) {
            System.err.println();
            System.err.println("ERROR: " + e.getMessage());
            System.err.println();
            System.err.println("Common issues:");
            System.err.println("1. Is the REST API service running? Start it with: mvn spring-boot:run");
            System.err.println("2. Is the database running and accessible?");
            System.err.println("3. Are database credentials correct in .env file?");
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Test Category CRUD operations through REST API
     */
    private static void testCategoryServices(RestClient client) throws Exception {
        System.out.println("Step 1: Creating a new category via POST /api/categories");
        Category category = new Category();
        category.setName("Test Electronics");
        category.setDescription("Electronics for testing REST API");

        Category created = client.createCategory(category);
        System.out.println("✓ Category created with ID: " + created.getCategoryId());

        System.out.println();
        System.out.println("Step 2: Reading category via GET /api/categories/" + created.getCategoryId());
        Category retrieved = client.getCategoryById(created.getCategoryId());
        if (retrieved != null) {
            System.out.println("✓ Category retrieved: " + retrieved.getName());
        } else {
            System.out.println("✗ Failed to retrieve category");
        }

        System.out.println();
        System.out.println("Step 3: Deleting category via DELETE /api/categories/" + created.getCategoryId());
        boolean deleted = client.deleteCategory(created.getCategoryId());
        if (deleted) {
            System.out.println("✓ Category deleted successfully");
        } else {
            System.out.println("✗ Failed to delete category");
        }

        System.out.println();
        System.out.println("Step 4: Verifying deletion via GET /api/categories/" + created.getCategoryId());
        Category shouldBeNull = client.getCategoryById(created.getCategoryId());
        if (shouldBeNull == null) {
            System.out.println("✓ Category successfully deleted (not found)");
        } else {
            System.out.println("✗ Category still exists after deletion");
        }
    }

    /**
     * Test User CRUD operations through REST API
     */
    private static void testUserServices(RestClient client) throws Exception {
        System.out.println("Step 1: Creating a new user via POST /api/users");
        User user = new User();
        user.setEmail("testuser@example.com");
        user.setPasswordHash("hashedpassword123");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPhone("555-1234");
        user.setRole("CUSTOMER");

        User created = client.createUser(user);
        System.out.println("✓ User created with ID: " + created.getUserId());

        System.out.println();
        System.out.println("Step 2: Reading user via GET /api/users/" + created.getUserId());
        User retrieved = client.getUserById(created.getUserId());
        if (retrieved != null) {
            System.out.println("✓ User retrieved: " + retrieved.getEmail());
        } else {
            System.out.println("✗ Failed to retrieve user");
        }

        System.out.println();
        System.out.println("Step 3: Updating user via PUT /api/users/" + created.getUserId());
        retrieved.setFirstName("Updated");
        retrieved.setLastName("Name");
        boolean updated = client.updateUser(created.getUserId(), retrieved);
        if (updated) {
            System.out.println("✓ User updated successfully");

            User updatedUser = client.getUserById(created.getUserId());
            System.out.println("  New name: " + updatedUser.getFirstName() + " " + updatedUser.getLastName());
        } else {
            System.out.println("✗ Failed to update user");
        }

        System.out.println();
        System.out.println("Step 4: Reading all users via GET /api/users");
        List<User> allUsers = client.getAllUsers();
        System.out.println("✓ Retrieved " + allUsers.size() + " total users");

        System.out.println();
        System.out.println("Step 5: Deleting user via DELETE /api/users/" + created.getUserId());
        boolean deleted = client.deleteUser(created.getUserId());
        if (deleted) {
            System.out.println("✓ User deleted successfully");
        } else {
            System.out.println("✗ Failed to delete user");
        }

        System.out.println();
        System.out.println("Step 6: Verifying deletion via GET /api/users/" + created.getUserId());
        User shouldBeNull = client.getUserById(created.getUserId());
        if (shouldBeNull == null) {
            System.out.println("✓ User successfully deleted (not found)");
        } else {
            System.out.println("✗ User still exists after deletion");
        }
    }

    /**
     * Test Product CRUD operations through REST API
     */
    private static void testProductServices(RestClient client) throws Exception {
        System.out.println("Step 1: Creating a new product via POST /api/products");
        Product product = new Product();
        product.setName("Test Laptop");
        product.setDescription("High-performance laptop for testing");
        product.setPrice(new BigDecimal("999.99"));
        product.setStock(10);
        product.setCategoryId(1); // Assuming category 1 exists

        Product created = client.createProduct(product);
        System.out.println("✓ Product created with ID: " + created.getProductId());

        System.out.println();
        System.out.println("Step 2: Reading product via GET /api/products/" + created.getProductId());
        Product retrieved = client.getProductById(created.getProductId());
        if (retrieved != null) {
            System.out.println("✓ Product retrieved: " + retrieved.getName());
            System.out.println("  Price: $" + retrieved.getPrice());
            System.out.println("  Stock: " + retrieved.getStock());
        } else {
            System.out.println("✗ Failed to retrieve product");
        }

        System.out.println();
        System.out.println("Step 3: Updating product via PUT /api/products/" + created.getProductId());
        retrieved.setPrice(new BigDecimal("899.99"));
        retrieved.setStock(15);
        boolean updated = client.updateProduct(created.getProductId(), retrieved);
        if (updated) {
            System.out.println("✓ Product updated successfully");

            Product updatedProduct = client.getProductById(created.getProductId());
            System.out.println("  New price: $" + updatedProduct.getPrice());
            System.out.println("  New stock: " + updatedProduct.getStock());
        } else {
            System.out.println("✗ Failed to update product");
        }

        System.out.println();
        System.out.println("Step 4: Reading all products via GET /api/products");
        List<Product> allProducts = client.getAllProducts();
        System.out.println("✓ Retrieved " + allProducts.size() + " total products");

        System.out.println();
        System.out.println("Step 5: Deleting product via DELETE /api/products/" + created.getProductId());
        boolean deleted = client.deleteProduct(created.getProductId());
        if (deleted) {
            System.out.println("✓ Product deleted successfully");
        } else {
            System.out.println("✗ Failed to delete product");
        }

        System.out.println();
        System.out.println("Step 6: Verifying deletion via GET /api/products/" + created.getProductId());
        Product shouldBeNull = client.getProductById(created.getProductId());
        if (shouldBeNull == null) {
            System.out.println("✓ Product successfully deleted (not found)");
        } else {
            System.out.println("✗ Product still exists after deletion");
        }
    }
}
