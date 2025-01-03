package com.example.tubespbw.rental;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Rental {
    private int rentalId;
    private LocalDate rentalDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private int filmId;
    private String title;
    private int userId;
    private String metodePembayaran;
    private String noPembayaran;

    public String getFormattedRentalDate() {
        return rentalDate != null ? rentalDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")) : "";
    }

    public String getFormattedDueDate() {
        return dueDate != null ? dueDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")) : "";
    }

    public String getFormattedReturnDate() {
        return returnDate != null ? returnDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")) : "";
    }

    public int getRemainingDays() {
        if (rentalDate != null && dueDate != null) {
            return (int) ChronoUnit.DAYS.between(rentalDate, dueDate);
        }
        return -1;
    }
}
