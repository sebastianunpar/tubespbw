package com.example.tubespbw.film;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class FilmService {
    @Autowired
    FilmRepository repo;

    public List<Film> getAllFilmUser() throws SQLException{
        return repo.getAll();
    }

    public Optional<FilmDetail> getFilmDetail(int filmId) throws SQLException {
        Optional<FilmDetail> filmDetail = repo.getFilmDetail(filmId);
        return filmDetail;
    }

}
