package com.example.tubespbw.actor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Actor {
    private int actorId;
    private String name;
    private boolean valid;
}
