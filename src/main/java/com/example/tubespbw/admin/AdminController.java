package com.example.tubespbw.admin;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.tubespbw.film.Actor;
import com.example.tubespbw.film.Film;
import com.example.tubespbw.film.FilmService;
import com.example.tubespbw.film.Genre;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    FilmService filmService;

    @Autowired
    AdminJdbcRepo adminRepo;

    @GetMapping({ "", "/" })
    public String showHome(Model model) throws SQLException {
        // Retrieve all films
        List<Film> films = filmService.getAllFilmUser();
        model.addAttribute("bykFilm", films.size());

        // Retrieve total income
        model.addAttribute("totalIncome", adminRepo.getTotalIncome());

        // Retrieve all genres
        List<Genre> genres = filmService.getAllGenre();
        model.addAttribute("bykGenre", genres.size());

        // Retrieve all actors
        List<Actor> actors = filmService.getAllActor();
        model.addAttribute("bykAktor", actors.size());

        model.addAttribute("titleTerlaris", adminRepo.getTitleTerlaris());

        // Retrieve rental count for the most rented movie
        model.addAttribute("bykDisewa", adminRepo.getBykDisewa());

        return "admin/home";
    }

    @GetMapping("/poster")
    public ResponseEntity<byte[]> getMostRentedMoviePoster() {
        byte[] poster = adminRepo.getMostRentedMoviePoster();

        if (poster == null) {
            return ResponseEntity.notFound().build(); // Return a 404 if no poster is found
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Adjust MIME type if necessary (e.g., PNG)
        return new ResponseEntity<>(poster, headers, HttpStatus.OK);
    }

    @GetMapping("/current-rentals")
    public String showCurrentRentals() {
        return "admin/lease";
    }

    @GetMapping("/monthly-report")
    public String showMonthlyReport(
            @RequestParam(value = "start-date", required = false) String startDate,
            @RequestParam(value = "end-date", required = false) String endDate,
            Model model) {
        List<ReportData> reports;

        if (startDate != null && endDate != null) {
            reports = adminRepo.getReportByDateRange(startDate, endDate);
        } else {
            reports = adminRepo.getMonthlyReport();
        }

        model.addAttribute("reports", reports);

        model.addAttribute("totalIncome", adminRepo.getTotalIncome());
        model.addAttribute("monthlyIncome", adminRepo.getMonthlyIncome());
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

    @GetMapping("/manage-movie")
    public String showManageMovie() {
        return "admin/browseAdmin";
    }

    @GetMapping("/add-movie")
    public String showAddMovie(Model model) throws SQLException {
        List<Genre> genres = filmService.getAllGenre();
        List<Actor> actors = filmService.getAllActor();
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

    @PostMapping("addFilm")
    public String addFilm(@RequestParam MultipartFile poster, @RequestParam String title, @RequestParam int price,
            @RequestParam int stock, @RequestParam String description, @RequestParam("genres") List<Integer> genres,
            @RequestParam("actors") List<Integer> actors) {
        // System.out.println(title);
        // System.out.println(price);
        // System.out.println(stock);
        // System.out.println(description);
        // System.out.println(genres);
        // System.out.println(actors);
        filmService.insertFilm(poster, title, price, stock, description, genres, actors);
        return "redirect:/admin/add-movie";
    }

    @GetMapping("/manage-actors")
    public String showManageActors(Model model) throws SQLException {
        List<Actor> actors = filmService.getAllActor();
        model.addAttribute("actors", actors);
        return "admin/manageActors";
    }

    @GetMapping("/manage-genres")
    public String showManageGenres(Model model) throws SQLException {
        List<Genre> genres = filmService.getAllGenre();
        System.out.println(genres);
        model.addAttribute("genres", genres);
        return "admin/manageGenres";
    }

    @GetMapping("/report")
    public String showReport() {
        return "admin/report";
    }
}
