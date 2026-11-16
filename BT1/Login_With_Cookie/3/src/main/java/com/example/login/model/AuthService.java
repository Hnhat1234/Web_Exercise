package com.example.login.model;

import java.sql.SQLException;

import com.example.login.dao.UserDao;

public class AuthService {

    private final UserDao userDao = new UserDao();

    public User authenticate(String username, String password) throws SQLException {
        if (username == null || password == null) {
            return null;
        }

        String trimmedUsername = username.trim();
        if (trimmedUsername.isEmpty() || password.isEmpty()) {
            return null;
        }

        return userDao.findByUsernameAndPassword(trimmedUsername, password);
    }
}
