package com.ecommerce.dao;

import com.ecommerce.model.OrderItem;
import java.sql.SQLException;
import java.util.List;

public interface OrderItemDAO {
    OrderItem create(OrderItem orderItem) throws SQLException;
    OrderItem getById(Integer id) throws SQLException;
    List<OrderItem> getAll() throws SQLException;
    boolean update(OrderItem orderItem) throws SQLException;
    boolean delete(Integer id) throws SQLException;
    List<OrderItem> getByOrderId(Integer orderId) throws SQLException;
}
