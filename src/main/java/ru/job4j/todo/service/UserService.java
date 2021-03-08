package ru.job4j.todo.service;


public interface UserService {
    void addNewTask(String message);
    void changeTaskStatus(String message);
    String getTasks(String message);
}
