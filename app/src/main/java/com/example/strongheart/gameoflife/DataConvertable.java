package com.example.strongheart.gameoflife;

import android.util.Pair;

import java.util.List;

/**
 * Created by the-french-cat on 17/06/17.
 */

public interface DataConvertable {
    public JSONable setBeaconCoordinates(List<Pair<Integer, Double>> listOfTxesAndRssis);
}
