package by.andersenlab.repository.impl;

import by.andersenlab.pojo.User;
import by.andersenlab.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserRepositoryImplTest {

    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = UserRepositoryImpl.getInstance();
    }


    @Test
    public void saveAndGetTest() {
        Long id = userRepository.save(new User("password", "login"));
        User user = userRepository.get(id);
        assertEquals(user.getPassword(), "password");
    }

    @Test
    public void updateTest() {
        Long id = userRepository.save(new User("password", "login"));
        userRepository.update(new User(id, "password", "lipski"));
        User user = userRepository.get(id);
        assertEquals(user.getLogin(), "lipski");
    }

    @Test
    public void removeTest() {
        List<User> list = userRepository.list();
        list.forEach(userRepository::remove);
        list = userRepository.list();
        assertEquals(list.size(), 0);
    }


}