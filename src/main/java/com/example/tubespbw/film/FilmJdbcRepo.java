package com.example.tubespbw.film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
        String sql = "SELECT * FROM genre ORDER BY valid DESC, name ASC";
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
        sql = "UPDATE actor SET valid = ? WHERE actorId = ?";
        jdbcTemplate.update(sql, newValid, actorId);
    }
    // 
    @Override
    public List<Actor> getAllActor() throws SQLException {
        String sql = "SELECT * FROM actor ORDER BY valid DESC, name ASC";
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
    public void updateFilm(String title, String description, byte[] poster, int stock, int price, int filmId) throws SQLException {
        String sql = """
            UPDATE film 
            SET title = ?, synopsis = ?, poster = ?, stock = ?, price = ? 
            WHERE filmId = ?
        """;
        jdbcTemplate.update(sql, title, description, poster, stock, price, filmId);

        jdbcTemplate.update("DELETE FROM filmGenre WHERE filmId = ?", filmId);
        jdbcTemplate.update("DELETE FROM filmActor WHERE filmId = ?", filmId);    
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
    @Override
    public List<Film> searchFilms(String movieName) {
        String sql = "SELECT filmId, title, stock, poster FROM film WHERE title ILIKE ?";
        String likeQuery = "%" + movieName + "%";
        return jdbcTemplate.query(sql, this::mapRowToFilm, likeQuery);
    }
   @Override
public List<Film> filterFilmsByActorAndGenre(List<String> actorNames, List<String> genreNames, String movieName)
        throws SQLException {
    // Filter out empty actor names
    actorNames = actorNames.stream()
                           .filter(name -> name != null && !name.trim().isEmpty())
                           .collect(Collectors.toList());

    String sql = """
        SELECT DISTINCT film.filmId, film.title, film.stock, film.poster 
        FROM film
        INNER JOIN filmActor ON filmActor.filmId = film.filmId
        INNER JOIN actor ON actor.actorId = filmActor.actorId
        INNER JOIN filmGenre ON filmGenre.filmId = film.filmId
        INNER JOIN genre ON genre.genreId = filmGenre.genreId
    """;

    // Kondisi filter
    StringBuilder whereClause = new StringBuilder();
    Map<String, Object> parameters = new HashMap<>();

    // Actor filter
    if (actorNames != null && !actorNames.isEmpty()) {
        whereClause.append("actor.name IN (:actorNames) ");
        parameters.put("actorNames", actorNames);
    }

    // Genre filter
    if (genreNames != null && !genreNames.isEmpty()) {
        if (whereClause.length() > 0) {
            whereClause.append("AND ");
        }
        whereClause.append("genre.name IN (:genreNames) ");
        parameters.put("genreNames", genreNames);
    }

    // Movie name filter
    if (movieName != null && !movieName.isEmpty()) {
        if (whereClause.length() > 0) {
            whereClause.append("AND ");
        }
        whereClause.append("film.title ILIKE :movieName ");
        parameters.put("movieName", "%" + movieName + "%");
    }

    // Apply where conditions
    if (whereClause.length() > 0) {
        sql += "WHERE " + whereClause.toString();
    }

    // If multiple actors are selected, ensure that all specified actors are in the same movie
    if (actorNames != null && actorNames.size() > 1) {
        // Group by filmId and ensure the count of distinct actors matches the number of actors
        sql += " GROUP BY film.filmId, film.title, film.stock, film.poster";
        sql += " HAVING COUNT(DISTINCT actor.name) = :actorCount";
        parameters.put("actorCount", actorNames.size());
    }

    // Debugging outputs
    System.out.println(sql);
    System.out.println(parameters);

    // Use NamedParameterJdbcTemplate for parameter binding
    NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
    return namedParameterJdbcTemplate.query(sql, parameters, this::mapRowToFilm);
}
    

    @Override
    public int getFilmCount() {
        String sql = "SELECT COUNT(*) FROM film";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public int getFilmCountByName(String movieName) {
        String sql = "SELECT COUNT(*) FROM film WHERE title ILIKE ?";
        String likeQuery = "%" + movieName + "%";
        return jdbcTemplate.queryForObject(sql, Integer.class, likeQuery);
    }
    @Override
    public int getFilmCountByActorAndGenre(List<String> actorNames, List<String> genreNames, String movieName) {
        String sql = """
            SELECT COUNT(DISTINCT film.filmId)
            FROM film
            LEFT JOIN filmActor ON filmActor.filmId = film.filmId
            LEFT JOIN filmGenre ON filmGenre.filmId = film.filmId
            LEFT JOIN actor ON actor.actorId = filmActor.actorId
            LEFT JOIN genre ON genre.genreId = filmGenre.genreId
        """;

        StringBuilder whereClause = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        if (actorNames != null && !actorNames.isEmpty()) {
            whereClause.append("actor.name IN (:actorNames) ");
            parameters.put("actorNames", actorNames);
        }

        if (genreNames != null && !genreNames.isEmpty()) {
            if (whereClause.length() > 0) {
                whereClause.append("AND ");
            }
            whereClause.append("genre.name IN (:genreNames) ");
            parameters.put("genreNames", genreNames);
        }

        if (movieName != null && !movieName.isEmpty()) {
            if (whereClause.length() > 0) {
                whereClause.append("AND ");
            }
            whereClause.append("film.title ILIKE :movieName ");
            parameters.put("movieName", "%" + movieName + "%");
        }

        if (whereClause.length() > 0) {
            sql += "WHERE " + whereClause.toString();
        }

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
    }

    @Override
    public boolean removeFilmStock(int filmId) {
        String sql = "UPDATE film SET stock = stock - 1 WHERE filmId = ? AND stock > 0";
        int rowsAffected = jdbcTemplate.update(sql, filmId);
        return rowsAffected > 0;
    }

    @Override
    public boolean addFilmStock(int filmId) {
        String sql = "UPDATE film SET stock = stock + 1 WHERE filmId = ?";
        int rowsAffected = jdbcTemplate.update(sql, filmId);
        return rowsAffected > 0;
    }

    @Override
    public List<Film> getTopFilms(int n) {
        String sql = "SELECT filmId, title, stock, poster FROM (SELECT film.filmId, title, stock, poster, COUNT(rental.filmId) AS count FROM film JOIN rental ON film.filmId = rental.filmId GROUP BY film.filmId) AS ranked_films ORDER BY count DESC LIMIT ?";
        return jdbcTemplate.query(sql, this::mapRowToFilm, n);
    }

    @Override
    public List<Film> getFilmTerlaris() {
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
                        f.filmId,
                        f.title,
                        f.stock,
                        f.poster
                    FROM rentals_in_month rim
                    LEFT JOIN film f ON rim.filmId = f.filmId
                    ORDER BY rim.rental_count DESC, f.title ASC
                    LIMIT 1
                """;

        try {
            return jdbcTemplate.query(sql, this::mapRowToFilm, year, month);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Integer> getFilmIdByRentalId(int rentalId) {
        String sql = "SELECT filmId FROM rental WHERE rentalId = ?";
        return jdbcTemplate.query(sql, this::mapRowToFilmId, rentalId);
    }
}
