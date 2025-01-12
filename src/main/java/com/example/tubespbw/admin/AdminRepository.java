package com.example.tubespbw.admin;

import java.time.LocalDate;
import java.util.List;

public interface AdminRepository {
    String getTotalIncome();
    String getBykDisewa();
    String getTitleTerlaris();
    byte[] getMostRentedMoviePoster();
    String getMonthlyIncome();
    List<ReportData> getReportByDateRange(String startDate, String endDate);
    List<ReportData> getMonthlyReport();
    List<ReportData> getOngoingRentals();
    String getMostPopularActor();
    String getMostPopularGenre();
    int getRentalCountByDateRange(String startDate, String endDate);
    String getTotalPriceByDateRange(String startDate, String endDate);
    String getMostPopularFilmTitleByDateRange(String startDate, String endDate);
    String getMostPopularGenreByDateRange(String startDate, String endDate);
    String getMostPopularActorByDateRange(String startDate, String endDate);
    void updateReturnDate(int rentalId, LocalDate returnDate);
}
