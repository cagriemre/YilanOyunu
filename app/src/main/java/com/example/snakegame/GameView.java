package com.example.snakegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.LogRecord;

public class GameView extends View {
    private Bitmap bmgrass1,bmgrass2,bmSnake,bmApple;
    public static int sizeOfMap = 75*Constants.SCREEN_WIDTH/1080;
    private int h = 21,w=12;
    private ArrayList<Grass> arrGrass = new ArrayList<>();
    private Snake snake;
    private boolean move=false;
    private float mx,my;
    private Handler handler;
    private Apple apple;
    private Runnable r;
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bmgrass1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.grass);
        bmgrass1 = Bitmap.createScaledBitmap(bmgrass1,sizeOfMap,sizeOfMap,true);
        bmgrass2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.grass03);
        bmgrass2 = Bitmap.createScaledBitmap(bmSnake,sizeOfMap,sizeOfMap,true);
        bmSnake = BitmapFactory.decodeResource(this.getResources(),R.drawable.snake1);
        bmSnake = Bitmap.createScaledBitmap(bmSnake,14*sizeOfMap,sizeOfMap,true);
        bmApple = BitmapFactory.decodeResource(this.getResources(),R.drawable.apple);
        bmApple = Bitmap.createScaledBitmap(bmApple,sizeOfMap,sizeOfMap,true);
        for (int i = 0; i<h; i++){
            for (int j= 0; j<w;j++){
                if (((i+j)&2)==0){
                    arrGrass.add(new Grass(bmgrass1,j*sizeOfMap+Constants.SCREEN_WIDTH/2-(w/2)*sizeOfMap,
                            i*sizeOfMap+100*Constants.SCREEN_HEIGHT/1920,sizeOfMap,sizeOfMap));

                }else{
                    arrGrass.add(new Grass(bmgrass2,j*sizeOfMap+Constants.SCREEN_WIDTH/2-(w/2)*sizeOfMap,
                            i*sizeOfMap+100*Constants.SCREEN_HEIGHT/1920,sizeOfMap,sizeOfMap));
                }
            }
        }
        snake = new Snake(bmSnake,arrGrass.get(126).getX(),arrGrass.get(126).getY(),4);
        apple = new Apple(bmApple,arrGrass.get(randomApple()[0]).getX(),arrGrass.get(randomApple()[1]).getY());
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
            invalidate();
            }
        };
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int a = event.getActionMasked();
        switch (a){
            case MotionEvent.ACTION_MOVE:{
                if (move==false){
                    mx = event.getX();
                    my = event.getY();
                    move = true;
                }else{
                    if (mx-event.getX()>100*Constants.SCREEN_WIDTH/1080&&!snake.isMoveRight()){
                        mx = event.getX();
                        my = event.getY();
                        snake.setMoveLeft(true);
                    }else if (event.getX()-mx>100*Constants.SCREEN_WIDTH/1080&&!snake.isMoveLeft()){
                        mx = event.getX();
                        my = event.getY();
                        snake.setMoveRight(true);
                    }else if (my-event.getY()>100*Constants.SCREEN_WIDTH/1080&&!snake.isMoveBottom()){
                        mx = event.getX();
                        my = event.getY();
                        snake.setMoveTop(true);
                    }else if (event.getX()-my>100*Constants.SCREEN_WIDTH/1080&&!snake.isMoveTop()){
                        mx = event.getX();
                        my = event.getY();
                        snake.setMoveBottom(true);
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:{
                mx = 0;
                my = 0;
                move = false;
                break;
            }
        }
        return true;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(0XFF1A6100);
        for (int i = 0;i<arrGrass.size(); i++){
            canvas.drawBitmap(arrGrass.get(i).getBm(),arrGrass.get(i).getX(),arrGrass.get(i).getY(),null);
        }
        snake.update();
        snake.draw(canvas);
        apple.draw(canvas);
        if (snake.getArrPartSnake().get(0).getrBody().intersect(apple.getR())){
            randomApple();
            apple.reset(arrGrass.get(randomApple()[0]).getX(),arrGrass.get(randomApple()[1]).getY());
            snake.addPart();
        }
        handler.postDelayed(r,100);
    }
    public int[] randomApple() {
        int[] xy = new int[2];
        Random r = new Random();
        xy[0] = r.nextInt(arrGrass.size() - 1);
        xy[1] = r.nextInt(arrGrass.size() - 1);
        Rect rect = new Rect(arrGrass.get(xy[0]).getX(), arrGrass.get(xy[1]).getY(), arrGrass.get(xy[0]).getX() + sizeOfMap, arrGrass.get(xy[1]).getY() + sizeOfMap);
        boolean check = true;
        while (check) {
            check = false;
            for (int i =0;i<snake.getArrPartSnake().size();i++){
                if (rect.intersect(snake.getArrPartSnake().get(i).getrBody())){
                    check = true;
                    xy[0] = r.nextInt(arrGrass.size()-1);
                    xy[1] = r.nextInt(arrGrass.size()-1);
                    rect = new Rect(arrGrass.get(xy[0]).getX(), arrGrass.get(xy[1]).getY(), arrGrass.get(xy[0]).getX() + sizeOfMap, arrGrass.get(xy[1]).getY() + sizeOfMap);
                }
            }

        }
    return xy;
    }










}
