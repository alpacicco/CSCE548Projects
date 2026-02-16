package com.ecommerce.controller;

import com.ecommerce.model.Order;
import com.ecommerce.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

/**
 * REST API Controller: OrderController
 * This controller exposes RESTful endpoints for Order operations.
 * All endpoints invoke the Business Layer (OrderService).
 */
@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    private final OrderService orderService;

    public OrderController() {
        this.orderService = new OrderService();
    }

    /**
     * POST /api/orders - Create a new order
     * 
     * @param order Order object from request body
     * @return Created order with HTTP 201
     */
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        try {
            Order createdOrder = orderService.createOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * GET /api/orders/{id} - Get order by ID
     * 
     * @param id Order ID
     * @return Order object or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Integer id) {
        try {
            Order order = orderService.getOrderById(id);
            if (order != null) {
                return ResponseEntity.ok(order);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * GET /api/orders - Get all orders
     * 
     * @return List of all orders
     */
    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();
            return ResponseEntity.ok(orders);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * PUT /api/orders/{id} - Update order
     * 
     * @param id    Order ID
     * @param order Updated order object
     * @return Success message or error
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Integer id, @RequestBody Order order) {
        try {
            order.setOrderId(id);
            boolean updated = orderService.updateOrder(order);
            if (updated) {
                return ResponseEntity.ok("Order updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to update order");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * DELETE /api/orders/{id} - Delete order
     * 
     * @param id Order ID
     * @return Success message or error
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer id) {
        try {
            boolean deleted = orderService.deleteOrder(id);
            if (deleted) {
                return ResponseEntity.ok("Order deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * GET /api/orders/user/{userId} - Get orders by user ID
     * 
     * @param userId User ID
     * @return List of orders for the user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable Integer userId) {
        try {
            List<Order> orders = orderService.getOrdersByUserId(userId);
            return ResponseEntity.ok(orders);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * PUT /api/orders/{id}/status - Update order status
     * 
     * @param id         Order ID
     * @param statusData Map containing new status
     * @return Success message or error
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Integer id,
            @RequestBody java.util.Map<String, String> statusData) {
        try {
            String status = statusData.get("status");
            boolean updated = orderService.updateOrderStatus(id, status);
            if (updated) {
                return ResponseEntity.ok("Order status updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to update order status");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }

    /**
     * GET /api/orders/user/{userId}/count - Get order count for user
     * 
     * @param userId User ID
     * @return Order count
     */
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<?> getUserOrderCount(@PathVariable Integer userId) {
        try {
            int count = orderService.getUserOrderCount(userId);
            return ResponseEntity.ok(java.util.Map.of("count", count));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        }
    }
}
