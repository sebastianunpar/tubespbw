package com.example.tubespbw.film;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Actor {
    private int actorId;
    private String name;
    private boolean valid;
}
