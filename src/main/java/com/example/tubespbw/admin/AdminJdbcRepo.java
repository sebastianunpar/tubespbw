package com.example.tubespbw.admin;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminJdbcRepo implements AdminRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String getTotalIncome() {
        String sql = "SELECT 'Rp ' || TO_CHAR(SUM(f.price), 'FM999G999G999') AS total_income FROM rental r JOIN film f ON r.filmId = f.filmId";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    @Override
    public String getMonthlyIncome() {
        String sql = "SELECT 'Rp ' || TO_CHAR(SUM(f.price), 'FM999G999G999') AS total_income " +
                "FROM rental r " +
                "JOIN film f ON r.filmId = f.filmId " +
                "WHERE EXTRACT(MONTH FROM r.rentalDate) = EXTRACT(MONTH FROM CURRENT_DATE) " +
                "AND EXTRACT(YEAR FROM r.rentalDate) = EXTRACT(YEAR FROM CURRENT_DATE)";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    @Override
    public String getTitleTerlaris() {
        LocalDate currentDate = LocalDate.now();

        // Extract the current year and month as integers
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();

        String sql = """
                    WITH rentals_in_month AS (
                        SELECT
                            r.filmId,
                            COUNT(r.filmId) AS rental_count
                        FROM rental r
                        WHERE EXTRACT(YEAR FROM r.rentalDate) = ? AND EXTRACT(MONTH FROM r.rentalDate) = ?
                        GROUP BY r.filmId
                    )
                    SELECT
                        COALESCE(f.title, 'No rentals this month') AS title
                    FROM rentals_in_month rim
                    LEFT JOIN film f ON rim.filmId = f.filmId
                    ORDER BY rim.rental_count DESC, f.title ASC
                    LIMIT 1
                """;
        try {
            return jdbcTemplate.queryForObject(sql, String.class, year, month);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public String getMostPopularGenre() {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();

        String sql = """
                WITH genre_count AS (
                    SELECT
                        g.name,
                        COUNT(r.rentalId) AS rental_count
                    FROM rental r
                    JOIN film f ON r.filmId = f.filmId
                    JOIN filmGenre fg ON f.filmId = fg.filmId
                    JOIN genre g ON fg.genreId = g.genreId
                    WHERE EXTRACT(YEAR FROM r.rentalDate) = ? 
                    AND EXTRACT(MONTH FROM r.rentalDate) = ?
                    GROUP BY g.name
                )
                SELECT
                    COALESCE(g.name, 'No rentals this month') AS genre
                FROM genre_count g
                ORDER BY g.rental_count DESC, g.name ASC
                LIMIT 1
                """;

        try {
            return jdbcTemplate.queryForObject(sql, String.class, year, month);
        } catch (EmptyResultDataAccessException e) {
            return "No rentals this month";
        }
    }

    @Override
    public String getMostPopularActor() {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();

        String sql = """
                WITH actor_count AS (
                    SELECT
                        a.name,
                        COUNT(r.rentalId) AS rental_count
                    FROM rental r
                    JOIN film f ON r.filmId = f.filmId
                    JOIN filmActor fa ON f.filmId = fa.filmId
                    JOIN actor a ON fa.actorId = a.actorId
                    WHERE EXTRACT(YEAR FROM r.rentalDate) = ?
                    AND EXTRACT(MONTH FROM r.rentalDate) = ?
                    GROUP BY a.name
                )
                SELECT
                    COALESCE(a.name, 'No rentals this month') AS actor
                FROM actor_count a
                ORDER BY a.rental_count DESC, a.name ASC
                LIMIT 1
                """;

        try {
            return jdbcTemplate.queryForObject(sql, String.class, year, month);
        } catch (EmptyResultDataAccessException e) {
            return "No rentals this month";
        }
    }

    @Override
    public String getBykDisewa() {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();

        String sql = """
                    WITH rentals_in_month AS (
                        SELECT
                            r.filmId,
                            COUNT(r.filmId) AS rental_count
                        FROM rental r
                        WHERE EXTRACT(YEAR FROM r.rentalDate) = ? AND EXTRACT(MONTH FROM r.rentalDate) = ?
                        GROUP BY r.filmId
                    )
                    SELECT
                        rim.rental_count
                    FROM rentals_in_month rim
                    JOIN film f ON rim.filmId = f.filmId
                    ORDER BY rim.rental_count DESC, f.title ASC
                    LIMIT 1
                """;
        try {
            return jdbcTemplate.queryForObject(sql, String.class, year, month);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public byte[] getMostRentedMoviePoster() {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();

        String sql = """
                    WITH rentals_in_month AS (
                        SELECT
                            r.filmId,
                            COUNT(r.filmId) AS rental_count
                        FROM rental r
                        WHERE EXTRACT(YEAR FROM r.rentalDate) = ? AND EXTRACT(MONTH FROM r.rentalDate) = ?
                        GROUP BY r.filmId
                    )
                    SELECT
                        f.poster
                    FROM rentals_in_month rim
                    JOIN film f ON rim.filmId = f.filmId
                    ORDER BY rim.rental_count DESC
                    LIMIT 1
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getBytes("poster"), year, month);
    }

    @Override
    public List<ReportData> getOngoingRentals() {
        String sql = """
                    SELECT
                        r.rentalId, -- Add this field
                        u.email AS email_penyewa,
                        f.title AS judul_film,
                        r.rentalDate AS tanggal_peminjaman,
                        r.returnDate AS tanggal_pengembalian,
                        f.price AS pemasukan
                    FROM rental r
                    JOIN users u ON r.userId = u.userId
                    JOIN film f ON r.filmId = f.filmId
                    WHERE r.returnDate IS NULL
                """;
        return formatReportData(mapReportDatas(sql));
    }

    @Override
    public List<ReportData> getMonthlyReport() {
        String sql = """
                    SELECT
                        r.rentalId,
                        u.email AS email_penyewa,
                        f.title AS judul_film,
                        r.rentalDate AS tanggal_peminjaman,
                        r.returnDate AS tanggal_pengembalian,
                        f.price AS pemasukan
                    FROM rental r
                    JOIN users u ON r.userId = u.userId
                    JOIN film f ON r.filmId = f.filmId
                    WHERE DATE_TRUNC('month', r.rentalDate) = DATE_TRUNC('month', CURRENT_DATE)
                """;
        return formatReportData(mapReportDatas(sql));
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<ReportData> getReportByDateRange(String startDate, String endDate) {
        String sql = """
                    SELECT
                        r.rentalId,
                        u.email AS email_penyewa,
                        f.title AS judul_film,
                        r.rentalDate AS tanggal_peminjaman,
                        r.returnDate AS tanggal_pengembalian,
                        f.price AS pemasukan
                    FROM rental r
                    JOIN users u ON r.userId = u.userId
                    JOIN film f ON r.filmId = f.filmId
                    WHERE r.rentalDate BETWEEN ?::date AND ?::date
                """;

        return formatReportData(jdbcTemplate.query(
                sql,
                new Object[] { startDate, endDate },
                (rs, rowNum) -> new ReportData(
                        rs.getInt("rentalId"),
                        rs.getString("email_penyewa"),
                        rs.getString("judul_film"),
                        rs.getDate("tanggal_peminjaman"),
                        rs.getDate("tanggal_pengembalian"),
                        rs.getBigDecimal("pemasukan"))));
    }

    @Override
    public int getRentalCountByDateRange(String startDate, String endDate) {
        String sql = """
                    SELECT COUNT(*) 
                    FROM rental r
                    WHERE r.rentalDate BETWEEN ?::date AND ?::date
                """;
        return jdbcTemplate.queryForObject(sql, Integer.class, startDate, endDate);
    }

    @Override
    public String getTotalPriceByDateRange(String startDate, String endDate) {
        String sql = """
                    SELECT SUM(f.price) 
                    FROM rental r
                    JOIN film f ON r.filmId = f.filmId
                    WHERE r.rentalDate BETWEEN ?::date AND ?::date
                """;
        Double totalPrice = jdbcTemplate.queryForObject(sql, Double.class, startDate, endDate);
        
        if (totalPrice == null) {
            return "Rp 0";
        }

        DecimalFormat rupiahFormat = new DecimalFormat("Rp #,##0.00");
        return rupiahFormat.format(totalPrice);
    }

    @Override
    public String getMostPopularFilmTitleByDateRange(String startDate, String endDate) {
        String sql = """
                    WITH rentals_in_date_range AS (
                        SELECT
                            r.filmId,
                            COUNT(r.filmId) AS rental_count
                        FROM rental r
                        WHERE r.rentalDate BETWEEN ?::date AND ?::date
                        GROUP BY r.filmId
                    )
                    SELECT
                        COALESCE(f.title, 'No rentals in this period') AS title
                    FROM rentals_in_date_range rim
                    LEFT JOIN film f ON rim.filmId = f.filmId
                    ORDER BY rim.rental_count DESC, f.title ASC
                    LIMIT 1
                """;
        try {
            return jdbcTemplate.queryForObject(sql, String.class, startDate, endDate);
        } catch (EmptyResultDataAccessException e) {
            return "No rentals in this period";
        }
    }

    @Override
    public String getMostPopularGenreByDateRange(String startDate, String endDate) {
        String sql = """
                    WITH genre_count AS (
                        SELECT
                            g.name,
                            COUNT(r.rentalId) AS rental_count
                        FROM rental r
                        JOIN film f ON r.filmId = f.filmId
                        JOIN filmGenre fg ON f.filmId = fg.filmId
                        JOIN genre g ON fg.genreId = g.genreId
                        WHERE r.rentalDate BETWEEN ?::date AND ?::date
                        GROUP BY g.name
                    )
                    SELECT
                        COALESCE(g.name, 'No rentals in this period') AS genre
                    FROM genre_count g
                    ORDER BY g.rental_count DESC, g.name ASC
                    LIMIT 1
                """;
        try {
            return jdbcTemplate.queryForObject(sql, String.class, startDate, endDate);
        } catch (EmptyResultDataAccessException e) {
            return "No rentals in this period"; // Default message if no results
        }
    }

    @Override
    public String getMostPopularActorByDateRange(String startDate, String endDate) {
        String sql = """
                    WITH actor_count AS (
                        SELECT
                            a.name,
                            COUNT(r.rentalId) AS rental_count
                        FROM rental r
                        JOIN film f ON r.filmId = f.filmId
                        JOIN filmActor fa ON f.filmId = fa.filmId
                        JOIN actor a ON fa.actorId = a.actorId
                        WHERE r.rentalDate BETWEEN ?::date AND ?::date
                        GROUP BY a.name
                    )
                    SELECT
                        COALESCE(a.name, 'No rentals in this period') AS actor
                    FROM actor_count a
                    ORDER BY a.rental_count DESC, a.name ASC
                    LIMIT 1
                """;
        try {
            return jdbcTemplate.queryForObject(sql, String.class, startDate, endDate);
        } catch (EmptyResultDataAccessException e) {
            return "No rentals in this period";
        }
    }

    private List<ReportData> formatReportData(List<ReportData> reports) {
        @SuppressWarnings("deprecation")
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        currencyFormatter.setMaximumFractionDigits(0); // Ensure no decimals
        currencyFormatter.setMinimumFractionDigits(0);
        reports.forEach(report -> {
            String formattedIncome = currencyFormatter.format(report.getPemasukan());
            report.setFormattedPemasukan(formattedIncome);
        });
        return reports;
    }

    @Override
    public void updateReturnDate(int rentalId, LocalDate returnDate) {
        String sql = "UPDATE rental SET returnDate = ? WHERE rentalId = ?";
        int rowsUpdated = jdbcTemplate.update(sql, returnDate, rentalId);

        if (rowsUpdated == 0) {
            throw new IllegalArgumentException(
                    "Rental with ID " + rentalId + " does not exist or is already completed.");
        }
    }

    private List<ReportData> mapReportDatas(String sql) {
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ReportData row = new ReportData(
                    rs.getInt("rentalId"),
                    rs.getString("email_penyewa"),
                    rs.getString("judul_film"),
                    rs.getDate("tanggal_peminjaman"),
                    rs.getDate("tanggal_pengembalian"),
                    rs.getBigDecimal("pemasukan"));
            row.setEmailPenyewa(rs.getString("email_penyewa"));
            row.setJudulFilm(rs.getString("judul_film"));
            row.setTanggalPeminjaman(rs.getDate("tanggal_peminjaman"));
            row.setTanggalPengembalian(rs.getDate("tanggal_pengembalian") != null
                    ? rs.getDate("tanggal_pengembalian")
                    : null);
            row.setPemasukan(rs.getBigDecimal("pemasukan"));

            return row;
        });
    }

}