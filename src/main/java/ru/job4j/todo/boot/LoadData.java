package ru.job4j.todo.boot;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.CategoryServiceImpl;
import ru.job4j.todo.store.HbmCategoryDAO;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class LoadData implements ServletContextListener {

    CategoryService service = new CategoryServiceImpl(HbmCategoryDAO.instOf());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (service.getCategoryList().size() == 0) {
            service.addCategory(new Category("дом"));
            service.addCategory(new Category("работа"));
            service.addCategory(new Category("учёба"));
            service.addCategory(new Category("отдых"));
        }
    }
}
