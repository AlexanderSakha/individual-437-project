package com.example.alexandersakha.finalproject;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Alexander Sakha on 4/3/2018.
 */

public class RectPlayer implements GameObject{

    private Rect rectangle;
    private int color;


    public RectPlayer(Rect rectangle, int color){
        this.rectangle = rectangle;
        this.color = color;
    }


    @Override
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
    }

    @Override
    public void update(){

    }

    public void update (Point point){ //new update method that creates a point and sets the rectangle with the point as a center
        int width = rectangle.width()/2;
        int height = rectangle.height()/2;
        rectangle.set(point.x - width, point.y - height, point.x + width, point.y + height);
    }

    public Rect getRectangle(){
        return rectangle;
    }
}
