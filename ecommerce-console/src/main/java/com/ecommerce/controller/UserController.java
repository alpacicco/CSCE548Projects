package com.ecommerce.controller;

import com.ecommerce.model.User;
import com.ecommerce.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

/**
 * REST API Controller: UserController
 * This controller exposes RESTful endpoints for User operations.
 * All endpoints invoke the Business Layer (UserService).
 * 
 * HOSTING INSTRUCTIONS:
 * This service can be hosted on multiple platforms:
 * 
 * 1. LOCAL HOSTING (Development):
 * - Run: mvn spring-boot:run
 * - Access at: http://localhost:8080/api/users
 * 
 * 2. HEROKU:
 * - Create Procfile: web: java -jar target/ecommerce-console-1.0-SNAPSHOT.jar
 * - Deploy: git push heroku main
 * 
 * 3. AWS ELASTIC BEANSTALK:
 * - Package: mvn clean package
 * - Deploy JAR through AWS Console or CLI
 * 
 * 4. DOCKER:
 * - Create Dockerfile with: FROM openjdk:17-alpine
 * - Build: docker build -t ecommerce-api .
 * - Run: docker run -p 8080:8080 ecommerce-api
 * 
 * 5. AZURE APP SERVICE:
 * - Right-click project in VS Code -> Deploy to Azure
 * - Or use Azure CLI: az webapp up
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    /**
     * POST /api/users - Register a new user
     * 
     * @param user User object from request body
     * @return Created user with HTTP 201
     */
    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User createdUser = userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * GET /api/users/{id} - Get user by ID
     * 
     * @param id User ID
     * @return User object or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * GET /api/users - Get all users
     * 
     * @return List of all users
     */
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * PUT /api/users/{id} - Update user
     * 
     * @param id   User ID
     * @param user Updated user object
     * @return Success message or error
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody User user) {
        try {
            user.setUserId(id);
            boolean updated = userService.updateUser(user);
            if (updated) {
                return ResponseEntity.ok("User updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to update user");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * DELETE /api/users/{id} - Delete user
     * 
     * @param id User ID
     * @return Success message or error
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        try {
            boolean deleted = userService.deleteUser(id);
            if (deleted) {
                return ResponseEntity.ok("User deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * GET /api/users/email/{email} - Find user by email
     * 
     * @param email User email
     * @return User object or 404 if not found
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        try {
            User user = userService.getUserByEmail(email);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * POST /api/users/authenticate - Authenticate user
     * 
     * @param credentials Map containing email and password
     * @return User object if authenticated, 401 if not
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody java.util.Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");
            User user = userService.authenticateUser(email, password);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }
}
