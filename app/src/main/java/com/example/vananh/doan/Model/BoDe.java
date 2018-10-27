package com.example.vananh.doan.Model;

public class BoDe {
    int id;
    String tenBoDe;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenBoDe() {
        return tenBoDe;
    }

    public void setTenBoDe(String tenBoDe) {
        this.tenBoDe = tenBoDe;
    }

    public  BoDe(){
        
    }
    public BoDe(int id, String tenBoDe) {
        this.id = id;
        this.tenBoDe = tenBoDe;
    }
}
