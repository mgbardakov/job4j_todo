package ru.job4j.todo.service;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.store.CategoryDAO;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryDAO dao;

    public CategoryServiceImpl(CategoryDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Category> getCategoryList() {
        return dao.findAll(Category.class);
    }

    @Override
    public void addCategory(Category category) {
        dao.create(category);
    }
}
