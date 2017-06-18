package com.example.strongheart.gameoflife;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by hackintosh on 6/18/17.
 */

public class BleCommunication {
    private static final int REQUEST_ENABLE_BT = 1;

    BluetoothAdapter mBluetoothAdapter;

    private BluetoothLeScanner LeScanner;

    private ScanCallback scanCallback;

    Map<Integer, String[]> map = new HashMap<>();

    private HashMap<String, List<Integer>> MajorsMinors = new HashMap<>();
    private int found = 0;

    private int currentMinor;

    private CountDownTimer timer;

    private int delay = 0;

    private MainActivity mainActivity;

    private SocketConnection socketConnection;

    public BleCommunication(Context context) {
        this.mainActivity = (MainActivity) context;

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        MajorsMinors.put("majors", Arrays.asList(177));

        MajorsMinors.put("minors", Arrays.asList(26210, 36332, 35133));

        currentMinor = MajorsMinors.get("minors").get(found);

        socketConnection = mainActivity.getConnection();

        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        }
        else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                mainActivity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }


        scanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, final ScanResult result) {
                super.onScanResult(callbackType, result);
                JSONable toTransfer;
                int change = 0;
                byte[] data;
                int TxPower;
                HashMap beaconData;
                String major;
                beaconData = getBeaconData(result.getScanRecord().getBytes());
                Log.v("Name","" + result.getDevice());
                Log.v("RSSI" + beaconData.get("minor"),"" + result.getRssi());
                Log.d("Found_Minor","" + beaconData.get("minor"));
                Log.d("Current_Minor","" + currentMinor);
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                else {
                    restartTimer();
                }
                if(beaconData != null && (int)beaconData.get("minor") == currentMinor) {
                    found ++;
                    if(found == 3) {
                        found = 0;
                        toTransfer = new DataConverter(10.0,10.0).setBeaconCoordinates(prepareToTransfer());
                        socketConnection.emmitMessage(toTransfer);
                    }
                    currentMinor = MajorsMinors.get("minors").get(found);
                    calculateDistance((int)beaconData.get("minor"),(int)beaconData.get("tx"),result.getRssi());
                    //updateDistance((int)beaconData.get("minor"));
                }
            }
        };

        LeScanner = mBluetoothAdapter.getBluetoothLeScanner();

        if (ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mainActivity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }
        else {
            LeScanner.startScan(scanCallback);
        }
    }


//    public void updateDistance(int id) {
//        int minor_id = -1;
//        if(id == MajorsMinors.get("minors").get(0)) {
//            beacon1.setText("" +  MajorsMinors.get("minors").get(0) + ": " + map.get(MajorsMinors.get("minors").get(0))[2]);
//            minor_id = 0;
//        }
//        if(id == MajorsMinors.get("minors").get(1)) {
//            beacon2.setText("" +  MajorsMinors.get("minors").get(1) + ": " + map.get(MajorsMinors.get("minors").get(1))[2]);
//            minor_id = 1;
//        }
//        if(id == MajorsMinors.get("minors").get(2)) {
//            beacon3.setText("" +  MajorsMinors.get("minors").get(2) + ": " + map.get(MajorsMinors.get("minors").get(2))[2]);
//            minor_id = 1;
//        }
//    }

    public HashMap getBeaconData(byte[] data) {
        HashMap<String, Integer> beaconData = new HashMap<>();
        beaconData.put("major", (data[25] & 0xff) * 0x100 + (data[26] & 0xff));
        beaconData.put("minor",(data[27] & 0xff) * 0x100 + (data[28] & 0xff));
        beaconData.put("tx",(int) data[29]);

        return beaconData;
    }

    public void calculateDistance(int major, int Tx, int rssi) {
        double distance = Math.pow(10d, ((double) Tx - rssi) / (10 * 2));
        Log.v("major","" + major);
        Log.v("Distance","" + distance);
        String[] values = new String[3];
        values[0] = String.valueOf(Tx);
        values[1] = String.valueOf(rssi);
        values[2] = String.valueOf(distance);
        map.put(major,values);
    }

    public void restartTimer() {
        final int tick = 0;

        timer = new CountDownTimer(delay,tick) {
            @Override
            public void onTick(long l) {
                delay -= tick;
                Log.d("Time_tick","" + delay);
            }

            @Override
            public void onFinish() {
                //delay = 500;
                //Log.d("Time","" + time);
                LeScanner.stopScan(scanCallback);
                LeScanner.startScan(scanCallback);
            }
        }.start();
    }

    public BluetoothLeScanner getLeScanner() { return  this.LeScanner; }

    public ScanCallback getScanCallback() { return this.scanCallback; }

    public List<android.util.Pair<Integer, Double>> prepareToTransfer() {
        List<android.util.Pair<Integer, Double>> toTransfer = new ArrayList<>();
        for(int i = 0; i < MajorsMinors.get("minors").size(); i++) {
            toTransfer.add(new android.util.Pair<Integer, Double>(Integer.parseInt(map.get(MajorsMinors.get("minors").get(0))[0]),
                    Double.parseDouble(map.get(MajorsMinors.get("minors").get(0))[1])));
        }
        return toTransfer;
    }
}
