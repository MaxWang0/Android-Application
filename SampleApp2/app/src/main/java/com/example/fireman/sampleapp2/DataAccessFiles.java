package com.example.fireman.sampleapp2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract;
import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Fireman on 2015/9/7.
 */
public class DataAccessFiles {

    private SQLiteDatabase database;
    private DataContract dbHelper;
    private String [] all_Columns = {"SNo", "Name", "Email"};
    public DataAccessFiles(Context context) {

        dbHelper = new DataContract(context);
    }

         public void open() throws SQLException {
            dbHelper.getWritableDatabase();
        }

         public void close(){
             dbHelper.close();
    }

    public studDtls insertValues(String name, String mail){
        ContentValues val = new ContentValues();
        val.put("Name", name);
        val.put("Email", mail);
        long insertid = database.insert("Registration", null, val);
        Cursor cur = database.query(false, "Registration", all_Columns, null, null, null, null, null, null, null);
        cur.moveToFirst();
        studDtls retVal = cursorToClass(cur);
        return retVal;
    }

    public ArrayList<studDtls> GetTheList(){
        ArrayList<studDtls> students = new ArrayList<studDtls>();
        Cursor cur = database.query(false, "Registration", all_Columns, null, null, null, null, null, null, null);
        cur.moveToFirst();
        while(!cur.isAfterLast()){
            studDtls retVal = cursorToClass(cur);
            students.add(retVal);
            cur.moveToFirst();
        }
        return students;
    }


    public studDtls cursorToClass(Cursor cur){
        studDtls obj = new studDtls();
        obj.setName(cur.getString(1));
        obj.setMail(cur.getString(2));

        return null;

    }



}
