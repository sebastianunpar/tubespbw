package com.example.tubespbw.film;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface FilmRepository {
    List<Film> getAll() throws SQLException;
    List<String> getFilmGenres(int filmId) throws SQLException;
    List<String> getFilmActors(int filmId) throws SQLException;
    Optional<FilmDetail> getFilmDetail(int filmId) throws SQLException;
    int getFilmSales(int filmId) throws SQLException;
    boolean insertActor(String actor);
    boolean insertGenre(String genre);
    List<Genre> getAllGenre() throws SQLException;
    List<Actor> getAllActor() throws SQLException;
    boolean insertFilm(String title, String description, byte[] poster, int stock, int price);
    boolean insertFilmGenre(int filmId, int genreId);
    boolean insertFilmActor(int filmId, int actorId);
    Optional<Integer> getLatestFilmId();
}
