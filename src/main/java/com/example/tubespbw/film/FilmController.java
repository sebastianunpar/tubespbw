package com.example.tubespbw.film;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;


@Controller
public class FilmController {
    @Autowired
    FilmService service;

    @GetMapping("/browse")
    public String showBrowse(Model model) throws SQLException{
        List<Film> films = service.getAllFilmUser();
        model.addAttribute("films", films);
        return "browse";
    }

    @GetMapping("/film/{filmId}")
    public String showMovieDetail(@PathVariable int id, Model model) throws SQLException {
        Optional<FilmDetail> filmDetail = service.getFilmDetail(id);
        return "movieDetail";
    }
}
