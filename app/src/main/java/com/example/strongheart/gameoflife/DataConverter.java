package com.example.strongheart.gameoflife;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    public JSONable setBeaconCoordinates(List<Map<Integer, Double>> listOfTxesAndRssis) {
        Random random = new Random();
        return new Coordinates(random.nextDouble(), random.nextDouble());
    }

    private double calculateDistance(int txPower, double rssi) {
        return 0;
    }
}
