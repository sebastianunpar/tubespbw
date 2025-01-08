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

    public boolean register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public User login(String email, String password) {
        Optional<User> user = userRepository.findUser(email);
        if (user.isPresent()) {
            if (passwordEncoder.matches(password, user.get().getPassword())) {
                return user.get();
            }
        }
        return null;
    }

    public int getUserIdFromEmail(String email) {
        Optional<Integer> userIdO = userRepository.getUserIdFromEmail(email);
        if (!userIdO.isEmpty()) {
            int userId = userIdO.get();
            return userId;
        }
        return 0;
    }
}