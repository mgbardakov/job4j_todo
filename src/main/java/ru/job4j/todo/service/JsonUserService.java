package ru.job4j.todo.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.store.HbmItemDAO;
import ru.job4j.todo.store.ItemDAO;

import java.sql.Timestamp;
import java.util.Date;

public class JsonUserService implements UserService {

    private final ItemDAO dao;

    public JsonUserService(ItemDAO dao) {
        this.dao = dao;
    }

    @Override
    public void addNewTask(String message) {
        var jsonMessage = new Gson().fromJson(message, JsonObject.class);
        var item = new Item();
        item.setDescription(jsonMessage.get("description").getAsString());
        item.setCreated(new Timestamp(new Date().getTime()));
        item.setDone(false);
        dao.create(item);
    }

    @Override
    public void changeTaskStatus(String message) {
        var jsonMessage = new Gson().fromJson(message, JsonObject.class);
        var item = HbmItemDAO.instOf().read(Item.class, jsonMessage.get("id").getAsInt());
        item.setDone(!item.isDone());
        dao.update(item);
    }

    @Override
    public String getTasks(String message) {
        var jsonMessage = new Gson().fromJson(message, JsonObject.class);
        var all = jsonMessage.get("status").getAsBoolean();
        var rsl = "";
        if (all) {
            rsl = getAllTasks();
        } else {
            rsl = getUndoneTasks();
        }
        return rsl;
    }

    private String getAllTasks() {
        var list = dao.findAll(Item.class);
        return new Gson().toJson(list);
    }

    private String getUndoneTasks() {
        var list = dao.findUndoneItems();
        return new Gson().toJson(list);
    }

}
