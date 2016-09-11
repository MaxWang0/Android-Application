package com.example.fireman.sampleapp2;

import android.content.Intent;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Fireman on 2015/9/7.
 */
public class BasicApp extends AppCompatActivity{
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnAdd, btnSub, btnmul, btnDiv, btneq;
    TextView enterTxt, TxtResult;
    int op, var1, var2, result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_app);


        Intent currentIntent = getIntent();
        Toast.makeText(getApplicationContext(), currentIntent.getStringExtra(), )
        btn0 = (Button) findViewById(R.id.button14);
        btn1 = (Button) findViewById(R.id.button5);
        btn2 = (Button) findViewById(R.id.button8);
        btn3 = (Button) findViewById(R.id.button9);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button6);
        btn6 = (Button) findViewById(R.id.button7);
        btn7 = (Button) findViewById(R.id.button);
        btn8 = (Button) findViewById(R.id.button2);
        btn9 = (Button) findViewById(R.id.button3);
        enterTxt = (TextView)findViewById(R.id.editText);
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View argo0) {
                enterTxt.setText("" + "0");}});
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View argo0) {
                enterTxt.setText("" + "1");}});
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View argo0) {
                enterTxt.setText(enterTxt.getText() + "2");}});
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View argo0) {
                enterTxt.setText(enterTxt.getText() + "3");}});
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View argo0) {
                enterTxt.setText(enterTxt.getText() + "4");}});
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View argo0) {
                enterTxt.setText(enterTxt.getText() + "5");}});
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View argo0) {
                enterTxt.setText(enterTxt.getText() + "6");}});
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View argo0) {
                enterTxt.setText(enterTxt.getText() + "7");}});
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View argo0) {
                enterTxt.setText(enterTxt.getText() + "8");}});
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View argo0) {
                enterTxt.setText(enterTxt.getText() + "9");}});


        btnAdd = (Button) findViewById(R.id.button16);
        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View argo) {
                var1 = Integer.parseInt(enterTxt.getText().toString());
                enterTxt.setText("");
                op = 1;
            }
        });
        btnSub = (Button) findViewById(R.id.button12);
        btnSub.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View argo) {
                var1 = Integer.parseInt(enterTxt.getText().toString());
                enterTxt.setText("");
                op = 2;
            }
        });
        btneq = (Button) findViewById(R.id.button15);
        btneq.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View argo) {
                var2 = Integer.parseInt(enterTxt.getText().toString());
                switch (op){
                    case 1:
                        result = var1 + var2;
                        break;
                    case 2:
                        result = var1 - var2;
                        break;
                    case 3:
                        result = var1 * var2;
                        break;
                    case 4:
                        result = var1 / var2;
                        break;

                }
                enterTxt.setText(result + "");
            }
        });
        btnmul = (Button) findViewById(R.id.button11);
        btnmul.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View argo) {
                var1 = Integer.parseInt(enterTxt.getText().toString());
                enterTxt.setText("");
                op = 3;
            }
        });
        btnDiv = (Button) findViewById(R.id.button10);
        btnDiv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View argo) {
                var1 = Integer.parseInt(enterTxt.getText().toString());
                enterTxt.setText("");
                op = 4;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
