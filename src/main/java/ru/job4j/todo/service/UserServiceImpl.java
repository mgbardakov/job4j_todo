package ru.job4j.todo.service;

import ru.job4j.todo.model.User;
import ru.job4j.todo.store.HbmUserDAO;
import ru.job4j.todo.store.UserDAO;

public class UserServiceImpl implements UserService {

    private final UserDAO dao;

    public UserServiceImpl(UserDAO dao) {
        this.dao = dao;
    }

    @Override
    public User getUser(User user) {
        return dao.getUserByCredentials(user.getLogin(), user.getPassword());
    }

    @Override
    public boolean registerUser(User user) {
        if (validateUser(user)) {
            dao.create(user);
            return true;
        }
        return false;
    }

    private boolean validateUser(User user) {
        return !dao.isUserExists(user) && user.getPassword().length() > 0;
    }
}
