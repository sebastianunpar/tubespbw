package com.example.tubespbw.admin;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
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
                        f.title
                    FROM rentals_in_month rim
                    JOIN film f ON rim.filmId = f.filmId
                    ORDER BY rim.rental_count DESC, f.title ASC
                    LIMIT 1
                """;

        // Use `jdbcTemplate.queryForObject` to execute the query and retrieve the title
        return jdbcTemplate.queryForObject(sql, String.class, year, month);
    }

    @Override
    public String getBykDisewa() {
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
                        rim.rental_count
                    FROM rentals_in_month rim
                    JOIN film f ON rim.filmId = f.filmId
                    ORDER BY rim.rental_count DESC, f.title ASC
                    LIMIT 1
                """;

        // Use `jdbcTemplate.queryForObject` to execute the query and retrieve the title
        return jdbcTemplate.queryForObject(sql, String.class, year, month);
    }

    @Override
    public byte[] getMostRentedMoviePoster() {
        LocalDate currentDate = LocalDate.now();

        // Extract the current year and month
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
    public List<ReportData> getMonthlyReport() {
        String sql = """
                    SELECT
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

    @Override
    public List<ReportData> getReportByDateRange(String startDate, String endDate) {
        String sql = """
                    SELECT
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
                        rs.getString("email_penyewa"),
                        rs.getString("judul_film"),
                        rs.getDate("tanggal_peminjaman"),
                        rs.getDate("tanggal_pengembalian"),
                        rs.getBigDecimal("pemasukan"))));
    }

    private List<ReportData> formatReportData(List<ReportData> reports) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        currencyFormatter.setMaximumFractionDigits(0); // Ensure no decimals
        currencyFormatter.setMinimumFractionDigits(0);
        reports.forEach(report -> {
            String formattedIncome = currencyFormatter.format(report.getPemasukan());
            report.setFormattedPemasukan(formattedIncome);
        });
        return reports;
    }

    private List<ReportData> mapReportDatas(String sql) {
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ReportData row = new ReportData(
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