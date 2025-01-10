package com.example.tubespbw.rental;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
        String sql = "SELECT rentalId, rentalDate,  returnDate, rental.filmId, film.title, userId, metodePembayaran FROM rental JOIN film on film.filmId = rental.filmId WHERE userId = ? AND returnDate IS NULL";
        return jdbcTemplate.query(sql, this::mapRowToRental, userId);
    }

    private Rental mapRowToRental(ResultSet resultSet, int rowNum) throws SQLException {
        return new Rental(
            resultSet.getInt("rentalId"),
            resultSet.getDate("rentalDate") != null ? resultSet.getDate("rentalDate").toLocalDate() : null,
            resultSet.getDate("returnDate") != null ? resultSet.getDate("returnDate").toLocalDate() : null,
            resultSet.getInt("filmId"),
            resultSet.getString("title"),
            resultSet.getInt("userId"),
            resultSet.getString("metodePembayaran")
        );
    }

    @Override
    public List<Rental> getUserRentalHistory(int userId) {
        String sql = "SELECT rentalId, rentalDate, returnDate, rental.filmId, film.title, userId, metodePembayaran FROM rental JOIN film on film.filmId = rental.filmId WHERE userId = ? AND returnDate IS NOT NULL";
        return jdbcTemplate.query(sql, this::mapRowToRental, userId);
    }

    @Override
    public boolean insertRental(LocalDate rentalDate, int filmId, int userId, String metodePembayaran) {
        String sql = "INSERT INTO rental (rentalDate, filmId, userId, metodePembayaran) VALUES (?, ?, ?, ?)";
        int rowAffected = jdbcTemplate.update(sql, rentalDate, filmId, userId, metodePembayaran);
        return rowAffected > 0;
    }

    @Override
    public List<Integer> getRentalsPerMonth(int year) {
        String sql = "WITH months AS (SELECT generate_series(1, 12) AS month) SELECT COALESCE(COUNT(r.rentalId), 0) AS rentalCount FROM months m LEFT JOIN rental r ON EXTRACT(MONTH FROM r.rentalDate) = m.month AND EXTRACT(YEAR FROM r.rentalDate) = ? GROUP BY m.month ORDER BY m.month;";
        return jdbcTemplate.query(sql, this::mapRowToRentalCount, year);
    }
    private Integer mapRowToRentalCount(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getInt("rentalCount");
    }

    @Override
    public List<Double> getIncomePerMonth(int year) {
        String sql = "WITH months AS (SELECT generate_series(1, 12) AS month) SELECT COALESCE(SUM(f.price), 0) AS income FROM months m LEFT JOIN rental r ON EXTRACT(MONTH FROM r.rentalDate) = m.month AND EXTRACT(YEAR FROM r.rentalDate) = ? LEFT JOIN film f ON r.filmId = f.filmId GROUP BY m.month ORDER BY m.month;";
        return jdbcTemplate.query(sql, this::mapRowToIncome, year);
    }
    private double mapRowToIncome(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getDouble("income");
    }

    @Override
    public List<Integer> getRentalYears() {
        String sql = "SELECT DISTINCT EXTRACT(YEAR FROM rentalDate) AS year FROM rental ORDER BY year";
        return jdbcTemplate.query(sql, this::mapRowToYear);
    }
    private Integer mapRowToYear(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getInt("year");
    }
}
