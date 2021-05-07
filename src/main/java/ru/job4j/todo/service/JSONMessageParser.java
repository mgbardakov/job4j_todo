package ru.job4j.todo.service;

import com.google.gson.Gson;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.List;

public class JSONMessageParser implements MessageParser {
    @Override
    public Item getItem(String message) {
        return new Gson().fromJson(message, Item.class);
    }

    @Override
    public <T> String stringifyList(List<T> list) {
        return new Gson().toJson(list);
    }

    @Override
    public User getUser(String message) {
        return new Gson().fromJson(message, User.class);
    }

    @Override
    public String stringifyUser(User user) {
        return new Gson().toJson(user);
    }
}
