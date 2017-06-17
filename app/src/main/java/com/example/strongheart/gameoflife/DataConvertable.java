package com.example.strongheart.gameoflife;

import java.util.List;
import java.util.Map;

/**
 * Created by the-french-cat on 17/06/17.
 */

public interface DataConvertable {
    public JSONable setBeaconCoordinates(List<Map<Integer, Double>> listOfTxesAndRssis);
}
