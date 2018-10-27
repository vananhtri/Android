package com.example.vananh.doan.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CauHoi implements Serializable {
    int id;
    int iDBoDe;
    String noiDung;
    String dapAnA;
    String dapAnB;
    String dapAnC;
    String dapAnD;
    String cauTraLoi;
    int maLoaiCauHoi;

    public int getMaLoaiCauHoi() {
        return maLoaiCauHoi;
    }

    public void setMaLoaiCauHoi(int maLoaiCauHoi) {
        this.maLoaiCauHoi = maLoaiCauHoi;
    }

    public String getTenLoaiCauHoi() {
        return tenLoaiCauHoi;
    }

    public void setTenLoaiCauHoi(String tenLoaiCauHoi) {
        this.tenLoaiCauHoi = tenLoaiCauHoi;
    }

    String tenLoaiCauHoi;
    byte[] hinhAnh;

    ArrayList<String> listTraLoi = new ArrayList<>();

    public ArrayList<String> getListTraLoi() {

        return listTraLoi;
    }

    public void setListTraLoi(ArrayList<String> listTraLoi) {
        this.listTraLoi = listTraLoi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getiDBoDe() {
        return iDBoDe;
    }

    public void setiDBoDe(int iDBoDe) {
        this.iDBoDe = iDBoDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getDapAnA() {
        return dapAnA;
    }

    public void setDapAnA(String dapAnA) {
        this.dapAnA = dapAnA;
    }

    public String getDapAnB() {
        return dapAnB;
    }

    public void setDapAnB(String dapAnB) {
        this.dapAnB = dapAnB;
    }

    public String getDapAnC() {
        return dapAnC;
    }

    public void setDapAnC(String dapAnC) {
        this.dapAnC = dapAnC;
    }

    public String getDapAnD() {
        return dapAnD;
    }

    public void setDapAnD(String dapAnD) {
        this.dapAnD = dapAnD;
    }

    public String getCauTraLoi() {
        return cauTraLoi;
    }

    public void setCauTraLoi(String cauTraLoi) {
        this.cauTraLoi = cauTraLoi;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public CauHoi() {
    }

    public CauHoi(String tenLoaiCauHoi, String noiDung) {
        this.tenLoaiCauHoi = tenLoaiCauHoi;
        this.noiDung = noiDung;
    }

    public CauHoi(int id, int iDBoDe, String noiDung, String dapAnA, String dapAnB, String dapAnC, String dapAnD, String cauTraLoi) {
        this.id = id;
        this.iDBoDe = iDBoDe;
        this.noiDung = noiDung;
        this.dapAnA = dapAnA;
        this.dapAnB = dapAnB;
        this.dapAnC = dapAnC;
        this.dapAnD = dapAnD;
        this.cauTraLoi = cauTraLoi;
    }

    public CauHoi(int id, int iDBoDe, String noiDung, String dapAnA, String dapAnB, String dapAnC, String dapAnD, String cauTraLoi, byte[] hinhAnh) {
        this.id = id;
        this.iDBoDe = iDBoDe;
        this.noiDung = noiDung;
        this.dapAnA = dapAnA;
        this.dapAnB = dapAnB;
        this.dapAnC = dapAnC;
        this.dapAnD = dapAnD;
        this.cauTraLoi = cauTraLoi;
        this.hinhAnh = hinhAnh;
    }

    public CauHoi(int id, int maLoaiCauHoi, String tenLoaiCauHoi, String noiDung, String dapAnA, String dapAnB, String dapAnC, String dapAnD, String cauTraLoi) {
        this.id = id;
        this.maLoaiCauHoi = maLoaiCauHoi;
        this.tenLoaiCauHoi = tenLoaiCauHoi;
        this.noiDung = noiDung;
        this.dapAnA = dapAnA;
        this.dapAnB = dapAnB;
        this.dapAnC = dapAnC;
        this.dapAnD = dapAnD;
        this.cauTraLoi = cauTraLoi;
    }

    public ArrayList<String> ConvertCauTraLoiToList() {
        if (this.cauTraLoi != null) {
            ArrayList<String> listAns = new ArrayList<String>(Arrays.asList(this.cauTraLoi.split(",")));
            return listAns;
        }
        return new ArrayList<>();
    }
}
