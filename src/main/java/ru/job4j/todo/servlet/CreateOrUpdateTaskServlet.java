package ru.job4j.todo.servlet;

import ru.job4j.todo.service.*;
import ru.job4j.todo.store.HbmItemDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class CreateOrUpdateTaskServlet extends HttpServlet {

    private final UserService service = new UserServiceImpl(HbmItemDAO.instOf());
    private final MessageParser parser = new JSONMessageParser();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(var reader = req.getReader()) {
            var reqMessage = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            var item = parser.getItem(reqMessage);
            if (item.getId() == 0) {
                service.addNewTask(item);
            } else {
                service.updateTask(item);
            }
        }
    }
}
