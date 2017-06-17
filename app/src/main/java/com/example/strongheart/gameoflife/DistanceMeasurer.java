package com.example.strongheart.gameoflife;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by the-french-cat on 18/06/17.
 */

public class DistanceMeasurer {
    private Double mCurrentX;
    private Double mCurrentY;

    DistanceMeasurer() {

    }

    void setCurrentPosition(Double currentX, Double currentY) {
        mCurrentX = currentX;
        mCurrentY = currentY;
    }

    Double getDistanceTo(Double destinationX, Double destinationY) {
        return Math.sqrt(Math.pow(mCurrentX - destinationX, 2) + Math.pow(mCurrentY - destinationY, 2));
    }

    List<Double> getDistancesTo(List<Pair<Double, Double>> pairs, Double refValue) {
        List<Double> distances = new ArrayList<>();
        Double maxDistance = -1d;
        Double distance;
        for (Pair<Double, Double> pair : pairs) {
            distance = getDistanceTo(pair.first, pair.second);
            distances.add(distance);
            if (distance > maxDistance) maxDistance = distance;
        }
        for (Double dist : distances) dist *= (refValue / maxDistance);
        return distances;
    }

    List<Double> getClosest3Targets(List<Pair<Double, Double>> pairs, Double refValue) {
        return getDistancesTo(pairs, refValue).subList(0, 2);
    }
}
