package com.example.strongheart.gameoflife;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by strongheart on 6/12/17.
 */

public class GameMapAnimationLayout extends SurfaceView implements Runnable {
//    private String TAG = "PagerCanvasTop";

    Thread thread = null;
    boolean canDraw = false;


    Canvas canvas;
    SurfaceHolder surfaceHolder;
    String gameMapBackgroudColor = "#F4E6CC";

    List<Circle> circles;

    {
        surfaceHolder = getHolder();
        circles = new ArrayList<Circle>();
        //setBackgroundColor(Color.parseColor(gameMapBackgroudColor));
    }

    public GameMapAnimationLayout(Context context) {
        super(context);
    }

    public GameMapAnimationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameMapAnimationLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void run() {

        //prepPaintBrushes();

        while (canDraw) {

            if(!surfaceHolder.getSurface().isValid()) {
                continue;
            }

            canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.parseColor(gameMapBackgroudColor));



            drawClientCircles();

            Log.i("circlesNr", Integer.toString(circles.size()));

            surfaceHolder.unlockCanvasAndPost(canvas);

        }

    }

    public void pause() {

        canDraw = false;

        while (true) {
            try {
                thread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        thread = null;
    }

    public void resume() {
        canDraw = true;
        thread = new Thread(this);
        thread.start();
    }

//    private void prepPaintBrushes() {
//        red_paintbrush_fill = new Paint();
//        red_paintbrush_fill.setColor(Color.RED);
//        red_paintbrush_fill.setStyle(Paint.Style.FILL);
//    }
//
//    private void motionCircle(int speed) {
//        if ((circle_y == 130) && (circle_x < 650)) {
//            circle_x = circle_x + speed;
//        }
//
//        if ((circle_x == 650) && (circle_y < 650)) {
//            circle_y = circle_y + speed;
//        }
//
//        if ((circle_y == 650) && (circle_x > 130)) {
//            circle_x = circle_x - speed;
//        }
//
//        if ((circle_x == 130) && (circle_y > 130)) {
//            circle_y = circle_y - speed;
//        }
//    }

    public void generateClientCircle(float x, float y,
                        float radius, String color) {
        Circle newCircle = new Circle(x, y, radius, color);
        circles.add(newCircle);
//        Log.i("test", circles.size())
    }

    public void updateClientCircle(float x, float y, float radius, String color) {
        for(int i=0; i<circles.size(); ++i) {
            Log.i("change1", circles.get(i).getColor() + "^^^^" + color);
            if(circles.get(i).getColor().equals(color)) {
                circles.get(i).setX(x);
                circles.get(i).setY(y);
                circles.get(i).setRadius(radius);
                circles.get(i).setColor(color);
                Log.i("change2","yes");
                break;
            }
            Log.i("change3","no "+ Integer.toString(i) );
        }
    }

    public List<Circle> getClientCircles() {
        return circles;
    }

    public void deleteClientCircles() {
        circles.clear();
    }

    private void drawClientCircles() {
        Paint paintbrush_fill = new Paint();
        for(int i=0; i<circles.size(); ++i) {

            paintbrush_fill = new Paint();
            paintbrush_fill.setColor(Color.parseColor(circles.get(i).getColor()));
            paintbrush_fill.setStyle(Paint.Style.FILL);

            canvas.drawCircle(circles.get(i).getX(),
                    circles.get(i).getY(),
                    circles.get(i).getRadius(),
                    paintbrush_fill);
        }
    }



//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        this.canvas = canvas;
//        canvas.drawColor(Color.parseColor("#F4E6CC"));
//        super.onDraw(canvas);
//    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        this.canvas = canvas;
//        //canvas.drawColor(Color.RED);
//
//        Paint bluePaint = new Paint();
//        bluePaint.setColor(Color.BLUE);
//        bluePaint.setAntiAlias(true);
//
//        int screenWidth = this.getWidth();
//        int screenHeight = this.getHeight();
//
//        canvas.drawCircle(0, 0, 100, bluePaint);
//
//
//
//        super.onDraw(canvas);
//
//    }


}
