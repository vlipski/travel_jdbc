package by.andersenlab.service.impl;

import by.andersenlab.pojo.User;
import by.andersenlab.repository.UserRepository;
import by.andersenlab.repository.impl.UserRepositoryImpl;
import by.andersenlab.service.ServiceException;
import by.andersenlab.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserServiceImplTest {


    UserRepository userRepository;
    UserService userService;

    @Before
    public void setUp() throws Exception {
        userRepository = UserRepositoryImpl.getInstance();
        userService = UserServiceImpl.getInstance();
        userRepository.save(new User("password", "login"));
    }

    @After
    public void tearDown() throws Exception {
        List<User> list = userRepository.list();
        list.forEach(userRepository :: remove);
    }


    @Test
    public void registrationOkTest() throws ServiceException {
        User user = userService.registration(new User("password", "lipski"));
        assertNotNull(user.getId());
    }

    @Test(expected = ServiceException.class)
    public void registrationErrorTest() throws ServiceException {
        userService.registration(new User("password", "login"));
    }
}