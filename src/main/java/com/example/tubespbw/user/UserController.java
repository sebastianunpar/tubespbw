package com.example.tubespbw.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UserController {
    @GetMapping("/home")
    public String showHome() {
        return "home";
    }

    @GetMapping("/browse")
    public String showBrowse() {
        return "home";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }
}
