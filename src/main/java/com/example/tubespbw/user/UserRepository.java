package com.example.tubespbw.user;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findUser (String email);
}
