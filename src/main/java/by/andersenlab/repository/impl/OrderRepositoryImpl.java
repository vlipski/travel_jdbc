package by.andersenlab.repository.impl;

import by.andersenlab.connection.ConnectionManager;
import by.andersenlab.pojo.Order;
import by.andersenlab.pojo.Tour;
import by.andersenlab.repository.OrderRepository;
import by.andersenlab.repository.RepositoryException;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class OrderRepositoryImpl implements OrderRepository {

    private static volatile OrderRepository INSTANCE = null;
    private Connection connection;
    private PreparedStatement statement;
    private final String SAVE = "INSERT INTO `order` (date, user_id) VALUES (now(), ?)";
    private static final String SAVE_TOUR = "INSERT INTO tour (date_from, date_to, name, order_id) VALUES (?, ?, ?, ?";
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
    public Long save(Order order) throws RepositoryException {
        try {
            Connection connection = ConnectionManager.getConnection(2);
            connection.setAutoCommit(false);
            PreparedStatement statementOrder = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS);
            statementOrder.setLong(1, order.getUserId());
            statementOrder.executeUpdate();
            ResultSet results = statement.getGeneratedKeys();
            Tour tour = order.getTour();
            if (tour != null) {
                PreparedStatement statementTour = connection.prepareStatement(SAVE_TOUR, Statement.RETURN_GENERATED_KEYS);
                statementTour.setDate(1, tour.getDateFrom());
                statementTour.setDate(2, tour.getDateTo());
                statementTour.setString(3, tour.getName());
                statementTour.setLong(4, tour.getId());
                statementOrder.executeUpdate();
            }
            Long id = null;
            if (results.next()) {
                id = results.getLong(1);
            }
            connection.commit();
            return id;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error("Save order error ", e);
                throw new RepositoryException("Save order error " + e.getMessage());
            }
            log.error("Save order error ", e);
            throw new RepositoryException("Save order error " + e.getMessage());
        }
    }

    @Override
    public int update(Order order) throws RepositoryException {
        try {
            Connection connection = ConnectionManager.getConnection(2);
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setDate(1, order.getDate());
            statement.setLong(2, order.getUserId());
            statement.setLong(3, order.getId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Update order error ", e);
            throw new RepositoryException("Update order error " + e.getMessage());
        }
    }

    @Override
    public int remove(Order order) throws RepositoryException {
        try {
            Connection connection = ConnectionManager.getConnection(2);
            PreparedStatement statement = connection.prepareStatement(REMOVE);
            statement.setLong(1, order.getId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Remove order error ", e);
            throw new RepositoryException("Remove order error " + e.getMessage());
        }

    }

    @Override
    public Order get(Long id) throws RepositoryException {
        try {
            Connection connection = ConnectionManager.getConnection(2);
            PreparedStatement statement = connection.prepareStatement(SELECT);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Order order = populate(result);
                result.close();
                connection.close();
                return order;
            }
            return null;
        } catch (SQLException e) {
            log.error("Get order error ", e);
            throw new RepositoryException("Get order error " + e.getMessage());
        }
    }

    private Order populate(ResultSet result) throws SQLException {
        Order order = new Order();
        order.setId(result.getLong(1));
        order.setDate(result.getDate(2));
        order.setUserId(result.getLong(3));
        return order;
    }

}
