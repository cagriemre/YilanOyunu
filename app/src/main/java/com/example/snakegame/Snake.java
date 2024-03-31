package com.example.snakegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Snake {
    private boolean moveLeft,moveRight,moveTop,moveBottom;
    private Bitmap bm,bmHeadUp,bmHeadDown,bmHeadLeft,bmHeadRight,bmBodyVertical,bmBodyHorizontal,
    bmBodyTopRight,bmBodyTopLeft,bmBodyBottomRight,bmBodyBottomLeft,bmTailRight,bmTailLeft,bmTailUp,bmTailDown;

    private int x,y,lenght;
    private ArrayList<PartSnake> arrPartSnake = new ArrayList<>();

    public Snake(Bitmap bm, int x, int y, int lenght) {
        this.bm = bm;
        this.x = x;
        this.y = y;
        this.lenght = lenght;
        bmBodyBottomLeft = Bitmap.createBitmap(bm,0,0,GameView.sizeOfMap,GameView.sizeOfMap);
        bmBodyBottomRight = Bitmap.createBitmap(bm,GameView.sizeOfMap,0,GameView.sizeOfMap,GameView.sizeOfMap);
        bmBodyHorizontal = Bitmap.createBitmap(bm,2*GameView.sizeOfMap,0,GameView.sizeOfMap,GameView.sizeOfMap);
        bmBodyTopLeft = Bitmap.createBitmap(bm,3*GameView.sizeOfMap,0,GameView.sizeOfMap,GameView.sizeOfMap);
        bmBodyTopRight = Bitmap.createBitmap(bm,4*GameView.sizeOfMap,0,GameView.sizeOfMap,GameView.sizeOfMap);
        bmBodyVertical = Bitmap.createBitmap(bm,5*GameView.sizeOfMap,0,GameView.sizeOfMap,GameView.sizeOfMap);
        bmHeadDown = Bitmap.createBitmap(bm,6*GameView.sizeOfMap,0,GameView.sizeOfMap,GameView.sizeOfMap);
        bmHeadLeft = Bitmap.createBitmap(bm,7*GameView.sizeOfMap,0,GameView.sizeOfMap,GameView.sizeOfMap);
        bmHeadRight = Bitmap.createBitmap(bm,8*GameView.sizeOfMap,0,GameView.sizeOfMap,GameView.sizeOfMap);
        bmHeadUp = Bitmap.createBitmap(bm,9*GameView.sizeOfMap,0,GameView.sizeOfMap,GameView.sizeOfMap);
        bmTailUp = Bitmap.createBitmap(bm,10*GameView.sizeOfMap,0,GameView.sizeOfMap,GameView.sizeOfMap);
        bmTailRight = Bitmap.createBitmap(bm,11*GameView.sizeOfMap,0,GameView.sizeOfMap,GameView.sizeOfMap);
        bmTailLeft = Bitmap.createBitmap(bm,12*GameView.sizeOfMap,0,GameView.sizeOfMap,GameView.sizeOfMap);
        bmTailDown = Bitmap.createBitmap(bm,13*GameView.sizeOfMap,0,GameView.sizeOfMap,GameView.sizeOfMap);
        arrPartSnake.add(new PartSnake(bmHeadRight,x,y));
        for (int i = 1;i<lenght -1;i++){
            arrPartSnake.add(new PartSnake(bmBodyHorizontal,arrPartSnake.get(i-1).getX() - GameView.sizeOfMap,y));
        }
        arrPartSnake.add(new PartSnake(bmTailRight,arrPartSnake.get(lenght-2).getX() - GameView.sizeOfMap,y));
        setMoveRight(true);

    }
    public void update(){
        for(int i=lenght -1;i>0;i--){
            arrPartSnake.get(i).setX(arrPartSnake.get(i-1).getX());
            arrPartSnake.get(i).setY(arrPartSnake.get(i-1).getY());
        }
        if(moveRight){
            arrPartSnake.get(0).setX(arrPartSnake.get(0).getX()+GameView.sizeOfMap);
            arrPartSnake.get(0).setBm(bmHeadRight);
        }else if(moveLeft){
            arrPartSnake.get(0).setX(arrPartSnake.get(0).getX()-GameView.sizeOfMap);
            arrPartSnake.get(0).setBm(bmHeadLeft);
        }else if(moveTop){
            arrPartSnake.get(0).setY(arrPartSnake.get(0).getY()-GameView.sizeOfMap);
            arrPartSnake.get(0).setBm(bmHeadUp);
        }else if(moveBottom){
            arrPartSnake.get(0).setY(arrPartSnake.get(0).getY()+GameView.sizeOfMap);
            arrPartSnake.get(0).setBm(bmHeadDown);
        }
        for (int i=1;i<lenght - 1 ;i++){
            if (arrPartSnake.get(i).getrLeft().intersect(arrPartSnake.get(i+1).getrBody())
            &&arrPartSnake.get(i).getrBottom().intersect(arrPartSnake.get(i-1).getrBody())
            ||arrPartSnake.get(i).getrLeft().intersect(arrPartSnake.get(i-1).getrBody())
                    &&arrPartSnake.get(i).getrBottom().intersect(arrPartSnake.get(i+1).getrBody())){
                arrPartSnake.get(i).setBm(bmBodyBottomLeft);
            }else if (arrPartSnake.get(i).getrRight().intersect(arrPartSnake.get(i+1).getrBody())
                    &&arrPartSnake.get(i).getrBottom().intersect(arrPartSnake.get(i-1).getrBody())
                    ||arrPartSnake.get(i).getrRight().intersect(arrPartSnake.get(i-1).getrBody())
                    &&arrPartSnake.get(i).getrBottom().intersect(arrPartSnake.get(i+1).getrBody())){
                arrPartSnake.get(i).setBm(bmBodyBottomRight);
            }else if (arrPartSnake.get(i).getrLeft().intersect(arrPartSnake.get(i+1).getrBody())
                    &&arrPartSnake.get(i).getrTop().intersect(arrPartSnake.get(i-1).getrBody())
                    ||arrPartSnake.get(i).getrLeft().intersect(arrPartSnake.get(i-1).getrBody())
                    &&arrPartSnake.get(i).getrTop().intersect(arrPartSnake.get(i+1).getrBody())){
                arrPartSnake.get(i).setBm(bmBodyTopLeft);
            }else if (arrPartSnake.get(i).getrRight().intersect(arrPartSnake.get(i+1).getrBody())
                    &&arrPartSnake.get(i).getrTop().intersect(arrPartSnake.get(i-1).getrBody())
                    ||arrPartSnake.get(i).getrRight().intersect(arrPartSnake.get(i-1).getrBody())
                    &&arrPartSnake.get(i).getrTop().intersect(arrPartSnake.get(i+1).getrBody())){
                arrPartSnake.get(i).setBm(bmBodyTopRight);
            }else if (arrPartSnake.get(i).getrTop().intersect(arrPartSnake.get(i+1).getrBody())
                    &&arrPartSnake.get(i).getrBottom().intersect(arrPartSnake.get(i-1).getrBody())
                    ||arrPartSnake.get(i).getrTop().intersect(arrPartSnake.get(i-1).getrBody())
                    &&arrPartSnake.get(i).getrBottom().intersect(arrPartSnake.get(i+1).getrBody())){
                arrPartSnake.get(i).setBm(bmBodyVertical);
            }else if (arrPartSnake.get(i).getrLeft().intersect(arrPartSnake.get(i+1).getrBody())
                    &&arrPartSnake.get(i).getrRight().intersect(arrPartSnake.get(i-1).getrBody())
                    ||arrPartSnake.get(i).getrLeft().intersect(arrPartSnake.get(i-1).getrBody())
                    &&arrPartSnake.get(i).getrRight().intersect(arrPartSnake.get(i+1).getrBody())){
                arrPartSnake.get(i).setBm(bmBodyHorizontal);
            }
        }

        if (arrPartSnake.get(lenght-1).getrRight().intersect(arrPartSnake.get(lenght-2).getrBody())){
            arrPartSnake.get(lenght-1).setBm(bmTailRight);
        }else if (arrPartSnake.get(lenght-1).getrLeft().intersect(arrPartSnake.get(lenght-2).getrBody())){
            arrPartSnake.get(lenght-1).setBm(bmTailLeft);
        }else if (arrPartSnake.get(lenght-1).getrTop().intersect(arrPartSnake.get(lenght-2).getrBody())){
            arrPartSnake.get(lenght-1).setBm(bmTailUp);
        }else if (arrPartSnake.get(lenght-1).getrBottom().intersect(arrPartSnake.get(lenght-2).getrBody())){
            arrPartSnake.get(lenght-1).setBm(bmTailDown);
        }
    }

    public Bitmap getBm() {
        return bm;
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }

    public Bitmap getBmHeadUp() {
        return bmHeadUp;
    }

    public void setBmHeadUp(Bitmap bmHeadUp) {
        this.bmHeadUp = bmHeadUp;
    }

    public Bitmap getBmHeadDown() {
        return bmHeadDown;
    }

    public void setBmHeadDown(Bitmap bmHeadDown) {
        this.bmHeadDown = bmHeadDown;
    }

    public Bitmap getBmHeadLeft() {
        return bmHeadLeft;
    }

    public void setBmHeadLeft(Bitmap bmHeadLeft) {
        this.bmHeadLeft = bmHeadLeft;
    }

    public Bitmap getBmHeadRight() {
        return bmHeadRight;
    }

    public void setBmHeadRight(Bitmap bmHeadRight) {
        this.bmHeadRight = bmHeadRight;
    }

    public Bitmap getBmBodyVertical() {
        return bmBodyVertical;
    }

    public void setBmBodyVertical(Bitmap bmBodyVertical) {
        this.bmBodyVertical = bmBodyVertical;
    }

    public Bitmap getBmBodyHorizontal() {
        return bmBodyHorizontal;
    }

    public void setBmBodyHorizontal(Bitmap bmBodyHorizontal) {
        this.bmBodyHorizontal = bmBodyHorizontal;
    }

    public Bitmap getBmBodyTopRight() {
        return bmBodyTopRight;
    }

    public void setBmBodyTopRight(Bitmap bmBodyTopRight) {
        this.bmBodyTopRight = bmBodyTopRight;
    }

    public Bitmap getBmBodyTopLeft() {
        return bmBodyTopLeft;
    }

    public void setBmBodyTopLeft(Bitmap bmBodyTopLeft) {
        this.bmBodyTopLeft = bmBodyTopLeft;
    }

    public Bitmap getBmBodyBottomRight() {
        return bmBodyBottomRight;
    }

    public void setBmBodyBottomRight(Bitmap bmBodyBottomRight) {
        this.bmBodyBottomRight = bmBodyBottomRight;
    }

    public Bitmap getBmBodyBottomLeft() {
        return bmBodyBottomLeft;
    }

    public void setBmBodyBottomLeft(Bitmap bmBodyBottomLeft) {
        this.bmBodyBottomLeft = bmBodyBottomLeft;
    }

    public Bitmap getBmTailRight() {
        return bmTailRight;
    }

    public void setBmTailRight(Bitmap bmTailRight) {
        this.bmTailRight = bmTailRight;
    }

    public Bitmap getBmTailLeft() {
        return bmTailLeft;
    }

    public void setBmTailLeft(Bitmap bmTailLeft) {
        this.bmTailLeft = bmTailLeft;
    }

    public Bitmap getBmTailUp() {
        return bmTailUp;
    }

    public void setBmTailUp(Bitmap bmTailUp) {
        this.bmTailUp = bmTailUp;
    }

    public Bitmap getBmTailDown() {
        return bmTailDown;
    }

    public void setBmTailDown(Bitmap bmTailDown) {
        this.bmTailDown = bmTailDown;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public ArrayList<PartSnake> getArrPartSnake() {
        return arrPartSnake;
    }

    public void setArrPartSnake(ArrayList<PartSnake> arrPartSnake) {
        this.arrPartSnake = arrPartSnake;
    }

    public void draw(Canvas canvas) {
        for (int i=0;i<lenght;i++){
            canvas.drawBitmap(arrPartSnake.get(i).getBm(),arrPartSnake.get(i).getX(),arrPartSnake.get(i).getY(),null);
        }
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        s();
        this.moveLeft = moveLeft;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public void setMoveRight(boolean moveRight) {
        s();
        this.moveRight = moveRight;
    }

    public boolean isMoveTop() {
        return moveTop;
    }

    public void setMoveTop(boolean moveTop) {
        s();
        this.moveTop = moveTop;
    }

    public boolean isMoveBottom() {
        return moveBottom;
    }

    public void setMoveBottom(boolean moveBottom) {
        s();
        this.moveBottom = moveBottom;
    }
    public void s(){
        this.moveLeft=false;
        this.moveBottom=false;
        this.moveRight=false;
        this.moveTop=false;
    }

    public void addPart() {
        PartSnake p = this.arrPartSnake.get(lenght-1);
        this.lenght +=1;
        if(p.getBm()==bmTailRight){
            this.arrPartSnake.add(new PartSnake(bmTailRight,p.getX()-GameView.sizeOfMap,p.getY()));
        }else if(p.getBm()==bmTailLeft){
            this.arrPartSnake.add(new PartSnake(bmTailLeft,p.getX()+GameView.sizeOfMap,p.getY()));
        }else if(p.getBm()==bmTailUp){
            this.arrPartSnake.add(new PartSnake(bmTailUp,p.getX(),p.getY()+GameView.sizeOfMap));
        }else if(p.getBm()==bmTailDown){
            this.arrPartSnake.add(new PartSnake(bmTailDown,p.getX(),p.getY()-GameView.sizeOfMap));
        }
    }
}