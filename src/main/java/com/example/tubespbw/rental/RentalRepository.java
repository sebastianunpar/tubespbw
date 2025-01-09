package com.example.tubespbw.rental;

import java.time.LocalDate;
import java.util.List;

public interface RentalRepository {
    List<Rental> getUserRentals(int userId);

    List<Rental> getUserRentalHistory(int userId);
    boolean insertRental(LocalDate rentalDate, LocalDate dueDate, int filmId, int userId, String metodePembayaran, String noPembayaran);
}
