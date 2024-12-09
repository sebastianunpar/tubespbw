package com.example.tubespbw.film;

import java.util.List;
import java.util.Optional;

public interface FilmRepository {
    List<Film> getAll();
    Optional<Film> getFilmBy();
    
}
