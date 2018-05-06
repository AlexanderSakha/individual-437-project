package com.example.alexandersakha.finalproject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by Alexander Sakha on 4/3/2018.
 * Updated on 5/4/2018
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {
    private MainThread thread;
    private RectPlayer player1, player2;
    private Point playerPoint1, playerPoint2, ballPoint, ballPointOld;
    private Ball ball;
    private Score score1, score2;
    private CollisionDetection CD1, CD2;

    private int screenW = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenH = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int scoreCounter1 = 0, scoreCounter2 = 0;
    private int xPos = screenW / 2;
    private float xmax, width;

    private boolean lock = false, lock1 = false;


    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);

        player1 = new RectPlayer(new Rect(screenW/5, 2*screenH/25, 3*screenW/5, 3*screenH/25), Color.WHITE);
        player2 = new RectPlayer(new Rect(screenW/5, 22*screenH/25, 3*screenW/5, 23*screenH/25), Color.WHITE);
        ball = new Ball(new Rect(5*screenW/15, 5*screenW/15, 6*screenW/15, 6*screenW/15), Color.WHITE, screenW/200, screenW/100);
        playerPoint1 = new Point(screenW/2, 2*screenH/25);
        playerPoint2 = new Point(screenW/2, 23*screenH/25);
        ballPoint = new Point(screenW/2, screenH/2);
        ballPointOld = new Point(ballPoint);
        int scaledSize = getResources().getDimensionPixelSize(R.dimen.myFontSize);
        score1 = new Score("0", Color.WHITE, scaledSize, screenW / 2, screenH/25);
        score2 = new Score("0", Color.WHITE, scaledSize, screenW / 2, screenH);


        CD1 =  new CollisionDetection(player1.getRectangle(), ball.getRectangle());
        CD2 =  new CollisionDetection(player2.getRectangle(), ball.getRectangle());

        SensorManager SM = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        assert SM != null;
        Sensor mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_GAME);

        assert ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)) != null;
        Display  display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        width = screenW/5;
        xmax = (float)display.getWidth() - width;

        setFocusable(true);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (true) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        xPos = xPos - (int) sensorEvent.values[0]*10;
        if (xPos > xmax) {
            xPos = (int)xmax;
        } else if (xPos < width) {
            xPos = (int) width;
        }

        playerPoint1.set(xPos, 2*screenH/25);
        Log.d("point1", Integer.toString(playerPoint1.x));
    }


    public void setPlayer2Point(int x2){
        int point=0;
        int mod = x2%(int)(xmax - width);
        int mod2 = x2%(2*(int)(xmax - width));
        if (mod == mod2){
            point = (int) width + (int) mod;
        }else if(mod < mod2){
            point = (int) xmax - (int) mod;
        }

        playerPoint2.x = point;
        Log.d("point2", Integer.toString(playerPoint2.x));
    }

    public void update() {
        player1.update(playerPoint1);
        player2.update(playerPoint2);
        ball.update(ballPoint, screenW, screenH, (CD1.collisionDetected() || CD2.collisionDetected()));
        if ((ball.getyLoc() > 23*screenH/25) && !lock) {
            scoreCounter1++;
            score1.update(Integer.toString(scoreCounter1));
            lock = !lock;
        }else if((ball.getyLoc() < 23*screenH/25)){
            lock = !lock;
        }
        if ((ball.getyLoc() < 2*screenH/25) && !lock1){
            scoreCounter2++;
            score2.update(Integer.toString(scoreCounter2));
            lock1 = !lock1;
        }else if((ball.getyLoc() > 2*screenH/25))
        {
            lock1 = !lock1;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.BLACK);

        player1.draw(canvas);

        player2.draw(canvas);

        ball.draw(canvas);

        score1.draw(canvas);
        score2.draw(canvas);
    }

}
