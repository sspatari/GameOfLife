package com.example.strongheart.gameoflife;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by the-french-cat on 17/06/17.
 */

public interface JSONable {
    public JSONObject toJson();
    float getX ();
    float getY ();
 }
