package com.example.tubespbw.rental;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tubespbw.film.FilmService;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepo;

    @Autowired
    FilmService filmService;

    public List<Rental> getUserRentals(int userId) { 
        return rentalRepo.getUserRentals(userId);
    }

    public List<Rental> getUserRentalHistory(int userId) {
        return rentalRepo.getUserRentalHistory(userId);
    }

    public boolean insertRental(int filmId, int userId, String metodePembayaran) {
        LocalDate rentalDate = LocalDate.now();
        boolean success = filmService.removeFilmStock(filmId);
        if (success)
            return rentalRepo.insertRental(rentalDate, filmId, userId, metodePembayaran);
        else
            return false;
    }

    public List<Integer> getRentalsPerMonth(int year) {
        return rentalRepo.getRentalsPerMonth(year);
    }

    public List<Double> getIncomePerMonth(int year) {
        return rentalRepo.getIncomePerMonth(year);

    }

    public List<Integer> getRentalYears() {
        return rentalRepo.getRentalYears();
    }
}
