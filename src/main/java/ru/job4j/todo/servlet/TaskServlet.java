package ru.job4j.todo.servlet;

import ru.job4j.todo.service.JsonUserService;
import ru.job4j.todo.service.UserService;
import ru.job4j.todo.store.HbmItemDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class TaskServlet extends HttpServlet {
    private static final UserService service = new JsonUserService(HbmItemDAO.instOf());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = new PrintWriter(resp.getOutputStream(),
                true, StandardCharsets.UTF_8);
             var reader = req.getReader()
        ) {
            var reqMessage = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            var responseMessage = service.getTasks(reqMessage);
            writer.print(responseMessage);
            writer.flush();
        }
    }
}
