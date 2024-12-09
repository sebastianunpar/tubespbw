package com.example.tubespbw;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        return "redirect:";
    }

    @PostMapping("register")
    public String register() {
        return "redirect:/admin";
    }
}