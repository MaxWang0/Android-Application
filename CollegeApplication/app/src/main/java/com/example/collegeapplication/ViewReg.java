package com.example.collegeapplication;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewReg extends Activity  {

	private DataAccessLayer datasource;
	ListView stdList;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.view_registration);
	        
	        stdList = (ListView) findViewById(R.id.StudReg);
	        
	        datasource = new DataAccessLayer(this);
	        datasource.open();
	        
	        List<StudentDtls> values = datasource.getAllComments();
	        List<String> name = new ArrayList<String>();
	        for (StudentDtls student : values) {
				name.add(student.getName());
			}

	        // use the SimpleCursorAdapter to show the
	        // elements in a ListView
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>
	        	(this, android.R.layout.simple_list_item_1, name);
	        stdList.setAdapter(adapter);
	        
	 }
}
