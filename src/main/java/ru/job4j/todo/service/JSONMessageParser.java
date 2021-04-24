package ru.job4j.todo.service;

import com.google.gson.Gson;
import ru.job4j.todo.model.Item;

import java.util.List;

public class JSONMessageParser implements MessageParser {
    @Override
    public Item getItem(String message) {
        return new Gson().fromJson(message, Item.class);
    }

    @Override
    public String stringifyItemList(List<Item> list) {
        return new Gson().toJson(list);
    }
}
