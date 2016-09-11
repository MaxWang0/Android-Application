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

public class Edit extends AppCompatActivity {

    EditText firstnameTxt, lastnameTxt, phoneTxt, emailTxt;
    private String nameDefaultValue = "Your Name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firstnameTxt = (EditText)findViewById(R.id.txtFirstName);
        lastnameTxt = (EditText) findViewById(R.id.txtLastName);
        phoneTxt = (EditText) findViewById(R.id.txtPhoneNumber);
        emailTxt = (EditText) findViewById(R.id.txtEmailAddress);
        lastnameTxt.setText(nameDefaultValue);
        /**
        //Button cfmBtn = (Button) findViewById(R.id.btnCfm);
        //Button canBtn = (Button) findViewById(R.id.btnCan);

        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            String firstName = (String) bd.get("Firstname");
            String lastName = (String) bd.get("Lastname");
            String phoneNumber = (String) bd.get("Phonenumber");
            String emailAddress = (String) bd.get("Emailaddress");
            firstnameTxt.setText(firstName);
            lastnameTxt.setText(lastName);
            phoneTxt.setText(phoneNumber);
            emailTxt.setText(emailAddress);

        }

        cfmBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), firstnameTxt.getText() + " has been edited!", Toast.LENGTH_SHORT).show();
            }
        });
         **/


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
