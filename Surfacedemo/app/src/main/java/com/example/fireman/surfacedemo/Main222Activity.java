package com.example.fireman.surfacedemo;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main222Activity extends Activity {

    Button SaveBtn;
    EditText PlayerName;
    TextView ScoreValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main222);

        SaveBtn = (Button) findViewById(R.id.saveBtn);
        PlayerName = (EditText) findViewById(R.id.editText);
        ScoreValue = (TextView) findViewById(R.id.textView2);

        Intent intent = getIntent();

        Bundle bd = intent.getExtras();



        if(bd != null)
        {
            int score = (Integer) bd.get("Score");
            String newscore = Integer.toString(score);
            ScoreValue.setText(newscore);

        }

        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start NewActivity.class

                Intent myIntent = new Intent(Main222Activity.this,
                        Main2Activity.class);
                Score currentScore = new Score(PlayerName.getText().toString(), Integer.parseInt(ScoreValue.getText().toString()));
                createContact(currentScore);
                startActivity(myIntent);
            }
        });


    }

    public void createContact(Score currentScore) {


        String file_name = "Scores.txt";
        String Message = currentScore.getName().toString()+","+Integer.toString(currentScore.getScore())+"\n";
        try {
            FileOutputStream fileOutputStream = openFileOutput(file_name, MODE_PRIVATE | MODE_APPEND);
            fileOutputStream.write(Message.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
