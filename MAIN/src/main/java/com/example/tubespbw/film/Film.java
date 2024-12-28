package com.example.tubespbw.film;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Film {
    private int filmId;
    private String title;
    private int stock;
    private byte[] poster;
    private String base64Poster;
}