package com.example.tubespbw.film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FilmJdbcRepo implements FilmRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override //untuk page browse, cuma perlu id title poster
    public List<Film> getAll() throws SQLException {
        String sql = "SELECT filmId, title, poster FROM film";
        return jdbcTemplate.query(sql, this::mapRowToFilm);
    }
    private Film mapRowToFilm (ResultSet resultSet, int rowNum) throws SQLException {
        return new Film (
            resultSet.getInt("filmId"),
            resultSet.getString("title"),
            resultSet.getBytes("poster")
        );
    }

    @Override
    public List<String> getFilmGenres(int filmId) throws SQLException {
        String sql = "SELECT genre FROM filmGenre WHERE filmId = ?";
        return jdbcTemplate.query(sql, this::mapRowToGenre, filmId);
    }
    private String mapRowToGenre (ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getString("genre");
    }

    @Override
    public List<String> getFilmActors(int filmId) throws SQLException {
        String sql = "SELECT actor FROM filmActor WHERE filmId = ?";
        return jdbcTemplate.query(sql, this::mapRowToActor, filmId);
    }
    private String mapRowToActor (ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getString("actor");
    }

    @Override //untuk page per film, perlu semua detail
    public Optional<FilmDetail> getFilmDetail(int filmId) throws SQLException{
        String sql = "SELECT * FROM film WHERE filmId = ?";
        List<FilmDetail> films = jdbcTemplate.query(sql, this::mapRowToFilmDetail, filmId);
        if (films.isEmpty()) {
            return Optional.empty();
        }
        FilmDetail film = films.get(0);
        List<String> genres = getFilmGenres(filmId);
        List<String> actors = getFilmActors(filmId);
        film.setGenres(genres);
        film.setActors(actors);
        return Optional.of(film);
    }
    private FilmDetail mapRowToFilmDetail (ResultSet resultSet, int rowNum) throws SQLException {
        return new FilmDetail (
            resultSet.getInt("filmId"),
            resultSet.getString("title"),
            resultSet.getString("synopsis"),
            resultSet.getBytes("poster"),
            resultSet.getInt("stock"),
            resultSet.getDouble("price"),
            resultSet.getDouble("fine"),
            resultSet.getBoolean("valid"),
            null,
            null
        );
    }
}