package ru.job4j.todo.servlet;

import ru.job4j.todo.service.*;
import ru.job4j.todo.store.HbmItemDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class TaskServlet extends HttpServlet {
    private static final UserService service = new UserServiceImpl(HbmItemDAO.instOf());
    private final MessageParser parser = new JSONMessageParser();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = new PrintWriter(resp.getOutputStream(),
                true, StandardCharsets.UTF_8)) {
            boolean all = Boolean.parseBoolean(req.getParameter("all"));
            var items = service.getTasks(all);
            var responseMessage = parser.stringifyItemList(items);
            writer.print(responseMessage);
            writer.flush();
        }
    }
}
