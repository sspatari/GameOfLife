package com.example.strongheart.gameoflife;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import jama.Matrix;
import jkalman.JKalman;

/**
 * Created by the-french-cat on 17/06/17.
 */

class DataConverter implements DataConvertable {
    private Double mFieldLengthX;
    private Double mFieldLengthY;
    private List<Double> mDistances;
    private Double mRelativeCoordinates;
    private JKalman mJKalman;
    private Matrix mCorrectedState;
    private Matrix mState;
    private Matrix mMeasurements;
    private Matrix mPredict;
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
    }

    @Override
    public JSONable setBeaconCoordinates(List<Pair<Integer, Double>> listOfTxesAndRssis) {
        Pair<Double, Double> xyPair = getXYPair(listOfTxesAndRssis);
        xyPair = normalize(xyPair);
        return new Coordinates(xyPair.first, xyPair.second);
    }

    private Pair<Double, Double> getXYPair(List<Pair<Integer, Double>> pairList) {
        Double xa = 0d;
        Double ya = mFieldLengthY;
        Double xb = 0d;
        Double yb = 0d;
        Double xc = mFieldLengthX;
        Double yc = 0d;
        Double da = calculateDistance(pairList.get(0));
        Double db = calculateDistance(pairList.get(1));
        Double dc = calculateDistance(pairList.get(2));

        Double yp = (Math.pow(da, 2) - Math.pow(dc, 2) - Math.pow(xa, 2) + Math.pow(xc, 2) - Math.pow(ya, 2) + Math.pow(yc, 2) + (Math.pow(db, 2) - Math.pow(da, 2) + Math.pow(xa, 2) - Math.pow(xb, 2) + Math.pow(ya, 2) - Math.pow(yb, 2)) / (2 * xa - 2 * xb)) / ((ya - yb) / (xa - xb) * (2 * xa - 2 * xc) - 2 * ya + 2 * yc);
        Double xp = (Math.pow(db, 2) - Math.pow(da, 2) + Math.pow(xa, 2) - Math.pow(xb, 2) - Math.pow(ya, 2) - Math.pow(yb, 2) - yp * (2 * ya - 2 * yb)) / (2 * xa - 2 * xb);
        return filter(xp, yp);
    }

    private Pair<Double, Double> filter(Double xp, Double yp) {
//        mMeasurements.set(0, 0, xp);
//        mMeasurements.set(1, 0, yp);
//        mCorrectedState = mJKalman.Correct(mMeasurements);
//        mPredict = mJKalman.Predict();
//        xp = mPredict.get(0, 0);
//        yp = mPredict.get(1, 0);

        return new Pair<>(xp, yp);
    }

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
}
