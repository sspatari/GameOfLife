package com.example.strongheart.gameoflife;

import org.json.JSONArray;

import java.util.HashMap;

/**
 * Created by the-french-cat on 17/06/17.
 */

public class Coordinates implements JSONable {
    private Double mX;
    private Double mY;

    private Coordinates(Double x, Double y) {
        mX = x;
        mY = y;
    }

    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(new HashMap<String, Double>().put("x", mX));
        jsonArray.put(new HashMap<String, Double>().put("y", mY));
        return jsonArray;
    }
}
