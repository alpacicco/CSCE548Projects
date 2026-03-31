package com.ecommerce.dao;

import com.ecommerce.model.Product;
import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    Product create(Product product) throws SQLException;

    Product getById(Integer id) throws SQLException;

    List<Product> getAll() throws SQLException;

    boolean update(Product product) throws SQLException;

    boolean delete(Integer id) throws SQLException;

    List<Product> getByCategory(Integer categoryId) throws SQLException;
}
