package ru.job4j.todo.servlet;

import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.CategoryServiceImpl;
import ru.job4j.todo.service.JSONMessageParser;
import ru.job4j.todo.service.MessageParser;
import ru.job4j.todo.store.HbmCategoryDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class CategoryServlet extends HttpServlet {

    private final CategoryService service = new CategoryServiceImpl(HbmCategoryDAO.instOf());
    private final MessageParser parser = new JSONMessageParser();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(var writer = new PrintWriter(resp.getOutputStream(),
                true, StandardCharsets.UTF_8)) {
            var list = service.getCategoryList();
            var message = parser.stringifyList(list);
            writer.print(message);
            writer.flush();
        }
    }
}
