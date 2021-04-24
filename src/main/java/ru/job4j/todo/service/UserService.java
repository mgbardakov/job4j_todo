package ru.job4j.todo.service;


import ru.job4j.todo.model.Item;

import java.util.List;

public interface UserService {
    void addNewTask(Item item);
    void updateTask(Item item);
    List<Item> getTasks(boolean all);
}
