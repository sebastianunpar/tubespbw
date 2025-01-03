package com.example.tubespbw.rental;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RentalRepoJdbc implements RentalRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Rental> getUserRentals(int userId) {
        String sql = "SELECT rentalId, rentalDate, dueDate, returnDate, rental.filmId, film.title, userId, metodePembayaran, noPembayaran FROM rental JOIN film on film.filmId = rental.filmId WHERE userId = ? AND returnDate IS NULL";
        return jdbcTemplate.query(sql, this::mapRowToRental, userId);
    }
    private Rental mapRowToRental(ResultSet resultSet, int rowNum) throws SQLException {
        return new Rental(
            resultSet.getInt("rentalId"),
            resultSet.getDate("rentalDate").toLocalDate(),
            resultSet.getDate("dueDate").toLocalDate(),
            resultSet.getDate("returnDate").toLocalDate(),
            resultSet.getInt("filmId"),
            resultSet.getString("title"),
            resultSet.getInt("userId"),
            resultSet.getString("metodePembayaran"),
            resultSet.getString("noPembayaran")
        );
    }

    @Override
    public List<Rental> getUserRentalHistory(int userId) {
        String sql = "SELECT rentalId, rentalDate, dueDate, returnDate, rental.filmId, film.title, userId, metodePembayaran, noPembayaran FROM rental JOIN film on film.filmId = rental.filmId WHERE userId = ? AND returnDate IS NOT NULL";
        return jdbcTemplate.query(sql, this::mapRowToRental, userId);
    }
}
