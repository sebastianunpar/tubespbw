package com.example.tubespbw.admin;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.Year;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tubespbw.RequiresRole;
import com.example.tubespbw.actor.Actor;
import com.example.tubespbw.film.Film;
import com.example.tubespbw.film.FilmDetail;
import com.example.tubespbw.film.FilmService;
import com.example.tubespbw.genre.Genre;
import com.example.tubespbw.rental.RentalService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    RentalService rentalService;

    @Autowired
    FilmService filmService;

    @Autowired
    AdminJdbcRepo adminRepo;

    @GetMapping({ "", "/" })
    @RequiresRole("admin")
    public String showHome(Model model, HttpSession session) throws SQLException {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
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

        Film filmTerlaris = filmService.getFilmTerlaris();
        model.addAttribute("filmTerlaris", filmTerlaris);

        // Retrieve rental count for the most rented movie
        model.addAttribute("bykDisewa", adminRepo.getBykDisewa());

        return "admin/home";
    }

    // @GetMapping("/poster")
    // public ResponseEntity<byte[]> getMostRentedMoviePoster() {
    //     byte[] poster = adminRepo.getMostRentedMoviePoster();

    //     if (poster == null) {
    //         return ResponseEntity.notFound().build(); // Return a 404 if no poster is found
    //     }

    //     HttpHeaders headers = new HttpHeaders();
    //     headers.setContentType(MediaType.IMAGE_JPEG); // Adjust MIME type if necessary (e.g., PNG)
    //     return new ResponseEntity<>(poster, headers, HttpStatus.OK);
    // }

    @GetMapping("/current-rentals")
    @RequiresRole("admin")
    public String showCurrentRentals(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        List<ReportData> reports;
        reports = adminRepo.getOngoingRentals();

        model.addAttribute("reports", reports);
        return "admin/rentals";
    }

    @PostMapping("/current-rentals/mark-done")
    public String markRentalDone(@RequestParam("rentalId") int rentalId, RedirectAttributes redirectAttributes) {
        try {
            adminRepo.updateReturnDate(rentalId, LocalDate.now());
            filmService.addFilmStock(filmService.getFilmIdByRentalId(rentalId));
            redirectAttributes.addFlashAttribute("message", "Rental marked as done successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to mark rental as done.");
        }
        return "redirect:/admin/current-rentals";
    }

    @GetMapping("/monthly-report")
    @RequiresRole("admin")
    public String showMonthlyReport(
            @RequestParam(value = "start-date", required = false) String startDate,
            @RequestParam(value = "end-date", required = false) String endDate,
            Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        List<ReportData> reports;

        if (startDate == null || startDate.isEmpty()) {
            startDate = LocalDate.now().withDayOfMonth(1).toString();
        }
        if (endDate == null || endDate.isEmpty()) {
            endDate = LocalDate.now().toString();
        }

        if (startDate != null && endDate != null) {
            reports = adminRepo.getReportByDateRange(startDate, endDate);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
        } else {
            reports = adminRepo.getMonthlyReport();
        } 

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String firstDay = LocalDate.now().withDayOfMonth(1).format(formatter);
        String today = LocalDate.now().format(formatter);
        String rentalCount = adminRepo.getBykDisewa();
        String title = adminRepo.getTitleTerlaris();

        model.addAttribute("firstDay", firstDay);
        model.addAttribute("today", today);
        model.addAttribute("rentalCount", rentalCount);
        model.addAttribute("title", title);
        model.addAttribute("genre", adminRepo.getMostPopularGenre());
        model.addAttribute("actor", adminRepo.getMostPopularActor());
        model.addAttribute("reports", reports);
        model.addAttribute("totalIncome", adminRepo.getTotalIncome());
        model.addAttribute("monthlyIncome", adminRepo.getMonthlyIncome());
        return "admin/monthlyReport";
    }

    @GetMapping("/income-graph")
    @RequiresRole("admin")
    public String showIncome(@RequestParam(value = "year", required = false) Integer selectedYear, Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        if (selectedYear == null) {
            selectedYear = Year.now().getValue();
        }
        List<Double> data = rentalService.getIncomePerMonth(selectedYear);
        List<Integer> years = rentalService.getRentalYears();
        model.addAttribute("years", years);
        model.addAttribute("selectedYear", selectedYear);
        model.addAttribute("data", data);
        return "admin/incomeGraph";
    }

    @GetMapping("/film-graph")
    @RequiresRole("admin")
    public String showFilmGraph(@RequestParam(value = "year", required = false) Integer selectedYear, Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        if (selectedYear == null) {
            selectedYear = Year.now().getValue();
        }
        List<Integer> data = rentalService.getRentalsPerMonth(selectedYear);
        List<Integer> years = rentalService.getRentalYears();
        model.addAttribute("data", data);
        model.addAttribute("years", years);
        model.addAttribute("selectedYear", selectedYear);
        return "admin/filmGraph";
    }

   @GetMapping("/manage-movie")
   @RequiresRole("admin") 
    public String showBrowse(Model model, HttpSession session, 
                            @RequestParam(value = "movieName", required = false) String movieName,
                            @RequestParam(value = "actorName", required = false) List<String> actorName,
                            @RequestParam(value = "genreName", required = false) List<String> genreName, 
                            @RequestParam(name = "page", defaultValue = "1") int page) throws SQLException {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        
        if (actorName == null) {
            actorName = new ArrayList<>();
        }
        if (genreName == null) {
            genreName = new ArrayList<>();
        }

        int filmCount = 0;
        List<Film> films;
        if ((movieName != null && !movieName.isEmpty()) || 
            (actorName != null && !actorName.isEmpty()) || 
            (genreName != null && !genreName.isEmpty())) {
            films = filmService.filterFilmsByActorAndGenre(actorName, genreName, movieName);
            filmCount = filmService.getFilmCountByActorAndGenre(actorName, genreName, movieName);
        } else {
            films = filmService.getAllFilmUser();
            filmCount = filmService.getFilmCount();
        }

        int show = 18;
        int start = (page - 1) * show;
        int pageCount = (int) Math.ceil((double) filmCount / show);

        films = films.stream().skip(start).limit(show).collect(Collectors.toList());

        model.addAttribute("actorName", actorName);
        model.addAttribute("genreName", genreName);
        model.addAttribute("films", films);
        model.addAttribute("actors", filmService.getAllActor());
        model.addAttribute("genres", filmService.getAllGenre());
        model.addAttribute("movieName", movieName);

        model.addAttribute("currentPage", page);
        model.addAttribute("pageCount", pageCount);

        return "admin/browseAdmin";
    }

    @RequiresRole("admin")
    @GetMapping("/edit-movie/{filmId}")
    public String showEditMovie(@PathVariable("filmId") int filmId, HttpSession session, Model model) throws SQLException {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        FilmDetail filmDetail = filmService.getFilmDetail(filmId);
        List<Genre> genres = filmService.getAllGenre();
        List<Actor> actors = filmService.getAllActor();
    
        model.addAttribute("filmDetail", filmDetail);
        model.addAttribute("genres", genres);
        model.addAttribute("actors", actors);
        return "admin/editMovie";
    }

    @RequiresRole("admin")
    @PostMapping("updateFilm")
    public String updateMovie(
                            @RequestParam("filmId") int filmId,
                            @RequestParam("poster") MultipartFile poster, 
                            @RequestParam("title") String title, 
                            @RequestParam double price,
                            @RequestParam int stock, 
                            @RequestParam String description, 
                            @RequestParam("genres") List<Integer> genres,
                            @RequestParam("actors") List<Integer> actors
                            ) {
        filmService.updateFilm(poster, title, (int)price, stock, description, genres, actors, filmId);
        return "redirect:/admin/manage-movie";
    }
    
    @RequiresRole("admin")
    @PostMapping("add-genre-edit")
    public String addGenreEdit(@RequestParam String newGenre, @RequestParam String filmId) {
        filmService.insertGenre(newGenre);
        return "redirect:/admin/edit-movie/"+filmId;
    }

    @RequiresRole("admin")
    @PostMapping("add-actor-edit")
    public String addActorEdit(@RequestParam String newActor, @RequestParam String filmId) {
        filmService.insertActor(newActor);
        return "redirect:/admin/edit-movie/"+filmId;
    }

    @GetMapping("/add-movie")
    @RequiresRole("admin")
    public String showAddMovie(Model model, HttpSession session) throws SQLException {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        List<Genre> genres = filmService.getAllGenre();
        List<Actor> actors = filmService.getAllActor();
        model.addAttribute("genres", genres);
        model.addAttribute("actors", actors);
        return "admin/addMovie";
    }

    @PostMapping("addFilm")
    @RequiresRole("admin")
    public String addFilm(@RequestParam MultipartFile poster, @RequestParam String title, @RequestParam int price,
            @RequestParam int stock, @RequestParam String description, @RequestParam("genres") List<Integer> genres,
            @RequestParam("actors") List<Integer> actors) {
        filmService.insertFilm(poster, title, price, stock, description, genres, actors);
        return "redirect:/admin/add-movie";
    }

    @GetMapping("/report")
    @RequiresRole("admin")
    public String showReport(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "admin/report";
    }
}
