package com.example.tubespbw.film;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;

import com.example.tubespbw.genre.Genre;
import com.example.tubespbw.actor.Actor;
 
@Controller
public class FilmController {
    @Autowired
    FilmService service;

    @GetMapping("/browse")
    public String showBrowse(Model model) throws SQLException{
        List<Film> films = service.getAllFilmUser();
        List<Actor> actors = service.getAllActor();
        List<Genre> genres = service.getAllGenre(); 

        model.addAttribute("films", films);
        model.addAttribute("actors", actors);
        model.addAttribute("genres", genres);
        
        return "browse";
    }

    @GetMapping("/film/{filmId}")
    public String showMovieDetail(@PathVariable("filmId") int filmId, Model model) throws SQLException {
        FilmDetail filmDetail = service.getFilmDetail(filmId);
        int sales = service.getFilmSales(filmId);
        model.addAttribute("filmDetail", filmDetail);
        model.addAttribute("sales", sales);
        return "movieDetail";
    }
}