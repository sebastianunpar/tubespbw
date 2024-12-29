package com.example.tubespbw.film;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Genre {
    private int genreId;
    private String name;
    private boolean valid;
}
