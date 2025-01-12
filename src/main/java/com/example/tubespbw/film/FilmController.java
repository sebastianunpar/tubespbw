package com.example.tubespbw.film;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.example.tubespbw.rental.RentalService;
import com.example.tubespbw.user.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class FilmController {
    @Autowired
    FilmService service;

    @Autowired
    RentalService rentalService;

    @GetMapping("/browse")
    public String showBrowse(Model model, HttpSession session,
            @RequestParam(value = "movieName", required = false) String movieName,
            @RequestParam(value = "actorName", required = false) List<String> actorName,
            @RequestParam(value = "genreName", required = false) List<String> genreName, 
            @RequestParam(name = "page", defaultValue = "1") int page) throws SQLException {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
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
            films = service.filterFilmsByActorAndGenre(actorName, genreName, movieName);
            filmCount = service.getFilmCountByActorAndGenre(actorName, genreName, movieName);
        } else {
            films = service.getAllFilmUser();
            filmCount = service.getFilmCount();
        }

        int show = 18;
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
    public String showMovieDetail(@PathVariable("filmId") int filmId, HttpSession session, Model model) throws SQLException {
        FilmDetail filmDetail = service.getFilmDetail(filmId);
        int sales = service.getFilmSales(filmId);

        LocalDate rentalDate = LocalDate.now();
        LocalDate dueDate = rentalDate.plusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");
        String formattedRentalDate = rentalDate.format(formatter);
        String formattedDueDate = dueDate.format(formatter);

        model.addAttribute("filmDetail", filmDetail);
        model.addAttribute("sales", sales);
        model.addAttribute("rentalDate", formattedRentalDate);
        model.addAttribute("dueDate", formattedDueDate);
        session.removeAttribute("purchaseSuccess");
        return "movieDetail";
    }

    @PostMapping("/film/sewa")
    public String sewa(HttpSession session,RedirectAttributes redirectAttributes, @RequestParam String filmIdStr, @RequestParam("payment-method") String paymentMethod) throws SQLException {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        User user = (User)session.getAttribute("user");
        int filmId = Integer.parseInt(filmIdStr);
        int userId = user.getUserId();

        boolean success = rentalService.insertRental(filmId, userId, paymentMethod);

        if (success)
            return "redirect:/rentals";
        else
            return "redirect:/film/" + filmId;
    }
}