package com.ecommerce.dao;

import com.ecommerce.model.User;
import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    User create(User user) throws SQLException;
    User getById(Integer id) throws SQLException;
    List<User> getAll() throws SQLException;
    boolean update(User user) throws SQLException;
    boolean delete(Integer id) throws SQLException;
    User getByEmail(String email) throws SQLException;
}
