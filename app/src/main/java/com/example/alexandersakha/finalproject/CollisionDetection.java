package com.example.alexandersakha.finalproject;

import android.graphics.Rect;

/**
 * Created by Alexander Sakha on 4/5/2018.
 */

public class CollisionDetection {
    private Rect rectangle1, rectangle2;

    public CollisionDetection(Rect rectangle1, Rect rectangle2){
        this.rectangle1 = rectangle1;
        this.rectangle2 = rectangle2;
    }

    public boolean collisionDetected (){
        return Rect.intersects(rectangle1, rectangle2);
    }
}
