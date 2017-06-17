package com.example.strongheart.gameoflife;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by the-french-cat on 17/06/17.
 */

class DataConverter implements DataConvertable {
    private Double mFieldLengthX;
    private Double mFieldLengthY;
    private List<Double> mDistances;
    private Double mRelativeCoordinates;

    DataConverter(Double fieldX, Double fieldY) {
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
        int xa = pairList.get(0).first;
        Double ya = pairList.get(0).second;
        int xb = pairList.get(1).first;
        Double yb = pairList.get(1).second;
        int xc = pairList.get(2).first;
        Double yc = pairList.get(2).second;
        Double da = 0d;
        Double db = 0d;
        Double dc = mFieldLengthY;

        Double yp = (Math.pow(da, 2) - Math.pow(dc, 2) - Math.pow(xa, 2) + Math.pow(xc, 2) - Math.pow(ya, 2) + Math.pow(yc, 2) + (Math.pow(db, 2) - Math.pow(da, 2) + Math.pow(xa, 2) - Math.pow(xb, 2) + Math.pow(ya, 2) - Math.pow(yb, 2)) / (2 * xa - 2 * xb)) / ((ya - yb) / (xa - xb) * (2 * xa - 2 * xc) - 2 * ya + 2 * yc);
        Double xp = (Math.pow(db, 2) - Math.pow(da, 2) + Math.pow(xa, 2) - Math.pow(xb, 2) - Math.pow(ya, 2) - Math.pow(yb, 2) - yp * (2 * ya - 2 * yb)) / (2 * xa - 2 * xb);
        return new Pair<>(xp, yp);
    }

    private Pair<Double, Double> normalize(Pair<Double, Double> pair) {
        Double x = pair.first / mFieldLengthX;
        x = x < 0 ? 0 : x > 1 ? 1 : x;
        Double y = pair.second / mFieldLengthY;
        y = y < 0 ? 0 : y > 1 ? 1 : y;
        return new Pair<>(x, y);
    }
}
