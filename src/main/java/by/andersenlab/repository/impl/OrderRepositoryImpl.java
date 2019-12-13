package by.andersenlab.repository.impl;

import by.andersenlab.connection.ConnectionManager;
import by.andersenlab.pojo.Order;
import by.andersenlab.repository.OrderRepository;

import java.sql.*;

public class OrderRepositoryImpl implements OrderRepository {

    private static volatile OrderRepository INSTANCE = null;
    private Connection connection;
    private PreparedStatement statement;
    private final String SAVE = "INSERT INTO `order` (date, user_id) VALUES (now(), ?)";
    private final String UPDATE = "UPDATE `order` SET  date = ?, user_id = ?  WHERE id = ?";
    private final String REMOVE = "DELETE FROM `order` WHERE id = ?";
    private final String SELECT = "SELECT * FROM `order` WHERE id = ?";

    private OrderRepositoryImpl() {

    }

    public static OrderRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (UserRepositoryImpl.class) {
                INSTANCE = new OrderRepositoryImpl();
                return INSTANCE;
            }
        }
        return INSTANCE;
    }

    @Override
    public Long save(Order order ) throws SQLException {
        connection = ConnectionManager.getConnection(2);
        statement = connection.prepareStatement(
                SAVE, Statement.RETURN_GENERATED_KEYS);
        statement.setLong(1, order.getUserId());
        statement.executeUpdate();
        ResultSet results = statement.getGeneratedKeys();
        Long id = null;
        if (results.next()) {
            id = results.getLong(1);
        }
        results.close();
        connection.close();
        return id;
    }

    @Override
    public int update(Order order) throws SQLException {
        connection = ConnectionManager.getConnection(2);
        statement = connection.prepareStatement(UPDATE);
        statement.setDate(1, order.getDate());
        statement.setLong(2, order.getUserId());
        statement.setLong(3,order.getId());
        int number = statement.executeUpdate();
        connection.close();
        return number;
    }

    @Override
    public int remove(Order order) throws SQLException {
        connection = ConnectionManager.getConnection(2);
        statement = connection.prepareStatement(REMOVE);
        statement.setLong(1,order.getId());
        int number = statement.executeUpdate();
        connection.close();
        return number;
    }

    @Override
    public Order get(Long id) throws SQLException {
        connection = ConnectionManager.getConnection(2);
        statement = connection.prepareStatement(SELECT);
        statement.setLong(1, id);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            Order order = populate(result);
            result.close();
            connection.close();
            return order;
        }
        result.close();
        connection.close();
        throw new SQLException("order not exist");
    }

    private Order populate(ResultSet result) throws SQLException {
        Order order = new Order();
        order.setId(result.getLong(1));
        order.setDate(result.getDate(2));
        order.setUserId(result.getLong(3));
        return order;
    }

}
