package ru.job4j.todo.store;

import ru.job4j.todo.model.User;

public interface UserDAO extends DAO<User, Integer> {
    User getUserByCredentials(String login, String password);
    boolean isUserExists(User user);
}
