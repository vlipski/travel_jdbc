package by.andersenlab.service.impl;

import by.andersenlab.connection.ConnectionManager;
import by.andersenlab.pojo.User;
import by.andersenlab.repository.UserRepository;
import by.andersenlab.repository.impl.UserRepositoryImpl;
import by.andersenlab.service.ServiceException;
import by.andersenlab.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class UserServiceImpl extends AbstractServices implements UserService {

    private static volatile UserService INSTANCE = null;

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        if (INSTANCE == null) {
            synchronized (UserServiceImpl.class) {
                INSTANCE = new UserServiceImpl();
                return INSTANCE;
            }
        }
        return INSTANCE;
    }

    @Override
    public User registration(User user) throws ServiceException {
        Connection connection = ConnectionManager.getConnection(2);
        UserRepository userRepository = UserRepositoryImpl.getInstance();
        try {
            startTransaction(connection);
            User existUser = userRepository.findByLogin(user.getLogin());
            if (existUser != null) {
                throw new ServiceException("User with that username exists");
            }
            Long id = userRepository.save(user);
            commit(connection);
            user.setId(id);
            return user;
        } catch (SQLException e) {
            rollback(connection);
            log.error("Error registration " + e.getMessage());
            throw new ServiceException("Error registration");
        }
    }
}
