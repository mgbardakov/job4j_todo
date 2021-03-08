package ru.job4j.todo.store;

import java.io.Serializable;
import java.util.List;

public interface DAO <T, I extends Serializable> {
    T create(T model);
    T read(Class<T> type, I id);
    void update (T model);
    void delete (T model);
    List<T> findAll(final Class<T> type);
}
