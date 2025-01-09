package com.example.tubespbw.rental;

import java.time.LocalDate;
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

    public boolean insertRental(int filmId, int userId, String metodePembayaran, String noPembayaran) {
        LocalDate rentalDate = LocalDate.now();
        LocalDate dueDate = rentalDate.plusDays(7);

        return rentalRepo.insertRental(rentalDate, dueDate, filmId, userId, metodePembayaran, noPembayaran);
    }

    public List<Integer> getRentalsPerMonth() {
        int year = LocalDate.now().getYear();
        return rentalRepo.getRentalsPerMonth(year);
    }

    public List<Double> getIncomePerMonth() {
        int year = LocalDate.now().getYear();
        return rentalRepo.getIncomePerMonth(2024);
    }
}
