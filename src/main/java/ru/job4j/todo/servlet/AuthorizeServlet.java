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

public class AuthorizeServlet extends HttpServlet {

    private final MessageParser parser = new JSONMessageParser();
    private final UserService userService = new UserServiceImpl(HbmUserDAO.instOf());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/index.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(var reader = req.getReader();
            var writer = new PrintWriter(resp.getOutputStream(),
                    true, StandardCharsets.UTF_8)) {
            var reqMessage = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            var reqUser = parser.getUser(reqMessage);
            var respUser = userService.getUser(reqUser);
            if (respUser != null) {
                var respMessage = parser.stringifyUser(respUser);
                writer.print(respMessage);
                req.getSession().setAttribute("currentUser", respUser);
            } else {
                writer.print("{\"errorMessage\":\"Неправильный логин или пароль\"}");
                writer.flush();
            }
        }

    }
}
