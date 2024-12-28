package com.example.tubespbw.user;


import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private String role;
}