package com.example.fireman.sampleapp2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.PublicKey;
import java.util.ConcurrentModificationException;
import android.content.Context;
import android.database.sqlite.*;
/**
 * Created by Fireman on 2015/9/7.
 */
public class DataContract extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "StdEnrollment";
    private static final int DATABASE_VERSION = 2;
    private static final String SQL_ENTRIES =
            "CREATE TABLE Registration{"+
            "SNo INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Name TEXT"+
            "Email TEXT)";


    public  DataContract(Context context, String name, SQLiteDatabase.CursorFactory factory, int version ){
        super(context, name, factory, DATABASE_VERSION);

    }

    public void onCreate (SQLiteDatabase db){
        db.execSQL(SQL_ENTRIES);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        db.execSQL("DROP TABLE IF EXISTS" + SQL_ENTRIES);
        onCreate(db);

    }
}
