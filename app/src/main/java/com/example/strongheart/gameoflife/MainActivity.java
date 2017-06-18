package com.example.strongheart.gameoflife;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends FragmentActivity {
    private GameMapFragment gameMapFragment;
    private SocketConnection connection;
    private ScreenBars screenBars;
    private Client client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new Client();
        screenBars = new ScreenBars(this);
        gameMapFragment = (GameMapFragment) getFragmentManager().findFragmentById(R.id.fragment1);

        connection = new SocketConnection(this);

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
