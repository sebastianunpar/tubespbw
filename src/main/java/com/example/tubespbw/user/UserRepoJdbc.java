package com.example.tubespbw.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepoJdbc implements UserRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<User> findUser(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> results = jdbcTemplate.query(sql, this::mapRowToUser, email);
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }
    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
            // resultSet.getInt("userId"),
            resultSet.getString("name"),
            resultSet.getString("email"),
            resultSet.getString("password"),
            resultSet.getString("password"),
            resultSet.getString("role")
        );
    }
    public void save(User user) throws Exception{
        String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword(), user.getRole());
        } catch (Exception e) {
            System.err.println("Error while saving user: " + e.getMessage());
            throw new Exception("Duplicate email address", e);
        }
    }

    @Override
    public Optional<Integer> getUserIdFromEmail(String email) {
        String sql = "SELECT userId FROM users WHERE email = ?";
        List<Integer> results = jdbcTemplate.query(sql, this::mapRowToUserId, email);
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }
    private int mapRowToUserId(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getInt("userId");
    }
}
