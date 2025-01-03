package com.example.tubespbw.film;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

 
@Controller
public class FilmController {
    @Autowired
    FilmService service;

    @GetMapping("/browse")
    public String showBrowse(Model model, 
                            @RequestParam(value = "movieName", required = false) String movieName,
                            @RequestParam(value = "actorName", required = false) List<String> actorName,
                            @RequestParam(value = "genreName", required = false) List<String> genreName, 
                            @RequestParam(name = "page", defaultValue = "1") int page) throws SQLException {

        if (actorName == null) {
        actorName = new ArrayList<>();
        }
        if (genreName == null) {
            genreName = new ArrayList<>();
        }
        
        int filmCount = 0;
        List<Film> films;
        if (movieName != null && !movieName.isEmpty()) {
            films = service.searchFilms(movieName);
            filmCount = service.getFilmCountByName(movieName);
        } else if ((actorName != null && !actorName.isEmpty()) || (genreName != null && !genreName.isEmpty())) {
            films = service.filterFilmsByActorAndGenre(actorName, genreName);
            filmCount = service.getFilmCountByActorAndGenre(actorName, genreName);
        } else {
            films = service.getAllFilmUser();
            filmCount = service.getFilmCount();
        }

        System.out.println(filmCount);
        
        int show = 21;
        int start = (page - 1) * show;
        int pageCount = (int) Math.ceil((double) filmCount / show);

        films = films.stream().skip(start).limit(show).collect(Collectors.toList());

        model.addAttribute("actorName", actorName);
        model.addAttribute("genreName", genreName);
        model.addAttribute("films", films);
        model.addAttribute("actors", service.getAllActor());
        model.addAttribute("genres", service.getAllGenre());
        model.addAttribute("movieName", movieName);

        model.addAttribute("currentPage", page);
        model.addAttribute("pageCount", pageCount);

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