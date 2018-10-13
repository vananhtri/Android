package com.example.vananh.doan.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.vananh.doan.Model.NguoiDung;

public class SQLNguoiDung extends SqlDataHelper {

    public SQLNguoiDung(Context context) {
        super(context);
    }

    public SQLNguoiDung(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public NguoiDung getNguoiDung(String username, String pasword) {
        NguoiDung nguoiDung = new NguoiDung();
        try {
            openDataBase();
//            Cursor cs = database.query("BoDe",null,null,null,null,null,null);
            Cursor cs = database.rawQuery("select * from NguoiDung where TenNguoiDung = ? and MatKhau= ?",
                    new String[]{username, pasword});
            while (cs.moveToNext()) { // co gia tri
                nguoiDung.setId(cs.getInt(0));
                nguoiDung.setTenNguoiDung(cs.getString(1));
                nguoiDung.setMatKhau(cs.getString(2));
                nguoiDung.setTinhTrang(cs.getInt(3));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }


        return nguoiDung;
    }
}
