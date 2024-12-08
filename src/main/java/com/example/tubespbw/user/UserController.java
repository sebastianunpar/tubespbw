package com.example.tubespbw.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping({"", "/"})
    public String showHome() {
        return "home";
    }

    @GetMapping("/browse")
    public String showBrowse() {
        return "browse";
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
}
