package com.example.tubespbw.rental;

import java.util.List;

public interface RentalRepository {
    List<Rental> getUserRentals(int userId);

    List<Rental> getUserRentalHistory(int userId);

}
