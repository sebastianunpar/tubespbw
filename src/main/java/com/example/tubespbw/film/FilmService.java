package com.example.tubespbw.film;

import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class FilmService {
    @Autowired
    FilmRepository repo;

    public List<Film> getAllFilmUser() throws SQLException{
        List<Film> films = repo.getAll();
        for (Film film : films) {
            String base64Poseter = generateBase64Poster(film.getPoster());
            film.setBase64Poster(base64Poseter);
        }
        return films;
    }

    private String generateBase64Poster(byte[] poster) {
        if (poster != null && poster.length > 0) {
            return Base64.getEncoder().encodeToString(poster);
        }
        return null;
    }

    public Optional<FilmDetail> getFilmDetail(int filmId) throws SQLException {
        Optional<FilmDetail> filmDetail = repo.getFilmDetail(filmId);
        return filmDetail;
    }

    public int getFilmSales(int filmId) throws SQLException {
        return repo.getFilmSales(filmId);
    }

}
