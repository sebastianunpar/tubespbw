package com.example.tubespbw.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {
    @GetMapping("")
    public String showHome() {
        return "home";
    }

    @GetMapping("/browse")
    public String showBrowse() {
        return "browse";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/rentals")
    public String showRentals() {
        return "user/rentals";
    }

    @GetMapping("/history")
    public String showHistory() {
        return "user/history";
    }

    @GetMapping("/movie-detail")
    public String showMovieDetail() {
        return "movieDetail";
    }

    @PostMapping("login")
    public String login() {
        return "redirect:";
    }
}
