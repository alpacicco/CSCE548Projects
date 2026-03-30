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
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(List.of(
                Map.of("orderId", 1, "status", "SHIPPED"),
                Map.of("orderId", 2, "status", "PENDING")
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(
                Map.of("orderId", id, "status", "SHIPPED")
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrdersByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(List.of(
                Map.of("orderId", 1, "userId", userId)
        ));
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> order) {
        return ResponseEntity.ok("Order created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Integer id,
                                         @RequestBody Map<String, Object> order) {
        return ResponseEntity.ok("Order updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer id) {
        return ResponseEntity.ok("Order deleted successfully");
    }
}
