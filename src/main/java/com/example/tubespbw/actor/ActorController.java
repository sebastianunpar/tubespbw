package com.example.tubespbw.actor;

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

import com.example.tubespbw.actor.Actor;
import com.example.tubespbw.film.FilmService;
import com.example.tubespbw.genre.Genre;

@Controller
@RequestMapping("/admin")
public class ActorController {
    
    @Autowired
    FilmService filmService;

    @GetMapping("/manage-actors")
    public String showManageActors(Model model) throws SQLException {
        List<Actor> actors = filmService.getAllActor();
        System.out.println("Actors: " + actors); // Debug log
        model.addAttribute("actors", actors);
        return "admin/manageActors";
    }

    @PostMapping("add-actor")
    public String addActor(@RequestParam String newActor) {
        filmService.insertActor(newActor);
        return "redirect:/admin/add-movie";
    }

    @GetMapping("/actor/{actorId}")
    public String showGenreEdit(@PathVariable("actorId") int actorId, Model model) throws SQLException {
        System.out.println("Fetching actor with ID: " + actorId);
        Actor actor = filmService.getActorById(actorId);
        if (actor == null) {
            throw new RuntimeException("Actor not found with ID: " + actorId);
        }
        model.addAttribute("actor", actor);
        return "admin/editActor";
    }

    @PostMapping("/actor/update")
    public String updateActor(@RequestParam("actorId") int genreId,
                              @RequestParam("actorName") String genreName,
                              @RequestParam("actorValid") boolean genreValid) throws SQLException {
        filmService.updateActor(genreId, genreName, genreValid); // Update the genre
        return "redirect:/admin/manage-actors"; // Redirect back to manage genres
    }    

}
