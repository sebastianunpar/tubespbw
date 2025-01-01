package com.example.tubespbw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tubespbw.user.User;

import com.example.tubespbw.user.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLogin(User user) {
        return "login";
    }

    @PostMapping("/login")
    public String login(
        @RequestParam String email,
        @RequestParam String password,
        HttpSession session,
        Model model
    ) {
        User user = userService.login(email, password);
        if (user != null) {
            session.setAttribute("user", user);
            
            if (user.getRole().equals("admin")) {
                return "admin/home";
            }
            return "redirect:/";
        }
        model.addAttribute("status", "failed");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }    
}