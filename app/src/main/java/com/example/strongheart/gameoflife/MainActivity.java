package com.example.strongheart.gameoflife;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends FragmentActivity {
    private GameMapFragment gameMapFragment;
    private SocketConnection connection;
    private ScreenBars screenBars;
    private Client client;

    private BleCommunication communication;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new Client();
        screenBars = new ScreenBars(this);
        gameMapFragment = (GameMapFragment) getFragmentManager().findFragmentById(R.id.fragment1);

        connection = new SocketConnection(this);


        communication = new BleCommunication(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.v("Premision","works");
                    communication.getLeScanner().startScan(communication.getScanCallback());

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        connection.dispose();
    }

    public void setInitClientColor(JSONObject data) {
        try {
            String color = data.getString("color");
            client.setColor(color);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public GameMapFragment getGameMap() { return gameMapFragment; }

    public ScreenBars getScreenBars() {return screenBars;}
}
