package com.example.fireman.breakoutgame;

/**
 * Created by Fireman on 2015/11/26.
 */

import android.graphics.RectF;

public class Brick {

    private RectF rect;

    private boolean isVisible;

    private int hitnum = 6;

    public Brick(int row, int column, int width, int height){

        isVisible = true;

        int padding = 1;

        rect = new RectF(column * width + padding,
                row * height + padding,
                column * width + width - padding,
                row * height + height - padding);
    }

    public RectF getRect(){
        return this.rect;
    }

    public void setHitnum() {
        hitnum--;
    }
    public int getHitnum() {
        return hitnum;
    }

    public void setInvisible(){
        isVisible = false;
    }

    public boolean getVisibility(){
        return isVisible;
    }
}
