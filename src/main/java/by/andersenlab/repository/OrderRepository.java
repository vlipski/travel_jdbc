package by.andersenlab.repository;

import by.andersenlab.pojo.Order;
import by.andersenlab.pojo.User;

import java.sql.SQLException;

public interface OrderRepository {

    Long save(Order order ) throws RepositoryException;

    int update(Order order) throws RepositoryException;

    int remove(Order order) throws RepositoryException;

    Order get(Long id) throws RepositoryException;
}

