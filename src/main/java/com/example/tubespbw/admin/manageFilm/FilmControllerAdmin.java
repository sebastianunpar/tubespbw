package com.example.tubespbw.admin.manageFilm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

import com.example.tubespbw.genre.Genre;
import com.example.tubespbw.RequiresRole;
import com.example.tubespbw.actor.Actor;
 
@Controller
@RequestMapping("/admin")
public class FilmControllerAdmin {
    @Autowired
    FilmServiceAdmin service;

    @GetMapping("/manage-movie")
    @RequiresRole("admin")
    public String showBrowse(Model model, 
                            @RequestParam(value = "movieName", required = false) String movieName,
                            @RequestParam(value = "actorName", required = false) List<String> actorName,
                            @RequestParam(value = "genreName", required = false) List<String> genreName) throws SQLException {
        
        if (actorName == null) {
            actorName = new ArrayList<>();
        }
        if (genreName == null) {
            genreName = new ArrayList<>();
        }

        List<FilmAdmin> films;
        if ((movieName != null && !movieName.isEmpty()) || 
            (actorName != null && !actorName.isEmpty()) || 
            (genreName != null && !genreName.isEmpty())) {
            films = service.filterFilmsByActorAndGenre(actorName, genreName, movieName);
        } else {
            films = service.getAllFilmUser();
        }

        model.addAttribute("actorName", actorName);
        model.addAttribute("genreName", genreName);
        model.addAttribute("films", films);
        model.addAttribute("actors", service.getAllActor());
        model.addAttribute("genres", service.getAllGenre());
        model.addAttribute("movieName", movieName);

        return "admin/browseAdmin";
    }


    @GetMapping("/film/{filmId}")
    public String showMovieDetail(@PathVariable("filmId") int filmId, Model model) throws SQLException {
        FilmDetailAdmin filmDetail = service.getFilmDetail(filmId);
        int sales = service.getFilmSales(filmId);
        model.addAttribute("filmDetail", filmDetail);
        model.addAttribute("sales", sales);
        return "movieDetail";
    }
}
