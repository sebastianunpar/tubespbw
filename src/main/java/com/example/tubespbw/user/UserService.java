package com.example.tubespbw.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // public boolean register(User user) {
    //     user.setPassword(passwordEncoder.encode(user.getPassword()));
    //     try {
    //         userRepository.save(user);
    //     } catch (Exception e) {
    //         return false;
    //     }
    //     return true;
    // }

    // public User login(String username, String password) {
    //     Optional<User> user = userRepository.findByUsername(username);
    //     if (user.isPresent()) {
    //         if (passwordEncoder.matches(password, user.get().getPassword())) {
    //             return user.get();
    //         }
    //     }
    //     return null;
    // }
}
