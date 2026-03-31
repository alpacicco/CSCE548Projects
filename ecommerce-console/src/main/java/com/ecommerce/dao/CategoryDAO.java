package com.ecommerce.dao;

import com.ecommerce.model.Category;
import java.sql.SQLException;
import java.util.List;

public interface CategoryDAO {
    Category create(Category category) throws SQLException;

    Category getById(Integer id) throws SQLException;

    List<Category> getAll() throws SQLException;

    boolean update(Category category) throws SQLException;

    boolean delete(Integer id) throws SQLException;
}
