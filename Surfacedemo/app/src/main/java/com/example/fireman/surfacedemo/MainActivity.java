/***
 * Breakout Game
 * Author: Yu Wang
 */


package com.example.fireman.surfacedemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.SeekBar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static android.widget.SeekBar.*;

public class MainActivity extends Activity implements Runnable, SensorEventListener {

    private Button swapBtn;
    private Button scoreBtn;
    private SeekBar seekBar;
    private SurfaceView surface;
    private SurfaceHolder holder;
    private boolean locker=true;
    List<Score> Scores = new ArrayList<Score>();

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private float lastX, lastY, lastZ;
    private float deltaX = 0;
    private float deltaY = 0;
    private float deltaZ = 0;

    private float vibrateThreshold = 0;
    public Vibrator v;



    // A boolean which we will set and unset
    // when the game is running- or not.
    volatile boolean playing;

    // Game is paused at the start
    boolean paused = true;

    // A Canvas and a Paint object
    Canvas canvas;
    Paint paint = new Paint();

    // This variable tracks the game frame rate
    long fps;
    long progress = 0;

    // This is used to help calculate the fps
    private long timeThisFrame;
    private long startTime = 0;

    // The size of the screen in pixels
    int screenX;
    int screenY;

    // The players paddle

    Paddle paddle;

    // A ball
    Ball ball;

    // Up to 200 bricks
    Brick[] bricks = new Brick[200];
    int numBricks = 0;

    // Set color
    Random rnd = new Random();
    int[] color1 = new int[24];
    int[] color2 = new int[24];
    int[] color3 = new int[24];

    // The score
    int score = 0;

    // Lives
    int lives = 3;

    private Thread thread;
    private int radiusBlack, radiusWhite;
    private boolean left = true;
    //physics
    private static final int baseRadius = 10;
    private static final int maxRadius = 50;
    private static final int baseSpeed = 1;
    private int speed = 0;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // success! we have an accelerometer
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            vibrateThreshold = accelerometer.getMaximumRange() / 2;
        } else {
            // fai! we dont have an accelerometer!
        }

        //initialize vibration
        v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);


        swapBtn = (Button) findViewById(R.id.buttonswap);
        scoreBtn = (Button) findViewById(R.id.scoreList);
        seekBar = (SeekBar) findViewById(R.id.seekBar);


        surface = (SurfaceView) findViewById(R.id.mysurface);
        holder = surface.getHolder();

        // Get a Display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();
        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);

        /**
        screenX = 924;
        screenY = 1404;
         screenX = 616;
         screenY = 936;
         **/
        screenX = 616;
        screenY = 936 ;

        paddle = new Paddle(screenX, screenY);

        // Create a ball
        ball = new Ball(screenX, screenY);

        //Set the random color
        for(int i = 0; i < 24; i++){
            color1[i] = rnd.nextInt(256);
            color2[i] = rnd.nextInt(256);
            color3[i] = rnd.nextInt(256);
        }

        thread = new Thread(this);
        thread.start();
        swapBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                /** SWAP ANIMATION LEFT OR RIGHT CIRCLE*/
                left = !left;
                paused = !paused;

            }
        });

        scoreBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        Main2Activity.class);
                startActivity(myIntent);
            }
        });

        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

                                               @Override
                                               public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                                                   progress = progressValue;
                                               }

                                               @Override
                                               public void onStartTrackingTouch(SeekBar seekBar) {
                                               }

                                               @Override
                                               public void onStopTrackingTouch(SeekBar seekBar) {
                                                   float xSpeed = ball.getXSpeed();
                                                   float ySpeed = ball.getYSpeed();
                                                   float proportion = Math.abs(ySpeed/xSpeed);
                                                   if(xSpeed > 0){
                                                       xSpeed += progress;
                                                   }else if(xSpeed < 0){
                                                       xSpeed -= progress;
                                                   }

                                                   if(ySpeed > 0){
                                                       ySpeed = Math.abs(xSpeed) * proportion;
                                                   }else if(ySpeed < 0){
                                                       ySpeed = - Math.abs(xSpeed) * proportion;
                                                   }
                                                   ball.resetspeed(xSpeed, ySpeed);

                                               }
                                           });



            createBricksAndRestart();
    }

    /**
    private void startActivity(View view) {
        Intent i = new Intent(this, Main2Activity.class);
        i.putExtra("Score", score);
        i.putExtra("Time", startTime);
        startActivity(i);
    }
     **/

    public void createBricksAndRestart(){

        startTime = 0;

        // Put the ball back to the start
        ball.reset(screenX, screenY);

        int brickWidth_1 = screenX / 9;
        int brickWidth_2 = screenX / 8;
        int brickWidth_3 = screenX / 7;
        int brickHeight = screenY / 12;

        // Build a wall of bricks
        numBricks = 0;


        for(int row = 0; row < 3; row ++ ){
            if(row == 0){
                for(int column = 0; column < 9; column ++ ) {
                    bricks[numBricks] = new Brick(row, column, brickWidth_1, brickHeight);
                    numBricks ++;
                }
            }else if(row == 1){
                for(int column = 0; column < 8; column ++ ) {
                    bricks[numBricks] = new Brick(row, column, brickWidth_2, brickHeight);
                    numBricks ++;
                }
            }else {
                for(int column = 0; column < 7; column ++ ) {
                    bricks[numBricks] = new Brick(row, column, brickWidth_3, brickHeight);
                    numBricks ++;
                }
            }

        }
        // Reset scores and lives
        score = 0;
        lives = 3;


    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(locker){

            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

            // Update the frame
            // Update the frame
            if(!paused){
                startTime += timeThisFrame;
                update();
            }

            //checks if the lockCanvas() method will be success,and if not, will check this statement again
            if(!holder.getSurface().isValid()){
                continue;
            }
            /** Start editing pixels in this surface.*/
            Canvas canvas = holder.lockCanvas();


            //ALL PAINT-JOB MAKE IN draw(canvas); method.
            draw(canvas);

            // End of painting to canvas. system will paint with this canvas,to the surface.
            holder.unlockCanvasAndPost(canvas);

            // Calculate the fps this frame
            // We can then use the result to
            // time animations and more.
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 15000 / timeThisFrame;
            }
        }
    }


    // Everything that needs to be updated goes in here
    // Movement, collision detection etc.
    public void update() {

        // Move the paddle if required
        paddle.update(fps);

        //ball.resetspeed(progress);

        ball.update(fps);

        ball.resetdirection(deltaX, deltaY);

        // Check for ball colliding with a brick
        for(int i = 0; i < numBricks; i++){

            if (bricks[i].getVisibility()){

                if(RectF.intersects(bricks[i].getRect(), ball.getRect_topedge())) {
                    bricks[i].setHitnum();
                    if(bricks[i].getHitnum() == 0){
                        bricks[i].setInvisible();
                    }

                    //ball.reverseYVelocity();
                    ball.setRandomYVelocity();
                    ball.clearObstacleY(bricks[i].getRect().bottom + ball.ballHeight);
                    score = score + 10;

                }
            }
        }

        // Check for ball colliding with paddle
        if(RectF.intersects(paddle.getRect(),ball.getRect_edge())) {
            ball.setRandomYVelocity();
            ball.reverseYVelocity();
            ball.clearObstacleY(paddle.getRect().top - 2);

        }

        // Bounce the ball back when it hits the bottom of screen
        if(ball.getRect().bottom > screenY){
            ball.reverseYVelocity();
            ball.clearObstacleY(screenY - 2);

            // Lose a life
            lives --;

            if(lives == 0){
                paused = true;
                Intent myIntent = new Intent(MainActivity.this,
                        Main222Activity.class);
                int lowscore = 0;

                if (getContactCount() != 0){
                    int index;
                    Scores.addAll(getAllScores());
                    Collections.sort(Scores, Score.ConNameComparator);
                    if((index = getContactCount()) < 10){
                        myIntent.putExtra("Score", score);
                        startActivity(myIntent);
                    }else{
                        lowscore = Scores.get(9).getScore();
                        if(score > lowscore){
                            myIntent.putExtra("Score", score);
                            startActivity(myIntent);
                        }
                    }
                }else{
                    myIntent.putExtra("Score", score);
                    startActivity(myIntent);
                }

                createBricksAndRestart();

                /**
                if (getContactCount() != 0){
                    int index;
                    Scores.addAll(getAllScores());
                    Collections.sort(Scores, Score.ConNameComparator);
                    if((index = getContactCount()) < 10){
                        lowscore = Scores.get(index).getScore();

                        myIntent.putExtra("Score", score);
                        startActivity(myIntent);
                        createBricksAndRestart();
                    }else{
                        lowscore = Scores.get(10).getScore();
                        if(score > lowscore){
                            myIntent.putExtra("Score", score);
                            startActivity(myIntent);
                            createBricksAndRestart();
                        }

                    }
                }
                 **/



            }
        }

        // Bounce the ball back when it hits the top of screen
        if(ball.getRect().top < 0){
            ball.reverseYVelocity();
            ball.clearObstacleY(12);

        }

        // If the ball hits left wall bounce
        if(ball.getRect().left < 0){
            ball.reverseXVelocity();
            ball.clearObstacleX(2);

        }

        // If the ball hits right wall bounce
        if(ball.getRect().right > screenX){
            ball.reverseXVelocity();
            ball.clearObstacleX(screenX - 52);

        }

    }


    /**This method deals with paint-works. Also will paint something in background*/
    private void draw(Canvas canvas) {
        // paint a background color
        canvas.drawColor(Color.argb(255, 128, 128, 128));


        // Choose the brush color for drawing
        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setTextSize(40);

        // Draw the paddle
        canvas.drawRect(paddle.getRect(), paint);

        // Draw the ball
        canvas.drawOval(ball.getRect(), paint);

        // Draw the bricks
        // Change the brush color for drawing
        paint.setColor(Color.argb(255,  249, 129, 0));

        // Draw the bricks if visible
        for(int i = 0; i < numBricks; i++){
            if(bricks[i].getHitnum() == 5){
                paint.setColor(Color.argb(255,  255, 255, 255));
            }else if(bricks[i].getHitnum() == 4){
                paint.setColor(Color.argb(255, 000, 000, 255));
            }else if(bricks[i].getHitnum() == 3){
                paint.setColor(Color.argb(255, 000, 255, 000));
            }else if(bricks[i].getHitnum() == 2){
                paint.setColor(Color.argb(255, 255, 000, 000));
            }else if(bricks[i].getHitnum() == 1){
                paint.setColor(Color.argb(150, 000, 000, 000));
            }
            if(bricks[i].getVisibility()) {
                canvas.drawRect(bricks[i].getRect(), paint);
                paint.setColor(Color.argb(255, color1[i], color2[i], color3[i]));
            }
        }

        // Draw the HUD
        // Draw the HUD
        // Choose the brush color for drawing
        paint.setColor(Color.argb(255,  255, 255, 255));

        // Draw the score
        paint.setTextSize(40);
        canvas.drawText("Score: " + score + "   Lives: " + lives + " Time: " + startTime/1000, 10,50, paint);

        // Has the player cleared the screen?
        if(score == numBricks * 60){
            paint.setTextSize(90);
            canvas.drawText("YOU HAVE WON!", 10,screenY/2, paint);
        }

        // Has the player lost?
        if(lives <= 0){
            paint.setTextSize(90);
            canvas.drawText("YOU HAVE LOST!", 10,screenY/2, paint);
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





    private void calculateRadiuses() {
        // TODO Auto-generated method stub
        if(left){
            updateSpeed(radiusBlack);
            radiusBlack += speed;
            radiusWhite = baseRadius;
        }
        else{
            updateSpeed(radiusWhite);
            radiusWhite += speed;
            radiusBlack = baseRadius;
        }
    }
    /**Change speed according to current radius size.
     * if, radius is bigger than maxRad the speed became negative otherwise
     * if radius is smaller then baseRad speed will positive.
     * @param radius
     */
    private void updateSpeed(int radius) {
        // TODO Auto-generated method stub
        if(radius>=maxRadius){
            speed = -baseSpeed;
        }
        else if (radius<=baseRadius){
            speed = baseSpeed;
        }

    }


    // The SurfaceView class implements onTouchListener
    // So we can override this method and detect screen touches.
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:

                paused = false;

                if(motionEvent.getX() > screenX / 2){
                    paddle.setMovementState(paddle.RIGHT);
                }
                else{
                    paddle.setMovementState(paddle.LEFT);
                }

                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:

                paddle.setMovementState(paddle.STOPPED);
                break;
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        pause();
    }

    private void pause() {
        //CLOSE LOCKER FOR run();
        locker = false;
        while(true){
            try {
                //WAIT UNTIL THREAD DIE, THEN EXIT WHILE LOOP AND RELEASE a thread
                thread.join();
            } catch (InterruptedException e) {e.printStackTrace();
            }
            break;
        }
        thread = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        resume();
    }

    private void resume() {
        //RESTART THREAD AND OPEN LOCKER FOR run();
        locker = true;

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // display the current x,y,z accelerometer values
        displayCurrentValues();

        // get the change of the x,y,z values of the accelerometer
        deltaX = lastX - event.values[0];
        deltaY = lastY - event.values[1];
        deltaZ = Math.abs(lastZ - event.values[2]);

        // if the change is below 2, it is just plain noise
        if (Math.abs(deltaX) < 2)
            deltaX = 0;
        if (Math.abs(deltaY) < 2)
            deltaY = 0;
        if (deltaZ < 2)
        deltaZ = 0;

        // set the last know values of x,y,z
        lastX = event.values[0];
        lastY = event.values[1];
        lastZ = event.values[2];
        vibrate();



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // display the current x,y,z accelerometer values
    public void displayCurrentValues() {


    }

    // if the change in the accelerometer value is big enough, then vibrate!
    // our threshold is MaxValue/2
    public void vibrate() {
        if ((deltaX > vibrateThreshold) || (deltaY > vibrateThreshold) || (deltaZ > vibrateThreshold)) {
            v.vibrate(50);
        }
    }

}
