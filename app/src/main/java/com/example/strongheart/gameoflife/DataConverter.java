package com.example.strongheart.gameoflife;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by the-french-cat on 17/06/17.
 */

public class DataConverter implements DataConvertable {
    private List<Double> mDistances;
    private Double mRelativeCoordinates;

    DataConverter() {
        mDistances = new ArrayList<>();
    }

    private Double getXPosition() {
        Double dist1 = mDistances.get(0);
        Double dist2 = mDistances.get(0);
        Double dist3 = mDistances.get(0);

        return 0d;
    }

    private Double getYPosition() {
        return 0d;
    }

    protected double calculateDistance(int txPower, double rssi) {
        if (rssi == 0) {
            return -1.0; // if we cannot determine distance, return -1.
        }

        double ratio = rssi * 1.0 / txPower;
        if (ratio < 1.0) {
            return Math.pow(ratio, 10);
        } else {
            double accuracy = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
            return accuracy;
        }
    }

    public void setBeaconsCharacteristics(int txPower1, double rssi1, int txPower2, double rssi2, int txPower3, double rssi3) {
        mDistances.add(calculateDistance(txPower1, rssi1));
        mDistances.add(calculateDistance(txPower2, rssi2));
        mDistances.add(calculateDistance(txPower3, rssi3));
    }

    @Override
    public JSONable setBeaconCoordinates(List<Map<Integer, Double>> listOfTxesAndRssis) {
        return null;
    }
}
