package com.example.strongheart.gameoflife;

/**
 * Created by strongheart on 6/18/17.
 */

public class Client {
    private float x;
    private float y;
    private String color;
    private int lifePoints;

    public Client() {
        lifePoints = 0;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String getColor() {
        return color;
    }

    public int getLifePoints() {
        return lifePoints;
    }


}
