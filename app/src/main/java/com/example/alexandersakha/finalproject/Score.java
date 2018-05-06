package com.example.alexandersakha.finalproject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Alexander Sakha on 4/4/2018.
 */

public class Score implements GameObject {
    private int xLoc, yLoc, scaledSize;
    private String string;

    public Score(String string, int color, int scaledS, int xLoc, int yLoc) {
        this.string = string;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.scaledSize = scaledS;
        Paint paint = new Paint();
        paint.setTextSize(scaledSize);
        paint.setColor(color);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextSize(scaledSize);
        paint.setColor(Color.WHITE);
        canvas.drawText(string, xLoc, yLoc, paint);
    }

    public void update (String s){ //new update method that creates a point and sets the rectangle with the point as a center
        string = s;
    }
}
