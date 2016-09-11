package com.example.collegeapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class NewReg extends Activity  {

	private DataAccessLayer datasource;
	EditText name,email,dob;
	//DatePicker dob;
	Button submit,cancel;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.new_registration);
	       
	        name = (EditText) findViewById(R.id.nameField);
	        email = (EditText) findViewById(R.id.emailAddressText);
	        //dob = (DatePicker) findViewById(R.id.dobPicker);
	        dob = (EditText) findViewById(R.id.dobPicker);
	        submit = (Button) findViewById(R.id.submit);
	        cancel = (Button) findViewById(R.id.cancel);

	        submit.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					datasource = new DataAccessLayer(getApplicationContext());
			        datasource.open();
			        StudentDtls newobj=
			        datasource.createComment(name.getText().toString(), 
			        		email.getText().toString(),
			        		//dob.getYear()+"-"+dob.getMonth()+"-"+dob.getDayOfMonth());
			        		dob.getText().toString());
			        
			        if(newobj.getName().equals(name.getText().toString()))
			        {
			        	Intent main = new Intent(getApplicationContext(),ViewReg.class);
			        	startActivity(main);
			        }
			        else{
			        	Intent main = new Intent(getApplicationContext(),MainActivity.class);
			        	startActivity(main);
			        }
				}
			});
	        
	 }
}
