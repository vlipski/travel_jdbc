package by.andersenlab.connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PoledConnections {

    private static PoledConnections poledConnections;
    private ComboPooledDataSource poledDataSource;

    private PoledConnections() throws IOException, SQLException, PropertyVetoException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        poledDataSource = new ComboPooledDataSource();
        poledDataSource.setDriverClass(resourceBundle.getString("connection.driver_class"));
        poledDataSource.setJdbcUrl(resourceBundle.getString("connection.url"));
        poledDataSource.setUser(resourceBundle.getString("connection.username"));
        poledDataSource.setPassword(resourceBundle.getString("connection.password"));
        poledDataSource.setMinPoolSize(Integer.parseInt(resourceBundle.getString("connection.min_pool_size")));
        poledDataSource.setMaxPoolSize(Integer.parseInt(resourceBundle.getString("connection.max_pool_size")));
        poledDataSource.setAcquireIncrement(Integer.parseInt(resourceBundle.getString("connection.acquire_increment")));
        poledDataSource.setMaxStatements(Integer.parseInt(resourceBundle.getString("connection.max_statements")));
    }

    public static PoledConnections getInstance() throws IOException, SQLException, PropertyVetoException {
        if (poledConnections == null) {
            poledConnections = new PoledConnections();
            return poledConnections;
        } else {
            return poledConnections;
        }
    }

    public Connection getConnection(int levelIsolation) throws SQLException {
        Connection connection = poledDataSource.getConnection();
        connection.setTransactionIsolation(levelIsolation);
        return connection;
    }
}
