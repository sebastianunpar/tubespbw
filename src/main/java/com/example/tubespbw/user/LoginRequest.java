package com.example.tubespbw.user;

import lombok.Data;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class LoginRequest  {
    
    @NotNull
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "password is required")
    private String password;

}
