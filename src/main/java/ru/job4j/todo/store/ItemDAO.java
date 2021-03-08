package ru.job4j.todo.store;

import ru.job4j.todo.model.Item;

import java.util.List;

public interface ItemDAO extends DAO<Item, Integer> {
    List<Item> findUndoneItems();
}
