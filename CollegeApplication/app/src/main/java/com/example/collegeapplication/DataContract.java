package com.example.collegeapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataContract extends SQLiteOpenHelper {

	private static final String TEXT_TYPE = " TEXT";
    private static final String DATABASE_NAME = "StudentEnrollment";
    
    private static final int DATABASE_VERSION = 2;
    private static final String COMMA_SEP = ",";
    
    
    private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + "Registration" + " (" +
        "ID" + " INTEGER PRIMARY KEY AUTOINCREMENT," +
        "NAME" + TEXT_TYPE + COMMA_SEP +
        "EMAIL" + TEXT_TYPE + COMMA_SEP +
        "DOB" + TEXT_TYPE +
        " )";
	
	public DataContract(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
	public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + SQL_CREATE_ENTRIES);
	    onCreate(db);
	}
}
