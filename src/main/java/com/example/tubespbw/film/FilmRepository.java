package com.example.tubespbw.film;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface FilmRepository {
    List<Film> getAll() throws SQLException;
    List<String> getFilmGenres(int filmId) throws SQLException;
    List<String> getFilmActors(int filmId) throws SQLException;
    Optional<FilmDetail> getFilmDetail(int filmId) throws SQLException;
    
}
