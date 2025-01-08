package com.example.tubespbw.admin;

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
}
