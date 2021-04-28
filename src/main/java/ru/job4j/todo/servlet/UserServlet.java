package ru.job4j.todo.servlet;

import ru.job4j.todo.model.User;
import ru.job4j.todo.service.JSONMessageParser;
import ru.job4j.todo.service.MessageParser;
import ru.job4j.todo.service.UserService;
import ru.job4j.todo.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class UserServlet extends HttpServlet {

    private final MessageParser parser = new JSONMessageParser();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try(var writer = new PrintWriter(resp.getOutputStream(),
        true, StandardCharsets.UTF_8)) {
            var token = (String) req.getSession().getAttribute("userToken");
            User user = (User) req.getSession().getAttribute("currentUser");
            if (user == null) {
                resp.sendRedirect("/auth.html");
                return;
            }
            String responseMessage = parser.stringifyUser(user);
            writer.print(responseMessage);
        }
    }
}
