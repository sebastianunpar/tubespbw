package com.example.tubespbw.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminController {
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
    public String showAddMovie() {
        return "admin/addMovie";
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
