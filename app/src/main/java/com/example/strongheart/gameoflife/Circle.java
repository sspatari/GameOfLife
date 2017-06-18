package com.example.strongheart.gameoflife;

/**
 * Created by strongheart on 6/17/17.
 */

public class Circle {
    private float x;
    private float y;
    private float radius;
    private String color;

    public Circle(float x, float y, float radius, String color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    public String getColor() {
        return color;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setColor(String color) {
       this.color = color;
    }
}
