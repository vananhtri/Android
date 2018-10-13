package com.example.vananh.doan.Model;

public class NguoiDung {
    int id;
    String TenNguoiDung, MatKhau;
    int tinhTrang;
    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public NguoiDung(String tenNguoiDung, String matKhau) {
        TenNguoiDung = tenNguoiDung;
        MatKhau = matKhau;
    }

    public NguoiDung() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenNguoiDung() {
        return TenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        TenNguoiDung = tenNguoiDung;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }
}
