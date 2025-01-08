package com.example.tubespbw.genre;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Genre {
    private int genreId;
    private String name;
    private boolean valid;
}
