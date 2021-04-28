package ru.job4j.todo.service;

import ru.job4j.todo.model.Item;
import ru.job4j.todo.store.ItemDAO;

import java.util.List;

public class TaskServiceImpl implements TaskService {

    private final ItemDAO dao;

    public TaskServiceImpl(ItemDAO dao) {
        this.dao = dao;
    }

    @Override
    public void addNewTask(Item item) {
        dao.create(item);
    }

    @Override
    public void updateTask(Item item) {
        dao.update(item);
    }

    @Override
    public List<Item> getTasks(boolean all) {
        return all ? getAllTasks() : getUndoneTasks();
    }

    private List<Item> getAllTasks() {
        return dao.findAll(Item.class);
    }

    private List<Item> getUndoneTasks() {
        return dao.findUndoneItems();
    }
}
