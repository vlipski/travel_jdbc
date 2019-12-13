package by.andersenlab.repository.impl;

import by.andersenlab.connection.ConnectionManager;
import by.andersenlab.pojo.User;
import by.andersenlab.repository.RepositoryException;
import by.andersenlab.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private static volatile UserRepository INSTANCE = null;
    private static final String SAVE = "INSERT INTO user (password, login) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE user SET  password = ?, login = ?  WHERE id = ?";
    private static final String REMOVE = "DELETE FROM user WHERE id = ?";
    private static final String SELECT = "SELECT * FROM user WHERE id = ?";
    private static final String SELECT_BY_LOGIN = "SELECT * FROM user WHERE login = ?";
    private static final String SELECT_ALL = "SELECT * FROM user";


    private UserRepositoryImpl() {
    }

    public static UserRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (UserRepositoryImpl.class) {
                INSTANCE = new UserRepositoryImpl();
                return INSTANCE;
            }
        }
        return INSTANCE;
    }

    @Override
    public Long save(User user) throws RepositoryException {
        try {
            Connection connection = ConnectionManager.getConnection(2);
            PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getLogin());
            statement.executeUpdate();
            ResultSet results = statement.getGeneratedKeys();
            Long id = null;
            if (results.next()) {
                id = results.getLong(1);
            }
            return id;
        } catch (SQLException e) {
            log.error("Save user error ", e);
            throw new RepositoryException("Save user error " + e.getMessage());
        }


    }

    @Override
    public int update(User user) throws RepositoryException {
        try {
            Connection connection = ConnectionManager.getConnection(2);
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getLogin());
            statement.setLong(3, user.getId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Update user error ", e);
            throw new RepositoryException("Update user error " + e.getMessage());
        }
    }

    @Override
    public int remove(User user) throws RepositoryException {
        try {
            Connection connection = ConnectionManager.getConnection(2);
            PreparedStatement statement = connection.prepareStatement(REMOVE);
            statement.setLong(1, user.getId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Remove user error ", e);
            throw new RepositoryException("Remove user error " + e.getMessage());
        }
    }

    @Override
    public User get(Long id) throws RepositoryException {
        try {
            Connection connection = ConnectionManager.getConnection(2);
            PreparedStatement statement = connection.prepareStatement(SELECT);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                User user = populate(result);
                return user;
            }
            return null;
        } catch (SQLException e) {
            log.error("Get user error ", e);
            throw new RepositoryException("Get user error " + e.getMessage());
        }
    }

    @Override
    public List<User> list() throws RepositoryException {
        List<User> list = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getConnection(2);
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                list.add(populate(result));
            }
            return list;
        } catch (SQLException e) {
            log.error("Get user error ", e);
            throw new RepositoryException("Get user error " + e.getMessage());
        }
    }

    @Override
    public User findByLogin(String login) throws RepositoryException {
        try {
            Connection connection = ConnectionManager.getConnection(2);
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_LOGIN);
            statement.setString(1, login);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                User user = populate(result);
                return user;
            }
            return null;
        } catch (SQLException e) {
            log.error("Find user error ", e);
            throw new RepositoryException("Find user error " + e.getMessage());
        }
    }

    private User populate(ResultSet result) throws SQLException {
        User user = new User();
        user.setId(result.getLong(1));
        user.setPassword(result.getString(2));
        user.setLogin(result.getString(3));
        return user;
    }

}
