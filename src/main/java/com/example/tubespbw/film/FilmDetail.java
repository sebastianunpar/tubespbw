package com.example.tubespbw.film;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class FilmDetail {
    private int filmId;
    private String title;
    private String synopsis;
    private byte[] poster;
    private int stock;
    private double price;
    private boolean valid;
    private List<String> genres;
    private List<String> actors;
    private String base64Poster;
}