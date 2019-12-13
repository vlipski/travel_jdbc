package by.andersenlab.service.impl;

import by.andersenlab.connection.ConnectionException;
import by.andersenlab.connection.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractServices {

    public void startTransaction(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commit(Connection connection) throws SQLException {
        connection.commit();
    }

    public Connection getConnection(int level) {
        return ConnectionManager.getConnection(level);
    }

    public void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new ConnectionException("rollback error " + e.getMessage());
        }
    }
}
