package ru.job4j.todo.service;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.store.*;

import java.util.List;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {

    private final ItemDAO itemDAO = HbmItemDAO.instOf();
    private final CategoryDAO categoryDAO = HbmCategoryDAO.instOf();

    @Override
    public void addNewTask(Item item) {
        var preparedItem = prepareItem(item);
        itemDAO.create(preparedItem);
    }

    @Override
    public void updateTask(Item item) {
        var preparedItem = prepareItem(item);
        itemDAO.update(preparedItem);
    }

    @Override
    public List<Item> getTasks(boolean all) {
        return all ? getAllTasks() : getUndoneTasks();
    }

    private List<Item> getAllTasks() {
        return itemDAO.findAll(Item.class);
    }

    private List<Item> getUndoneTasks() {
        return itemDAO.findUndoneItems();
    }

    private Item prepareItem(Item item) {
        item.setCategories(item.getCategories().stream().map(x ->
            categoryDAO.read(Category.class, x.getId())).collect(Collectors.toList()));
        return item;
    }
}
