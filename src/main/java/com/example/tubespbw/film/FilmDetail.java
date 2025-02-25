package com.example.tubespbw.film;

import java.text.NumberFormat;
import java.util.Base64;
import java.util.List;
import java.util.Locale;

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

    public String getPriceRupiah() {
        @SuppressWarnings("deprecation")
        Locale indonesia = new Locale("id", "ID");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(indonesia);
        return currencyFormat.format(price);
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
        if (poster != null) {
            this.base64Poster = Base64.getEncoder().encodeToString(poster);
        } else {
            this.base64Poster = null;
        }
    }    
}