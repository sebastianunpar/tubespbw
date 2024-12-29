package com.example.tubespbw.admin;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tubespbw.film.FilmService;


@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    FilmService filmService;

    @GetMapping({"", "/"})
    public String showHome() {
        
        return "admin/home";
    }

    @GetMapping("/monthly-report")
    public String showMonthlyReport() {
        return "admin/monthlyReport";
    }

    @GetMapping("/income")
    public String showIncome() {
        return "admin/incomeGraph";
    }

    @GetMapping("/film-graph")
    public String showFilmGraph() {
        return "admin/filmGraph";
    }

    @GetMapping("/add-movie")
    public String showAddMovie(Model model) throws SQLException  {
        List<String> genres = filmService.getAllGenre();
        List<String> actors = filmService.getAllActor();
        model.addAttribute("genres", genres);
        model.addAttribute("actors", actors);
        return "admin/addMovie";
    }

    @PostMapping("add-genre")
    public String addGenre(@RequestParam String newGenre) {
        filmService.insertGenre(newGenre);
        return "redirect:/admin/add-movie";
    }

    @PostMapping("add-actor")
    public String addActor(@RequestParam String newActor) {
        filmService.insertActor(newActor);
        return "redirect:/admin/add-movie";
    }



    @GetMapping("/manage-actors")
    public String showManageActors() {
        return "admin/manageActors";
    }

    @GetMapping("/manage-genres")
    public String showManageGenres() {
        return "admin/manageGenres";
    }

    @GetMapping("/report")
    public String showReport() {
        return "admin/report";
    }
}
