package by.andersenlab.repository;

import by.andersenlab.pojo.Order;
import by.andersenlab.pojo.User;

import java.sql.SQLException;

public interface OrderRepository {
    Long save(Order order ) throws SQLException, IllegalAccessException;

    int update(Order order) throws SQLException;

    int remove(Order order) throws SQLException;

    Order get(Long id) throws SQLException;
}

