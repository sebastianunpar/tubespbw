package com.example.tubespbw;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tubespbw.user.User;

import main.java.com.example.tubespbw.user.UserService;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        User user = userService.login(email, password);
        if (User != null ) {
            if (user.getRole() == "admin") {
                return "redirect:/admin";
            }
            else if (user.getRole() == "user")
            return "home";
        }
        return "redirect:";
    }

    @PostMapping("register")
    public String register() {
        return "redirect:/admin";
    }
}