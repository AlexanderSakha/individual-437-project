package com.example.alexandersakha.finalproject;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Alexander Sakha on 4/3/2018.
 */

public class Ball implements GameObject {
    private Rect rectangle;
    private int color;
    public int xVel, yVel, xLoc, yLoc;

    public Ball(Rect rectangle, int color, int xVel, int yVel) {
        this.rectangle = rectangle;
        this.color = color;
        this.xVel = xVel;
        this.yVel = yVel;
    }

    @Override
    public void update() {
    }

    public void update (Point point, int screenWidth, int screenHeight, boolean collisionDetected){ //new update method that creates a point and sets the rectangle with the point as a center
        int width = rectangle.width()/2;
        int height = rectangle.height()/2;

        if ((point.x > screenWidth - width) || (point.x < width)) {
            xVel = xVel * -1;
        }
        if ((point.y > screenHeight - height) || (point.y < height)) {
            yVel = yVel * -1;
        }
        if (collisionDetected){
            yVel = yVel * -1;
        }
        point.x += xVel;
        point.y += yVel;
        xLoc = point.x;
        yLoc = point.y;
        rectangle.set(point.x - width, point.y - height, point.x + width, point.y + height);
    }

    public int getxLoc(){
        return xLoc;
    }

    public int getyLoc(){
        return yLoc;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
    }

    public Rect getRectangle(){
        return rectangle;
    }
}
