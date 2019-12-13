package by.andersenlab.repository;

import by.andersenlab.pojo.User;

import java.util.List;

public interface UserRepository {

    Long save(User user) throws RepositoryException;

    int update(User user) throws RepositoryException;

    int remove(User user) throws RepositoryException;

    User get(Long id) throws RepositoryException;

    List<User> list() throws RepositoryException;

    User findByLogin(String login) throws RepositoryException;
}
