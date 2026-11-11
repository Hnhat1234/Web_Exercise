package com.example.login.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.login.model.Database;
import com.example.login.model.User;

public class UserDao {

    private static final String FIND_USER_SQL =
            "SELECT name, password FROM `user` WHERE name = ? AND password = ?";

    public User findByUsernameAndPassword(String username, String password) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_SQL)) {

            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(resultSet.getString("name"));
                }
                return null;
            }
        }
    }
}
