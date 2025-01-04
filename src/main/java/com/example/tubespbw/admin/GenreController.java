// package com.example.tubespbw.admin;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;

// import com.example.tubespbw.genre.Genre;
// import com.example.tubespbw.genre.GenreService;

// import java.util.List;




// UDAH ADA DI ADMIN CONTROLLER -> @GetMapping("/manage-genres")

// boleh dipindahin ke sini

// -seba




// @Controller
// public class GenreController {

//     @Autowired
//     private GenreService genreService;

//     @GetMapping("/admin/manage-genres")
//     public String manageGenres(Model model) {
//         List<Genre> genres = genreService.getAllGenres();
//         model.addAttribute("genres", genres);
//         return "admin/manageGenres"; // Ensure this matches the location of your HTML
//     }
// }
