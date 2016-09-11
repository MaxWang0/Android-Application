package com.example.fireman.sampleapp2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.List;

public class StudentErollment extends AppCompatActivity {

    Button can, sub;
    EditText name, email;
    ListView listvw;

    DataAccessFiles dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_erollment);

        sub = (Button) findViewById(R.id.button2);
        can = (Button) findViewById(R.id.button1);
        name = (EditText) findViewById(R.id.editText1);
        email = (EditText) findViewById(R.id.editText2);


        dob = new DataAccessFiles(getApplicationContext());
        dob.open();

        sub.setOnClickListener(new View.OnClickListener() {
            public void Onclick(View argo) {
                dob.insertValues(name.getText().toString(), email.getText().toString());

                Toast.makeText(getApplication(), "Entry Sved Successfully", Toast.LENGTH_LONG).show();
            }

        });


        ArrayList<studDtls> list = dob.GetTheList();
        ArrayList<String> names = new ArrayList<String>();
        for ( studDtls obj : list ){
           name.add(obj.getName());


        }
        listvw =  (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,);
        listvw.setAdapter(adapter);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_erollment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
