package com.example.vananh.doan.Model;

public class DiemThi {
    String ngayThi, diemThi, ketQua;

    public DiemThi() {
    }

    public DiemThi(String ngayThi, String diemThi, String ketQua) {
        this.ngayThi = ngayThi;
        this.diemThi = diemThi;
        this.ketQua = ketQua;
    }

    public String getNgayThi() {
        return ngayThi;
    }

    public void setNgayThi(String ngayThi) {
        this.ngayThi = ngayThi;
    }

    public String getDiemThi() {
        return diemThi;
    }

    public void setDiemThi(String diemThi) {
        this.diemThi = diemThi;
    }

    public String getKetQua() {
        return ketQua;
    }

    public void setKetQua(String ketQua) {
        this.ketQua = ketQua;
    }
}
