package com.example.strongheart.gameoflife;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class MainActivity extends FragmentActivity {
    private GameMapFragment gameMapFragment;
    private SocketConnection connection;
    private ScreenBars screenBars;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screenBars = new ScreenBars(this);
        gameMapFragment = (GameMapFragment) getFragmentManager().findFragmentById(R.id.fragment1);
        connection = new SocketConnection(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        connection.dispose();
    }

    public GameMapFragment getGameMap() { return gameMapFragment; }

    public ScreenBars getScreenBars() {return screenBars;}
}
