package com.example.tubespbw.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.tubespbw.rental.Rental;
import com.example.tubespbw.rental.RentalService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RentalService rentalService;

    @GetMapping({"", "/"})
    public String showHome(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "home";
    }

    @PostMapping("register")
    public String register(
        @Valid User user,
        BindingResult bindingResult,
        Model model
        ) {
        user.setRole("user");
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("formState", "register");
            return "login";
        }
        if (!user.getPassword().equals(user.getConfirmpassword())) {
            bindingResult.rejectValue(
                "confirmpassword", 
                "PasswordMissmatch",
                "Password do not match"
                );
            model.addAttribute("user", user);
            model.addAttribute("formState", "register");
            return "login";
        }
        boolean success = userService.register(user);
        if (success) {
            model.addAttribute("user", user);
            return "home";
        }
        else {
            bindingResult.rejectValue("email", "error.username", "email is already taken");
        }
        model.addAttribute("error", "Registration failed. Please try again.");
        model.addAttribute("user", user);
        model.addAttribute("formState", "register");
        // return "redirect:/admin";
        return "login";
    }

    @GetMapping("/rentals")
    public String showRentals(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        User user = (User) session.getAttribute("user");
        int userId = userService.getUserIdFromEmail(user.getEmail());
        if (userId != 0) {
            List<Rental> rentals = rentalService.getUserRentals(userId);
            System.out.println(rentals);
            model.addAttribute("rentals", rentals);
        }
        return "user/rentals";
    }

    @GetMapping("/history")
    public String showHistory(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        User user = (User) session.getAttribute("user");
        int userId = userService.getUserIdFromEmail(user.getEmail());
        if (userId != 0) {
            List<Rental> rentalHisotry = rentalService.getUserRentalHistory(userId);
            System.out.println(rentalHisotry);
            model.addAttribute("rentalHistory", rentalHisotry);
        }
        return "user/history";
    }
}