package ru.job4j.todo.service;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.List;

public interface MessageParser {
    Item getItem(String message);
    <T> String stringifyList(List<T> list);
    User getUser(String message);
    String stringifyUser(User user);
}