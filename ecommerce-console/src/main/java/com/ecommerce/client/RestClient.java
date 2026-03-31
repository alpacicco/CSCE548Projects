package com.ecommerce.client;

import com.ecommerce.model.User;
import com.ecommerce.model.Product;
import com.ecommerce.model.Category;
import com.ecommerce.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.util.Arrays;
import java.util.List;

/**
 * REST Client for E-Commerce API
 * 
 * This client communicates with the REST services (Service Layer) instead of
 * directly
 * accessing the Business Layer or Data Layer. It demonstrates proper
 * architectural separation.
 * 
 * USAGE:
 * 1. Start the REST API service first: mvn spring-boot:run (or run
 * Application.java)
 * 2. Then run this client to test the services
 */
public class RestClient {
    private final String baseUrl;
    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    /**
     * Constructor
     * 
     * @param baseUrl Base URL of the REST API (e.g., http://localhost:8080/api)
     */
    public RestClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.httpClient = HttpClients.createDefault();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    // ============== USER OPERATIONS ==============

    /**
     * Create a new user
     */
    public User createUser(User user) throws Exception {
        String json = objectMapper.writeValueAsString(user);
        HttpPost request = new HttpPost(baseUrl + "/users");
        request.setEntity(new StringEntity(json));
        request.setHeader("Content-Type", "application/json");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            if (response.getCode() >= 200 && response.getCode() < 300) {
                return objectMapper.readValue(responseBody, User.class);
            } else {
                throw new RuntimeException("Failed to create user: " + responseBody);
            }
        }
    }

    /**
     * Get user by ID
     */
    public User getUserById(Integer id) throws Exception {
        HttpGet request = new HttpGet(baseUrl + "/users/" + id);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            if (response.getCode() == 200) {
                return objectMapper.readValue(responseBody, User.class);
            } else if (response.getCode() == 404) {
                return null;
            } else {
                throw new RuntimeException("Failed to get user: " + responseBody);
            }
        }
    }

    /**
     * Get all users
     */
    public List<User> getAllUsers() throws Exception {
        HttpGet request = new HttpGet(baseUrl + "/users");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            if (response.getCode() == 200) {
                return Arrays.asList(objectMapper.readValue(responseBody, User[].class));
            } else {
                throw new RuntimeException("Failed to get users: " + responseBody);
            }
        }
    }

    /**
     * Update user
     */
    public boolean updateUser(Integer id, User user) throws Exception {
        String json = objectMapper.writeValueAsString(user);
        HttpPut request = new HttpPut(baseUrl + "/users/" + id);
        request.setEntity(new StringEntity(json));
        request.setHeader("Content-Type", "application/json");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            return response.getCode() >= 200 && response.getCode() < 300;
        }
    }

    /**
     * Delete user
     */
    public boolean deleteUser(Integer id) throws Exception {
        HttpDelete request = new HttpDelete(baseUrl + "/users/" + id);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            return response.getCode() >= 200 && response.getCode() < 300;
        }
    }

    // ============== PRODUCT OPERATIONS ==============

    /**
     * Create a new product
     */
    public Product createProduct(Product product) throws Exception {
        String json = objectMapper.writeValueAsString(product);
        HttpPost request = new HttpPost(baseUrl + "/products");
        request.setEntity(new StringEntity(json));
        request.setHeader("Content-Type", "application/json");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            if (response.getCode() >= 200 && response.getCode() < 300) {
                return objectMapper.readValue(responseBody, Product.class);
            } else {
                throw new RuntimeException("Failed to create product: " + responseBody);
            }
        }
    }

    /**
     * Get product by ID
     */
    public Product getProductById(Integer id) throws Exception {
        HttpGet request = new HttpGet(baseUrl + "/products/" + id);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            if (response.getCode() == 200) {
                return objectMapper.readValue(responseBody, Product.class);
            } else if (response.getCode() == 404) {
                return null;
            } else {
                throw new RuntimeException("Failed to get product: " + responseBody);
            }
        }
    }

    /**
     * Get all products
     */
    public List<Product> getAllProducts() throws Exception {
        HttpGet request = new HttpGet(baseUrl + "/products");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            if (response.getCode() == 200) {
                return Arrays.asList(objectMapper.readValue(responseBody, Product[].class));
            } else {
                throw new RuntimeException("Failed to get products: " + responseBody);
            }
        }
    }

    /**
     * Update product
     */
    public boolean updateProduct(Integer id, Product product) throws Exception {
        String json = objectMapper.writeValueAsString(product);
        HttpPut request = new HttpPut(baseUrl + "/products/" + id);
        request.setEntity(new StringEntity(json));
        request.setHeader("Content-Type", "application/json");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            return response.getCode() >= 200 && response.getCode() < 300;
        }
    }

    /**
     * Delete product
     */
    public boolean deleteProduct(Integer id) throws Exception {
        HttpDelete request = new HttpDelete(baseUrl + "/products/" + id);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            return response.getCode() >= 200 && response.getCode() < 300;
        }
    }

    // ============== CATEGORY OPERATIONS ==============

    /**
     * Create a new category
     */
    public Category createCategory(Category category) throws Exception {
        String json = objectMapper.writeValueAsString(category);
        HttpPost request = new HttpPost(baseUrl + "/categories");
        request.setEntity(new StringEntity(json));
        request.setHeader("Content-Type", "application/json");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            if (response.getCode() >= 200 && response.getCode() < 300) {
                return objectMapper.readValue(responseBody, Category.class);
            } else {
                throw new RuntimeException("Failed to create category: " + responseBody);
            }
        }
    }

    /**
     * Get category by ID
     */
    public Category getCategoryById(Integer id) throws Exception {
        HttpGet request = new HttpGet(baseUrl + "/categories/" + id);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            if (response.getCode() == 200) {
                return objectMapper.readValue(responseBody, Category.class);
            } else if (response.getCode() == 404) {
                return null;
            } else {
                throw new RuntimeException("Failed to get category: " + responseBody);
            }
        }
    }

    /**
     * Delete category
     */
    public boolean deleteCategory(Integer id) throws Exception {
        HttpDelete request = new HttpDelete(baseUrl + "/categories/" + id);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            return response.getCode() >= 200 && response.getCode() < 300;
        }
    }

    /**
     * Close the HTTP client
     */
    public void close() throws Exception {
        httpClient.close();
    }
}
