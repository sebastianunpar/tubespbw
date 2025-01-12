package com.example.tubespbw.admin;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ReportData {
    private int rentalId;
    private String emailPenyewa;
    private String judulFilm;
    private Date tanggalPeminjaman;
    private Date tanggalPengembalian;
    private BigDecimal pemasukan;
    private String formattedPemasukan;
    private String formattedTanggalPeminjaman;
    private String formattedTanggalPengembalian;

    public ReportData(int rentalId, String emailPenyewa, String judulFilm, Date tanggalPeminjaman,
            Date tanggalPengembalian,
            BigDecimal pemasukan) {
        this.rentalId = rentalId;
        this.emailPenyewa = emailPenyewa;
        this.judulFilm = judulFilm;
        this.tanggalPeminjaman = tanggalPeminjaman;
        this.tanggalPengembalian = tanggalPengembalian;
        this.pemasukan = pemasukan;
    }

}
