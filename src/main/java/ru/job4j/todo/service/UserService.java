package ru.job4j.todo.service;

import ru.job4j.todo.model.User;

public interface UserService {
    User getUser(User user);
    boolean registerUser(User user);
}
