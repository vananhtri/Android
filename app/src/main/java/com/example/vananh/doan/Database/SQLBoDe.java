package com.example.vananh.doan.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.vananh.doan.Model.BoDe;

import java.util.ArrayList;

public class SQLBoDe extends  SqlDataHelper {
    public SQLBoDe(Context context) {
        super(context);
    }

    public ArrayList<BoDe> GetListBoDe(){
        ArrayList<BoDe> listBoDe =  new ArrayList<>();
        try {
            openDataBase();
            Cursor cs = database.query("BoDe",null,null,null,null,null,null);
            while (cs.moveToNext()){
                BoDe boDe =  new BoDe(cs.getInt(0), cs.getString(1));
                listBoDe.add(boDe);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        finally {
            close();
        }


        return  listBoDe;
    }
    public SQLBoDe(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }
}
