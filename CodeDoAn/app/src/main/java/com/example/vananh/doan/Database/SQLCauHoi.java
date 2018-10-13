package com.example.vananh.doan.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.vananh.doan.Model.BoDe;
import com.example.vananh.doan.Model.CauHoi;

import java.util.ArrayList;

public class SQLCauHoi  extends  SqlDataHelper {
    public SQLCauHoi(Context context) {
        super(context);
    }

    public ArrayList<CauHoi> GetListCauHoiByBoDe(int maBoDe){
        ArrayList<CauHoi> listCauHoi =  new ArrayList<>();
        try {
            openDataBase();
            Cursor cs = database.rawQuery("SELECT * FROM CauHoi WHERE  IDBoDe = '"+maBoDe+"'",null);
            while (cs.moveToNext()){
                CauHoi cauHoi =  new CauHoi(
                        cs.getInt(0),
                        cs.getInt(1),
                        cs.getString(2), //ND
                        cs.getString(3), //A
                        cs.getString(4),
                        cs.getString(5),
                        cs.getString(6), //D
                        cs.getString(7)
                        //cs.getBlob(8)//Dung
                );
                listCauHoi.add(cauHoi);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        finally {
            close();
        }
        return  listCauHoi;
    }
    public SQLCauHoi(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }
}