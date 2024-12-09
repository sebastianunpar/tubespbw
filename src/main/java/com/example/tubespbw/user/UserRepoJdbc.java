package com.example.tubespbw.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepoJdbc implements UserRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<User> findUser(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        return jdbcTemplate.query(sql, this::mapRowToUser, email);
    }
    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
            resultSet.getInt("userId"),
            resultSet.getString("name"),
            resultSet.getString("email"),
            resultSet.getString("password"),
            resultSet.getString("role")
        );
    }
}
