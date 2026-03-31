package com.ecommerce.service;

import com.ecommerce.dao.OrderDAO;
import com.ecommerce.dao.OrderDAOImpl;
import com.ecommerce.model.Order;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Business Layer: OrderService
 * This service provides business logic for Order operations and invokes the
 * Data Access Layer (DAO).
 * All CRUD operations from OrderDAO are exposed through this service.
 */
@Service
public class OrderService {
    private final OrderDAO orderDAO;

    public OrderService() {
        this.orderDAO = new OrderDAOImpl();
    }

    /**
     * Create a new order
     * 
     * @param order Order object to create
     * @return Created order with generated ID
     */
    public Order createOrder(Order order) throws SQLException {
        // Business logic: Validate order has user ID and total amount
        if (order.getUserId() == null) {
            throw new IllegalArgumentException("Order must have a user ID");
        }
        if (order.getTotalAmount() == null) {
            throw new IllegalArgumentException("Order must have a total amount");
        }
        return orderDAO.create(order);
    }

    /**
     * Retrieve order by ID
     * 
     * @param id Order ID
     * @return Order object or null if not found
     */
    public Order getOrderById(Integer id) throws SQLException {
        return orderDAO.getById(id);
    }

    /**
     * Get all orders
     * 
     * @return List of all orders
     */
    public List<Order> getAllOrders() throws SQLException {
        return orderDAO.getAll();
    }

    /**
     * Update existing order
     * 
     * @param order Order object with updated information
     * @return true if update was successful
     */
    public boolean updateOrder(Order order) throws SQLException {
        // Business logic: Validate order exists
        Order existing = orderDAO.getById(order.getOrderId());
        if (existing == null) {
            throw new IllegalArgumentException("Order not found with ID: " + order.getOrderId());
        }
        return orderDAO.update(order);
    }

    /**
     * Delete an order by ID
     * 
     * @param id Order ID to delete
     * @return true if deletion was successful
     */
    public boolean deleteOrder(Integer id) throws SQLException {
        return orderDAO.delete(id);
    }

    /**
     * Get orders by user ID
     * 
     * @param userId User ID
     * @return List of orders for the specified user
     */
    public List<Order> getOrdersByUserId(Integer userId) throws SQLException {
        return orderDAO.getByUserId(userId);
    }

    /**
     * Update order status
     * 
     * @param orderId Order ID
     * @param status  New order status
     * @return true if update was successful
     */
    public boolean updateOrderStatus(Integer orderId, String status) throws SQLException {
        Order order = orderDAO.getById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }
        order.setStatus(status);
        return orderDAO.update(order);
    }

    /**
     * Calculate order statistics for a user
     * 
     * @param userId User ID
     * @return Number of orders placed by the user
     */
    public int getUserOrderCount(Integer userId) throws SQLException {
        return orderDAO.getByUserId(userId).size();
    }
}
