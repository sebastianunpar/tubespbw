package com.example.tubespbw.film;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FilmController {
    @GetMapping("/browse")
    public String showBrowse(Model model) {
        
        return "browse";
    }
}
