package com.example.strongheart.gameoflife;

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

    public Map<String, Double> toJson() {
        Map<String, Double> array = new HashMap<>();
        array.put("x", mX);
        array.put("y", mY);
        return array;
    }
}
