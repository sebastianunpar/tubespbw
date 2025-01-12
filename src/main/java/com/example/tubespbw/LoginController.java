package com.example.tubespbw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.tubespbw.user.LoginRequest;
import com.example.tubespbw.user.User;

import com.example.tubespbw.user.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLogin(LoginRequest loginRequest) {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @Valid LoginRequest loginRequest,
            BindingResult bindingResult,
            HttpSession session,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("loginRequest", loginRequest);
            return "login";
        }
        User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
            session.setAttribute("user", user);

            if (user.getRole().equals("admin")) {
                session.setAttribute("role", "admin");
                return "redirect:/admin";
            }
            session.setAttribute("role", "user");
            return "redirect:/";
        }
        model.addAttribute("error", "Wrong email or password");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}