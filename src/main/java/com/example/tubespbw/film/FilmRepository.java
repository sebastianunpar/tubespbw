package com.example.tubespbw.film;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.example.tubespbw.genre.Genre;
import com.example.tubespbw.actor.Actor;

public interface FilmRepository {
    List<Film> getAll() throws SQLException;
    List<String> getFilmGenres(int filmId) throws SQLException;
    List<String> getFilmActors(int filmId) throws SQLException;
    Optional<FilmDetail> getFilmDetail(int filmId) throws SQLException;
    int getFilmSales(int filmId) throws SQLException;
    boolean insertActor(String actor);
    boolean insertGenre(String genre);
    List<Genre> getAllGenre() throws SQLException;
    // Genre
    Genre getGenreById(int id) throws SQLException;
    void updateGenre(int genreId, String genreName, boolean genreValid) throws SQLException;
    List<Genre> searchGenresByName(String name);
    void changeValidGenre(int genreId);
    // Aktor
    Actor getActorById(int id) throws SQLException;
    void updateActor(int actorId, String actorName, boolean actorValid) throws SQLException;
    List<Actor> searchActorsByName(String name);
    void changeValidActor(int actorId);

    List<Actor> getAllActor() throws SQLException;
    boolean insertFilm(String title, String description, byte[] poster, int stock, int price);
    void updateFilm(String title, String description, byte[] poster, int stock, int price, int filmId) throws SQLException;
    boolean insertFilmGenre(int filmId, int genreId);
    boolean insertFilmActor(int filmId, int actorId);
    Optional<Integer> getLatestFilmId();
    List<Film> searchFilms(String movieName);
    List<Film> filterFilmsByActorAndGenre(List<String> actorNames, List<String> genreNames, String movieName) throws SQLException;
    int getFilmCount();
    int getFilmCountByName(String movieName);
    int getFilmCountByActorAndGenre(List<String> actorNames, List<String> genreNames, String movieName);
    boolean removeFilmStock(int filmId);
    boolean addFilmStock(int filmId);
    List<Film> getTopFilms(int n);
    List<Film> getFilmTerlaris();
    List<Integer> getFilmIdByRentalId(int rentalId);
}
