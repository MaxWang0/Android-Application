package com.example.fireman.surfacedemo;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main2Activity extends Activity {
    ListView listView ;
    ArrayAdapter<Score> scoreAdapter;
    Button ReturnBtn;
    List<Score> Scores = new ArrayList<Score>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.listView);
        ReturnBtn = (Button) findViewById(R.id.returnBtn);

        ReturnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start NewActivity.class
                Intent myIntent = new Intent(Main2Activity.this,
                        MainActivity.class);
                startActivity(myIntent);
            }
        });


        // Defined Array values to show in ListView
        String[] values = new String[] { "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data



        if (getContactCount() != 0){
            Scores.addAll(getAllScores());
        }



        Collections.sort(Scores, Score.ConNameComparator);
        populateList();

        /**
        ArrayAdapter<Score> adapter = new ArrayAdapter<Score>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, Scores);
         **/

        // Assign adapter to ListView
        //listView.setAdapter(adapter);
    }


    private void populateList() {
        scoreAdapter = new ContactListAdapter();
        listView.setAdapter(scoreAdapter);
    }



    private class ContactListAdapter extends ArrayAdapter<Score> {
        public ContactListAdapter() {
            super (Main2Activity.this, R.layout.listview_item, Scores);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);

            Score currentContact = Scores.get(position);

            TextView name = (TextView) view.findViewById(R.id.name);
            name.setText(currentContact.getName());
            TextView phone = (TextView) view.findViewById(R.id.score);
            phone.setText(Integer.toString(currentContact.getScore()));

            return view;
        }
    }

    public List<Score> getAllScores() {
        List<Score> scores = new ArrayList<Score>();

        try {
            String inputMessage;
            FileInputStream fileInputStream = openFileInput("Scores.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((inputMessage = bufferedReader.readLine())!= null){
                String[] line = inputMessage.split(",");
                scores.add(new Score(line[0], Integer.parseInt(line[1])));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return scores;
    }




    public int getContactCount() {
        int n = 0;

        try {
            FileInputStream fileInputStream = openFileInput("Scores.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while (bufferedReader.readLine()!= null){
                n++;
            }

            //Toast.makeText(getApplicationContext(),  n +  " has been added to Contact list!", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return n;
    }




}
