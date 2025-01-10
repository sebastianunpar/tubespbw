package com.example.tubespbw.admin.manageFilm;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import com.example.tubespbw.genre.Genre;
import com.example.tubespbw.actor.Actor;

@Service
public class FilmServiceAdmin {
    @Autowired
    FilmRepositoryAdmin repo;

    public List<FilmAdmin> getAllFilmUser() throws SQLException{
        List<FilmAdmin> films = repo.getAll();
        for (FilmAdmin film : films) {
            String base64Poster = generateBase64Poster(film.getPoster());
            film.setBase64Poster(base64Poster);
        }
        return films;
    }

    private String generateBase64Poster(byte[] poster) {
        if (poster != null && poster.length > 0) {
            return Base64.getEncoder().encodeToString(poster);
        }
        return null;
    }

    public FilmDetailAdmin getFilmDetail(int filmId) throws SQLException {
        FilmDetailAdmin filmDetail = repo.getFilmDetail(filmId).get();
        String base64Poster = generateBase64Poster(filmDetail.getPoster());
        filmDetail.setBase64Poster(base64Poster);
        return filmDetail;
    }

    public int getFilmSales(int filmId) throws SQLException {
        return repo.getFilmSales(filmId);
    }

    public boolean insertGenre(String genre) {
        return repo.insertGenre(genre);
    }

    public boolean insertActor(String actor) {
        return repo.insertActor(actor);
    }

    public List<Genre> getAllGenre() throws SQLException {
        return repo.getAllGenre();
    }

    // Genre
    public Genre getGenreById(int id) throws SQLException {
        return repo.getGenreById(id);
    }

    public void updateGenre(int genreId, String genreName, boolean genreValid) throws SQLException {
        repo.updateGenre(genreId, genreName, genreValid); // Call repository to update genre
    }    
    public List<Genre> searchGenresByName(String genreName) {
        return repo.searchGenresByName(genreName);
    }

    public void changeValidGenre(int genreId) {
        repo.changeValidGenre(genreId);
    }

    // Aktor
    public Actor getActorById(int id) throws SQLException {
        return repo.getActorById(id);
    }
    public void updateActor(int actorId, String actorName, boolean actorValid) throws SQLException {
        repo.updateActor(actorId, actorName, actorValid); // Call repository to update genre
    }

    public List<Actor> searchActorsByName(String actorName) {
        return repo.searchActorsByName(actorName);
    }
    public void changeValidActor(int actorId) {
        repo.changeValidGenre(actorId);
    }
    // 
    public List<Actor> getAllActor() throws SQLException {
        return repo.getAllActor();
    }

    public boolean insertFilm(MultipartFile poster, String title, int price, int stock, String description, List<Integer> genres, List<Integer> actors) {
        try {
            byte[] posterBytes = poster.getBytes();
            repo.insertFilm(title, description, posterBytes, stock, price);
            int filmId = repo.getLatestFilmId().get();
            for (Integer genre : genres) {
                repo.insertFilmGenre(filmId, genre);
            }
            for (Integer actor : actors) {
                repo.insertFilmActor(filmId, actor);
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<FilmAdmin> searchFilms(String query) throws SQLException {
        List<FilmAdmin> films = repo.searchFilms(query);
        for (FilmAdmin film : films) {
            String base64Poster = generateBase64Poster(film.getPoster());
            film.setBase64Poster(base64Poster);
        }
        return films;
    }

    public List<FilmAdmin> filterFilmsByActorAndGenre(List<String> actorNames, List<String> genreNames, String movieName) throws SQLException {
        List<FilmAdmin> films = repo.filterFilmsByActorAndGenre(actorNames, genreNames, movieName);
        for (FilmAdmin film : films) {
            String base64Poster = generateBase64Poster(film.getPoster());
            film.setBase64Poster(base64Poster);
        }
        return films;
    }
    
    

}
