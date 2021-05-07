package ru.job4j.todo.store;

import ru.job4j.todo.model.Category;

public class HbmCategoryDAO extends HbmDao<Category, Integer> implements CategoryDAO {
    private HbmCategoryDAO() {
    }

    private static final class Lazy {
        private static final CategoryDAO INST = new HbmCategoryDAO();
    }

    public static CategoryDAO instOf() {
        return HbmCategoryDAO.Lazy.INST;
    }
}
