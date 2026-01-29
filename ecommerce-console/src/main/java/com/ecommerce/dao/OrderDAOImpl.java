package com.ecommerce.dao;

import com.ecommerce.db.DatabaseConnection;
import com.ecommerce.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public Order create(Order order) throws SQLException {
        String sql = "INSERT INTO orders (user_id, order_number, status, total_amount, shipping_address_id, billing_address_id, notes) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, order.getUserId());
            stmt.setString(2, order.getOrderNumber());
            stmt.setString(3, order.getStatus());
            stmt.setBigDecimal(4, order.getTotalAmount());

            if (order.getShippingAddressId() != null) {
                stmt.setInt(5, order.getShippingAddressId());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            if (order.getBillingAddressId() != null) {
                stmt.setInt(6, order.getBillingAddressId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            stmt.setString(7, order.getNotes());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setOrderId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        }

        return order;
    }

    @Override
    public Order getById(Integer id) throws SQLException {
        String sql = "SELECT * FROM orders WHERE order_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToOrder(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Order> getAll() throws SQLException {
        String sql = "SELECT * FROM orders ORDER BY order_date DESC";
        List<Order> orders = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }
        }

        return orders;
    }

    @Override
    public boolean update(Order order) throws SQLException {
        String sql = "UPDATE orders SET user_id = ?, order_number = ?, status = ?, total_amount = ?, " +
                "shipping_address_id = ?, billing_address_id = ?, notes = ? WHERE order_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, order.getUserId());
            stmt.setString(2, order.getOrderNumber());
            stmt.setString(3, order.getStatus());
            stmt.setBigDecimal(4, order.getTotalAmount());

            if (order.getShippingAddressId() != null) {
                stmt.setInt(5, order.getShippingAddressId());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            if (order.getBillingAddressId() != null) {
                stmt.setInt(6, order.getBillingAddressId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            stmt.setString(7, order.getNotes());
            stmt.setInt(8, order.getOrderId());

            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        String sql = "DELETE FROM orders WHERE order_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public List<Order> getByUserId(Integer userId) throws SQLException {
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";
        List<Order> orders = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapResultSetToOrder(rs));
                }
            }
        }

        return orders;
    }

    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setUserId(rs.getInt("user_id"));
        order.setOrderNumber(rs.getString("order_number"));
        order.setStatus(rs.getString("status"));
        order.setTotalAmount(rs.getBigDecimal("total_amount"));
        order.setShippingAddressId((Integer) rs.getObject("shipping_address_id"));
        order.setBillingAddressId((Integer) rs.getObject("billing_address_id"));
        order.setOrderDate(rs.getTimestamp("order_date"));
        order.setShippedDate(rs.getTimestamp("shipped_date"));
        order.setDeliveredDate(rs.getTimestamp("delivered_date"));
        order.setNotes(rs.getString("notes"));
        order.setCreatedAt(rs.getTimestamp("created_at"));
        order.setUpdatedAt(rs.getTimestamp("updated_at"));
        return order;
    }
}
