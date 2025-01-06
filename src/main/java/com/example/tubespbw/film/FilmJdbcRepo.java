package com.example.tubespbw.film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.example.tubespbw.genre.Genre;
import com.example.tubespbw.actor.Actor;

@Repository
public class FilmJdbcRepo implements FilmRepository{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override //untuk page browse, cuma perlu id title poster
    public List<Film> getAll() throws SQLException {
        String sql = "SELECT filmId, title, stock, poster FROM film";
        return jdbcTemplate.query(sql, this::mapRowToFilm);
    }
    private Film mapRowToFilm (ResultSet resultSet, int rowNum) throws SQLException {
        return new Film (
            resultSet.getInt("filmId"),
            resultSet.getString("title"),
            resultSet.getInt("stock"),
            resultSet.getBytes("poster"),
            null
        );
    }

    @Override
    public List<String> getFilmGenres(int filmId) throws SQLException {
        String sql = "SELECT name FROM filmGenre JOIN genre on genre.genreId = filmGenre.genreId WHERE filmId = ?";
        return jdbcTemplate.query(sql, this::mapRowToGenre, filmId);
    }
    private String mapRowToGenre (ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getString("name");
    }

    @Override
    public List<String> getFilmActors(int filmId) throws SQLException {
        String sql = "SELECT name FROM filmActor JOIN actor on actor.actorId = filmActor.actorId WHERE filmId = ?";
        return jdbcTemplate.query(sql, this::mapRowToActor, filmId);
    }
    private String mapRowToActor (ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getString("name");
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
            resultSet.getBoolean("valid"),
            null,
            null,
            null
        );
    }

    public int getFilmSales(int filmId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM rental WHERE filmId = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{filmId}, Integer.class);
    }

    @Override
    public boolean insertGenre(String genre) {
        String sql = "INSERT INTO genre (name, valid) VALUES (?, 'true')";
        int rowsAffected = jdbcTemplate.update(sql, genre);
        System.out.println(rowsAffected);
        return rowsAffected > 0;
    }

    @Override
    public boolean insertActor(String actor) {
        String sql = "INSERT INTO actor (name, valid) VALUES (?, 'true')";
        try {
            int rowsAffected = jdbcTemplate.update(sql, actor);
            return rowsAffected > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Genre> getAllGenre() throws SQLException {
        String sql = "SELECT * FROM genre ORDER BY name";
        return jdbcTemplate.query(sql, this::mapRowToGenreObj);
    }
    private Genre mapRowToGenreObj(ResultSet resultSet, int rowNum) throws SQLException {
        return new Genre(
            resultSet.getInt("genreId"),
            resultSet.getString("name"),
            resultSet.getBoolean("valid")
        );
    }

    // Genre
    @Override
    public Genre getGenreById(int genreId) throws SQLException {
        String sql = "SELECT * FROM genre WHERE genreId = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToGenreObj, genreId);
        // return genre;
    }

    @Override
    public void updateGenre(int genreId, String genreName, boolean genreValid) throws SQLException {
        String sql = "UPDATE genre SET name = ?, valid = ? WHERE genreId = ?";
        jdbcTemplate.update(sql, genreName, genreValid, genreId);
    }   
    @Override
    public List<Genre> searchGenresByName(String genreName) {
        String sql = "SELECT * FROM genre WHERE LOWER(name) LIKE ?";
        return jdbcTemplate.query(sql, this::mapRowToGenreObj, "%"+genreName.toLowerCase()+"%");
    }

    @Override
    public void changeValidGenre(int genreId) {
        String sql = "SELECT valid FROM genre WHERE genreId = ?";
        boolean valid = jdbcTemplate.queryForObject(sql, Boolean.class, genreId);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(valid);
        boolean newValid = !valid;
        sql = "UPDATE genre SET valid = ? WHERE genreId = ?";
        jdbcTemplate.update(sql, newValid, genreId);
    }

    // Aktor
    @Override
    public Actor getActorById(int actorId) throws SQLException {
        String sql = "SELECT * FROM actor WHERE actorId = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToActorObj, actorId);
    }

    @Override
    public void updateActor(int actorId, String actorName, boolean actorValid) throws SQLException {
        String sql = "UPDATE actor SET name = ?, valid = ? WHERE actorId = ?";
        jdbcTemplate.update(sql, actorName, actorValid, actorId);
    }

    @Override
    public List<Actor> searchActorsByName(String actorName) {
        String sql = "SELECT * FROM actor WHERE LOWER(name) LIKE ?";
        return jdbcTemplate.query(sql, this::mapRowToActorObj, "%"+actorName.toLowerCase()+"%");
    }

    @Override
    public void changeValidActor(int actorId) {
        String sql = "SELECT valid FROM actor WHERE actorId = ?";
        boolean valid = jdbcTemplate.queryForObject(sql, Boolean.class, actorId);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(valid);
        boolean newValid = !valid;
        sql = "UPDATE actor SET valid = ? WHERE genreId = ?";
        jdbcTemplate.update(sql, newValid, actorId);
    }
    // 
    @Override
    public List<Actor> getAllActor() throws SQLException {
        String sql = "SELECT * FROM actor ORDER BY name";
        return jdbcTemplate.query(sql, this::mapRowToActorObj);
    }
    private Actor mapRowToActorObj(ResultSet resultSet, int rowNum) throws SQLException {
        return new Actor(
            resultSet.getInt("actorId"),
            resultSet.getString("name"),
            resultSet.getBoolean("valid")
        );
    }

    @Override
    public boolean insertFilm(String title, String description, byte[] poster, int stock, int price) {
        String sql = "INSERT INTO film (title, synopsis, poster, stock, price, valid) VALUES (?, ?, ?, ?, ?, 'true')";
        int rowsAffected = jdbcTemplate.update(sql, title, description, poster, stock, price);
        return rowsAffected > 0;
    }

    @Override
    public boolean insertFilmGenre(int filmId, int genreId) {
        String sql = "INSERT INTO filmGenre (filmId, genreId) VALUES (?, ?)";
        int rowsAffected = jdbcTemplate.update(sql, filmId, genreId);
        return rowsAffected > 0;
    }

    @Override
    public boolean insertFilmActor(int filmId, int actorId) {
        String sql = "INSERT INTO filmActor (filmId, actorId) VALUES (?, ?)";
        int rowsAffected = jdbcTemplate.update(sql, filmId, actorId);
        return rowsAffected > 0;
    }

    @Override
    public Optional<Integer> getLatestFilmId() {
        String sql = "SELECT filmId FROM film ORDER BY filmId DESC LIMIT 1";
        List<Integer> results = jdbcTemplate.query(sql, this::mapRowToFilmId);
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }
    private int mapRowToFilmId(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getInt("filmId");
    }
}
