package com.example.strongheart.gameoflife;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by the-french-cat on 17/06/17.
 */

class Coordinates implements JSONable {
    private Double mX;
    private Double mY;

    Coordinates(Double x, Double y) {
        mX = x;
        mY = y;
    }

    public JSONObject toJson() {
        JSONObject array = new JSONObject();
        try {
            array.put("x", mX);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            array.put("y", mY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    public float getX () {return mX.floatValue();}

    public float getY () {return mY.floatValue();}
}
