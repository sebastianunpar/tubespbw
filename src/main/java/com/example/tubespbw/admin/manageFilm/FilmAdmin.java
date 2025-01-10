package com.example.tubespbw.admin.manageFilm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class FilmAdmin {
    private int filmId;
    private String title;
    private int stock;
    private byte[] poster;
    private String base64Poster;
}