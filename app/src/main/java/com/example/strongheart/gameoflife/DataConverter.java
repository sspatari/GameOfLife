package com.example.strongheart.gameoflife;

import android.util.Log;
import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jama.Matrix;
import jkalman.JKalman;

/**
 * Created by the-french-cat on 17/06/17.
 */

class DataConverter {
    private Double mFieldLengthX;
    private Double mFieldLengthY;
    private List<Double> mDistances;
    private Double mRelativeCoordinates;
    private JKalman mJKalman;
    private Matrix mCorrectedState;
    private Matrix mState;
    private Matrix mMeasurements;
    private Matrix mPredict;
    private JSONObject coordinates1;
    float[][] coordinates;
    Map<Integer, float[]> beaconCoordinates = new HashMap<Integer, float[]>();
    DataConverter(Double fieldX, Double fieldY) {
        try {
            mJKalman = new JKalman(2, 1);
            mCorrectedState = new Matrix(2, 1);
            mState = new Matrix(2, 1);
            mMeasurements = new Matrix(2, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mDistances = new ArrayList<>();
        mFieldLengthX = fieldX;
        mFieldLengthY = fieldY;
        coordinates1 = new JSONObject();

        coordinates = new float[][]{{0, 0}, {4, 0}, {0, 5}};

        beaconCoordinates.put(35133, coordinates[2]);
        beaconCoordinates.put(36332, coordinates[0]);
        beaconCoordinates.put(26210, coordinates[1]);

    }

    public JSONObject setBeaconCoordinates(List<Integer> listBeacon,List<Double> list) {
        List<Double> coordinates = getPosition(listBeacon,list);
        //xyPair = normalize(xyPair);
        Log.e("xyPAIR", "" + coordinates.get(0));
        try {
            coordinates1.put("x",coordinates.get(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            coordinates1.put("y",coordinates.get(1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return coordinates1;
    }

    public List<Double> getPosition(List<Integer> listBeacon, List<Double> list) {
        Object[] keys = listBeacon.toArray();
        float xa = beaconCoordinates.get(keys[0])[0];
        float ya = beaconCoordinates.get(keys[0])[1];
        float xb = beaconCoordinates.get(keys[1])[0];
        float yb = beaconCoordinates.get(keys[1])[1];
        float xc = beaconCoordinates.get(keys[2])[0];
        float yc = beaconCoordinates.get(keys[2])[1];
        double da = list.get(0);
        double db = list.get(1);
        double dc = list.get(2);
        double yp = (Math.pow(da,2) - Math.pow(dc,2) - Math.pow(xa,2) + Math.pow(xc,2) - Math.pow(ya,2) + Math.pow(yc,2) + (Math.pow(db,2) - Math.pow(da,2) + Math.pow(xa,2) - Math.pow(xb,2) + Math.pow(ya,2) - Math.pow(yb,2))/(2*xa - 2*xb))/((ya - yb)/(xa - xb)*(2*xa - 2*xc) - 2*ya +2*yc);
        double xp = (Math.pow(db,2) - Math.pow(da,2) + Math.pow(xa,2) - Math.pow(xb,2) - Math.pow(ya,2) - Math.pow(yb,2) - yp*(2*ya -2*yb))/(2*xa - 2*xb);
        Log.e("X","" + xp);
        Log.e("Y","" + yp);
        List<Double> triangulated = new ArrayList<>();
        triangulated.add(xp);
        triangulated.add(yp);

        return triangulated;
    }

//    private Pair<Double, Double> filter(Double xp, Double yp) {
////        mMeasurements.set(0, 0, xp);
////        mMeasurements.set(1, 0, yp);
////        mCorrectedState = mJKalman.Correct(mMeasurements);
////        mPredict = mJKalman.Predict();
////        xp = mPredict.get(0, 0);
////        yp = mPredict.get(1, 0);
//
//        return new ;
//    }

    private Pair<Double, Double> normalize(Pair<Double, Double> pair) {
        Double x = pair.first / mFieldLengthX;
        x = x < 0 ? 0 : x > 1 ? 1 : x;
        Double y = pair.second / mFieldLengthY;
        y = y < 0 ? 0 : y > 1 ? 1 : y;
        return new Pair<>(x, y);
    }

    private double calculateDistance(Pair<Integer, Double> pair) {
        int txPower = pair.first;
        double rssi = pair.second;
        if (rssi == 0) {
            return -1.0; // if we cannot determine accuracy, return -1.
        }
        double ratio = rssi * 1.0 / txPower;
        if (ratio < 1.0) {
            return Math.pow(ratio, 10);
        } else {
            double accuracy = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
            return accuracy;
        }
    }

    public JSONObject getCoordinates1() { return coordinates1; }
}
