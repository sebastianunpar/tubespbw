package com.example.tubespbw.rental;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepo;

    public List<Rental> getUserRentals(int userId) {
        return rentalRepo.getUserRentals(userId);
    }

    public List<Rental> getUserRentalHistory(int userId) {
        return rentalRepo.getUserRentalHistory(userId);
    }

}
