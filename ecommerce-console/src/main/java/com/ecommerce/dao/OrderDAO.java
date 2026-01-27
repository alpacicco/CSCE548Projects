package com.ecommerce.dao;

import com.ecommerce.model.Order;
import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {
    Order create(Order order) throws SQLException;
    Order getById(Integer id) throws SQLException;
    List<Order> getAll() throws SQLException;
    boolean update(Order order) throws SQLException;
    boolean delete(Integer id) throws SQLException;
    List<Order> getByUserId(Integer userId) throws SQLException;
}
