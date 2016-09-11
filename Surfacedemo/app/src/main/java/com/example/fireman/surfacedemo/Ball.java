package com.example.fireman.surfacedemo;

import android.graphics.RectF;

import java.util.Random;

/**
 * Created by Fireman on 2015/11/28.
 */
public class Ball {

    RectF rect;
    RectF rect_edge;
    RectF rect_topedge;
    float xVelocity;
    float yVelocity;
    float orixVelocity;
    float oriyVelocity;
    float ballWidth = 50;
    float ballHeight= 50;

    public Ball(int screenX, int screenY){

        // Start the ball travelling straight up at 100 pixels per second
        xVelocity = 600;
        yVelocity = -1200;
        orixVelocity = 600;
        oriyVelocity = -1200;

        // Place the ball in the centre of the screen at the bottom
        // Make it a 10 pixel x 10 pixel square
        rect = new RectF();
        rect_edge = new RectF();
        rect_topedge = new RectF();

    }

    public RectF getRect(){
        return rect;
    }

    public RectF getRect_edge(){
        return rect_edge;
    }

    public RectF getRect_topedge(){
        return rect_topedge;
    }

    public void resetspeed(float xSpeed, float ySpeed){
        xVelocity = xSpeed;
        yVelocity = ySpeed;

    }

    public float getXSpeed(){
        return orixVelocity;
    }

    public float getYSpeed(){
        return oriyVelocity;
    }

    public void update(long fps){
        rect.left = rect.left + (xVelocity / fps);
        rect.top = rect.top + (yVelocity / fps);
        rect.right = rect.left + ballWidth;
        rect.bottom = rect.top - ballHeight;

        rect_edge.left = rect_edge.left + (xVelocity / fps);
        rect_edge.top = rect_edge.top + (yVelocity / fps);
        rect_edge.right = rect_edge.left + ballWidth;
        rect_edge.bottom = rect_edge.top - 2;

        rect_topedge.left = rect_topedge.left + (xVelocity / fps);
        rect_topedge.top = rect_topedge.top + (yVelocity / fps);
        rect_topedge.right = rect_topedge.left + 4;
        rect_topedge.bottom = rect_topedge.top - 2;
    }

    public void resetposition(float x){

        rect.left = rect.left + x;
        rect.right = rect.left + ballWidth;

        rect_edge.left = rect_edge.left + x;
        rect_edge.right = rect_edge.left + ballWidth;

        rect_topedge.left = rect_topedge.left + x;
        rect_topedge.right = rect_topedge.left + 4;

    }

    public void resetdirection(float x, float y){
        xVelocity += x * 50;
        yVelocity -= y * 50;
    }

    public void reverseYVelocity(){
        yVelocity = - yVelocity;
        oriyVelocity = -oriyVelocity;
    }

    public void reverseXVelocity(){
        xVelocity = - xVelocity;
        orixVelocity = - orixVelocity;
    }

    public void setRandomXVelocity(){
        Random generator = new Random();
        int answer = generator.nextInt(2);

        if(answer == 0){
            reverseXVelocity();
        }
    }

    public void setRandomYVelocity(){

        Random generator = new Random();
        int answer = generator.nextInt(15);
        float degree = 83 + answer;
        float newdegree = (float) (180 - degree - Math.toDegrees(Math.atan(Math.abs(yVelocity)/Math.abs(xVelocity))));
        float x = (float) Math.cos(Math.toRadians(newdegree));
        yVelocity = (Math.abs(xVelocity) * x);

        oriyVelocity = 600 * x;

    }

    public void clearObstacleY(float y){
        rect.top = y;
        rect.bottom = y - ballHeight;
        rect_edge.top = y;
        rect_edge.bottom = y - 2;
        rect_topedge.top = y - ballHeight + 2;
        rect_topedge.bottom = y - ballHeight;
    }

    public void clearObstacleX(float x){
        rect.left = x;
        rect.right = x + ballWidth;

        rect_edge.left = x;
        rect_edge.right = x + ballWidth;

        rect_topedge.left = x + ballWidth/2 - 2;
        rect_topedge.right = x + ballWidth/2 + 2;
    }

    public void reset(int x, int y){
        rect.left = x / 2;
        rect.top = y - 20;
        rect.right = x / 2 + ballWidth;
        rect.bottom = y - 20 - ballHeight;

        rect_edge.left = x / 2;
        rect_edge.top = y - 20;
        rect_edge.right = x / 2 + ballWidth;
        rect_edge.bottom = y - 20 - 2;

        rect_topedge.left = x / 2 + ballWidth/2 - 2;
        rect_topedge.top = y - 20 - ballHeight + 2;
        rect_topedge.right = x / 2 + ballWidth/2 + 2;
        rect_topedge.bottom = y - 20 - ballHeight;
    }
}
