package com.example.tubespbw.rental;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Rental {
    private int rentalId;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private int filmId;
    private String title;
    private double price;
    private int userId;
    private String metodePembayaran;

    public String getFormattedRentalDate() {
        return rentalDate != null ? rentalDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")) : "";
    }

    public String getFormattedReturnDate() {
        return returnDate != null ? returnDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")) : "";
    }

    public String getFormattedDueDate() {
        LocalDate dueDate = rentalDate.plusDays(7);
        return dueDate != null ? dueDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")) : "";
    }

    public int getRemainingDays() {
        LocalDate dueDate = rentalDate.plusDays(7);
        if (rentalDate != null) {
            return (int) ChronoUnit.DAYS.between(LocalDate.now(), dueDate);
        }
        return -1;
    }

    public String getPriceRupiah() {
        @SuppressWarnings("deprecation")
        Locale indonesia = new Locale("id", "ID");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(indonesia);
        return currencyFormat.format(price);
    }
}
