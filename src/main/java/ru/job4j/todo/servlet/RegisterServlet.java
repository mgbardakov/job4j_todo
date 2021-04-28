package ru.job4j.todo.servlet;

import ru.job4j.todo.service.JSONMessageParser;
import ru.job4j.todo.service.MessageParser;
import ru.job4j.todo.service.UserService;
import ru.job4j.todo.service.UserServiceImpl;
import ru.job4j.todo.store.HbmUserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class RegisterServlet extends HttpServlet {

    private final MessageParser parser = new JSONMessageParser();
    private final UserService userService = new UserServiceImpl(HbmUserDAO.instOf());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(var reader = req.getReader();
            var writer = new PrintWriter(resp.getOutputStream(),
        true, StandardCharsets.UTF_8)) {
            var reqMessage = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            var user = parser.getUser(reqMessage);
            var success = userService.registerUser(user);
            if (!success) {
                resp.setStatus(404);
                writer.print("{\"message\": \"Проверьте правильность ввода данных\"}");
            } else {
                writer.print("{\"message\": \"Пользователь успешно зарегистрирован\" }");
            }
        }
    }
}
