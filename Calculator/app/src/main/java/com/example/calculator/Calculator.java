package com.example.calculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Calculator extends ActionBarActivity {

	TextView answer;
    Button one, two, three, four, five, six, seven,
    	eight, nine, zero, plus, minus, multiply, divide, equals;
    int value;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_layout);
    
    one=(Button)findViewById(R.id.button1);
    two=(Button)findViewById(R.id.button2);
    three=(Button)findViewById(R.id.button3);
    four=(Button)findViewById(R.id.button4);
    five=(Button)findViewById(R.id.button5);
    six=(Button)findViewById(R.id.button6);
    seven=(Button)findViewById(R.id.button7);
    eight=(Button)findViewById(R.id.button8);
    nine=(Button)findViewById(R.id.button9);
    zero=(Button)findViewById(R.id.button10);
    plus=(Button)findViewById(R.id.button11);
    minus=(Button)findViewById(R.id.button12);
    multiply=(Button)findViewById(R.id.button13);
    divide=(Button)findViewById(R.id.button14);
    equals=(Button)findViewById(R.id.button15);
    answer = (TextView)findViewById(R.id.textView1);
    
    
    value=0;
    
 one.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		answer.setText(answer.getText().toString()+"1");
	}
});
 
 two.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			answer.setText(answer.getText().toString()+"2");
		}
	});
 three.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			answer.setText(answer.getText().toString()+"3");
		}
	});
 four.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			answer.setText(answer.getText().toString()+"4");
		}
	});
 five.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			answer.setText(answer.getText().toString()+"5");
		}
	});
 six.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			answer.setText(answer.getText().toString()+"6");
		}
	});
 seven.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			answer.setText(answer.getText().toString()+"7");
		}
	});
 eight.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			answer.setText(answer.getText().toString()+"8");
		}
	});
 nine.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			answer.setText(answer.getText().toString()+"9");
		}
	});
 zero.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			answer.setText(answer.getText().toString()+"0");
		}
	});
 
 
 
 plus.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		value=value+Integer.parseInt(answer.getText().toString());
		
		
		
		}
		});
 minus.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			value=value-Integer.parseInt(answer.getText().toString());
			
		
		
		}
		});
 multiply.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			value=value*Integer.parseInt(answer.getText().toString());
		}
		});
 divide.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			value=value/Integer.parseInt(answer.getText().toString());
		}
		});
	
 equals.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			answer.setText(String.valueOf(value));
		}
		});
	
    
    
    
    
    
    
    
    };

 
 
 
 
 
    
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
