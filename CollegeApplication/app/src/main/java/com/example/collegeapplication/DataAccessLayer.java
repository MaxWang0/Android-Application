package com.example.collegeapplication;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataAccessLayer {

	// Database fields
	  private SQLiteDatabase database;
	  private DataContract dbHelper;
	  private String[] allColumns = { "ID","NAME","EMAIL","DOB" };

	  public DataAccessLayer(Context context) {
	    dbHelper = new DataContract(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }
	
	  public StudentDtls createComment(String comment,String Email, String DOB) {
		    ContentValues values = new ContentValues();
		    values.put("NAME", comment);
		    values.put("EMAIL", Email);
		    values.put("DOB", DOB);
		    long insertId = database.insert("Registration", null,
		        values);
		    Cursor cursor = database.query("Registration",
		        allColumns, "ID" + " = " + insertId, null,
		        null, null, null);
		    cursor.moveToFirst();
		    StudentDtls newregistration = cursorToComment(cursor);
		    cursor.close();
		    return newregistration;
		  }
	  
	  public List<StudentDtls> getAllComments() {
		    List<StudentDtls> comments = new ArrayList<StudentDtls>();

		    Cursor cursor = database.query("Registration",
		        allColumns, null, null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		    	StudentDtls students = cursorToComment(cursor);
		      comments.add(students);
		      cursor.moveToNext();
		    }
		    // make sure to close the cursor
		    cursor.close();
		    return comments;
		  }

		  private StudentDtls cursorToComment(Cursor cursor) {
			  StudentDtls comment = new StudentDtls();
		    comment.setName(cursor.getString(1));
		    comment.setEmail(cursor.getString(2));
		    comment.setDOB(cursor.getString(3));
		    return comment;
		  }
	  
}
