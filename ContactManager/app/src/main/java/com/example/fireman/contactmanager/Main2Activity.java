package com.example.fireman.contactmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {


    EditText firstname, lastname, phone, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firstname = (EditText) findViewById(R.id.firstName);
        lastname = (EditText) findViewById(R.id.lastName);
        phone = (EditText) findViewById(R.id.phoneNumber);
        email = (EditText) findViewById(R.id.emailAddress);

        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            String firstName = (String) bd.get("Firstname");
            String lastName = (String) bd.get("Lastname");
            String phoneNumber = (String) bd.get("Phonenumber");
            String emailAddress = (String) bd.get("Emailaddress");
            firstname.setText(firstName);
            lastname.setText(lastName);
            phone.setText(phoneNumber);
            email.setText(emailAddress);

        }

        Button saveBtn = (Button) findViewById(R.id.btnSave);
        Button cancelBtn = (Button) findViewById(R.id.btnCancel);
        System.out.println(firstname.getText());
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("Firstname",firstname.getText().toString());
                intent.putExtra("Lastname",lastname.getText().toString());
                intent.putExtra("Phonenumber", phone.getText().toString());
                intent.putExtra("Emailaddress",email.getText().toString());
                //Toast.makeText(getApplicationContext(), firstname.getText().toString()  +  "  has been added to Contact list!", Toast.LENGTH_SHORT).show();
                setResult(2,intent);
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = getIntent();
                setResult(2,i);
                finish();
            }
        });





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
