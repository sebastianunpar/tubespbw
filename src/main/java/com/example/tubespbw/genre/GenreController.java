package com.example.tubespbw.genre;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tubespbw.RequiresRole;
import com.example.tubespbw.actor.Actor;
import com.example.tubespbw.film.FilmService;
import com.example.tubespbw.genre.Genre;

@Controller
@RequestMapping("/admin")
public class GenreController {

    @Autowired
    FilmService filmService;

    @GetMapping("/manage-genres")
    @RequiresRole("admin")
    public String showManageGenres(Model model) throws SQLException {
        List<Genre> genres = filmService.getAllGenre();
        System.out.println(genres);
        model.addAttribute("genres", genres);
        return "admin/manageGenres";
    }

    @GetMapping("/search-genres")
    @RequiresRole("admin")
    public String searchActors(@RequestParam("genre_name") String genreName, Model model) {
        List<Genre> genre = filmService.searchGenresByName(genreName);
        model.addAttribute("genres", genre);
        return "admin/manageGenres";
    }

    @GetMapping("/add-genre-page")
    @RequiresRole("admin")
    public String showAddGenrePage() {
        return "/admin/addGenre";
    }

    @PostMapping("/add-genre-page")
    @RequiresRole("admin")
    public String addGenreManage(@RequestParam String newGenre) {
        filmService.insertGenre(newGenre);
        return "redirect:/admin/manage-genres";
    }

    @PostMapping("add-genre")
    @RequiresRole("admin")
    public String addGenre(@RequestParam String newGenre) {
        filmService.insertGenre(newGenre);
        return "redirect:/admin/add-movie";
    }

    @GetMapping("/genre/{genreId}")
    @RequiresRole("admin")
    public String showGenreEdit(@PathVariable("genreId") int genreId, Model model) throws SQLException {
        System.out.println("Fetching genre with ID: " + genreId);
        Genre genre = filmService.getGenreById(genreId);
        if (genre == null) {
            throw new RuntimeException("Genre not found with ID: " + genreId);
        }
        model.addAttribute("genre", genre);
        return "admin/editGenre";
    }

    @PostMapping("/genre/update")
    @RequiresRole("admin")
    public String updateGenre(@RequestParam("genreId") int genreId,
                              @RequestParam("genreName") String genreName,
                              @RequestParam("genreValid") boolean genreValid) throws SQLException {
        filmService.updateGenre(genreId, genreName, genreValid); // Update the genre
        return "redirect:/admin/manage-genres"; // Redirect back to manage genres
    }    

    @PostMapping("/change-valid-genre")
    @RequiresRole("admin")
    public String changeValidGenre(@RequestParam("genreId") int genreId, RedirectAttributes redirectAttributes) {
            filmService.changeValidGenre(genreId);
            redirectAttributes.addFlashAttribute("successMessage", "Genre deleted successfully.");
        return "redirect:/admin/manage-genres";
    }
}
