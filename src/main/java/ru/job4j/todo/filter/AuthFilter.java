package ru.job4j.todo.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class AuthFilter implements Filter {

    private static final Set<String> GRANTED_ACCESS = Set.of("auth.js",
            "auth.html","auth.do", "reg.html", "reg.js", "reg.do");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest sreq, ServletResponse sresp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) sreq;
        HttpServletResponse resp = (HttpServletResponse) sresp;
        String uri = req.getRequestURI();
        if (GRANTED_ACCESS.stream().anyMatch(uri::endsWith)) {
            chain.doFilter(sreq, sresp);
            return;
        }
        if (req.getSession().getAttribute("currentUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth.html");
            return;
        }
        chain.doFilter(sreq, sresp);
    }

    @Override
    public void destroy() {
    }
}