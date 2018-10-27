package com.example.vananh.doan.Model;

public class LoaiCauHoi {
    int maLoaiCauHoi;
    String TenLoaiCauHoi;



    public  LoaiCauHoi(){

    }
    public LoaiCauHoi(int maLoaiCauHoi, String tenLoaiCauHoi) {
        this.maLoaiCauHoi = maLoaiCauHoi;
        TenLoaiCauHoi = tenLoaiCauHoi;
    }

    public int getMaLoaiCauHoi() {
        return maLoaiCauHoi;
    }

    public void setMaLoaiCauHoi(int maLoaiCauHoi) {
        this.maLoaiCauHoi = maLoaiCauHoi;
    }

    public String getTenLoaiCauHoi() {
        return TenLoaiCauHoi;
    }

    public void setTenLoaiCauHoi(String tenLoaiCauHoi) {
        TenLoaiCauHoi = tenLoaiCauHoi;
    }
}
