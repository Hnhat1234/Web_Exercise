package com.example.login;

import java.io.IOException;
import java.sql.SQLException;

import com.example.login.model.AuthService;
import com.example.login.model.RememberMeCookie;
import com.example.login.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        User user;
        try {
            user = authenticateFromCookie(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error during authentication", e);
        }

        if (user != null) {
            request.getSession().setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user;
        try {
            user = authService.authenticate(username, password);
        } catch (SQLException e) {
            throw new ServletException("Database error during authentication", e);
        }

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/error");
            return;
        }

        boolean rememberMe = request.getParameter("rememberMe") != null;
        RememberMeCookie.write(request, response, username.trim(), password, rememberMe);

        request.getSession().setAttribute("user", user);
        response.sendRedirect(request.getContextPath() + "/home");
    }

    private User authenticateFromCookie(HttpServletRequest request, HttpServletResponse response)
            throws SQLException {
        RememberMeCookie.Credentials credentials = RememberMeCookie.read(request);
        if (credentials == null) {
            return null;
        }

        User user = authService.authenticate(credentials.username(), credentials.password());
        if (user == null) {
            RememberMeCookie.clear(request, response);
        }
        return user;
    }
}
